package com.simanta.restaurant_backend.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simanta.restaurant_backend.dto.Create_Menu_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Create_Menu_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Delete_Menu_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Delete_Menu_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Get_All_Menu_response_DTO;
import com.simanta.restaurant_backend.dto.Get_Available_Menu_ADMIN_response;
import com.simanta.restaurant_backend.dto.Update_Menu_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Update_Menu_ADMIN_response_DTO;
import com.simanta.restaurant_backend.exception.CategoryIdNotFoundException;
import com.simanta.restaurant_backend.exception.MenuIdNotFoundException;
import com.simanta.restaurant_backend.exception.MenuService_ADMIN_Exception;
import com.simanta.restaurant_backend.exception.NameAlreadyExistException;
import com.simanta.restaurant_backend.model.Category;
import com.simanta.restaurant_backend.model.Menu;
import com.simanta.restaurant_backend.repository.CategoryRepository;
import com.simanta.restaurant_backend.repository.MenuRepository;

import jakarta.transaction.Transactional;

@Service
public class MenuService_ADMIN {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    public MenuService_ADMIN(MenuRepository menuRepository,CategoryRepository categoryRepository,FileUploadService fileUploadService) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.fileUploadService = fileUploadService;
    }
 
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuService_ADMIN.class);

    // Create Menu
    @Transactional 
    public Create_Menu_ADMIN_response_DTO create_Menu(final Create_Menu_ADMIN_request_DTO create_Menu_ADMIN_request_DTO,final MultipartFile imageFile){
 
        final String menuName = create_Menu_ADMIN_request_DTO.getName().trim();

        LOGGER.info("here has been reached-----");

        final Category category_Id = categoryRepository.findById(create_Menu_ADMIN_request_DTO.getCategory_id())
        .orElseThrow(()-> new CategoryIdNotFoundException("Category not found"));

        if(menuRepository.existsByNameIgnoreCase(menuName)){
            throw new NameAlreadyExistException("Menu item already exists");
        }

        String imageUrl = null;
        
        if(imageFile != null && !imageFile.isEmpty()){
            imageUrl = fileUploadService.uploadFile(imageFile, "menu");
        }
        
        final Menu menu = new Menu();
        menu.setName(menuName);
        menu.setDescription(create_Menu_ADMIN_request_DTO.getDescription());
        menu.setPrice(create_Menu_ADMIN_request_DTO.getPrice());
        menu.setImageUrl(imageUrl);
        menu.setIsAvailable(create_Menu_ADMIN_request_DTO.getIsAvailable());
        menu.setIsVeg(create_Menu_ADMIN_request_DTO.getIsVeg());
        menu.setPreprationtime(create_Menu_ADMIN_request_DTO.getPreprationtime());
        menu.setStock(create_Menu_ADMIN_request_DTO.getStock());
        menu.setCategory(category_Id);
        menu.setCreateAt(LocalDateTime.now());

        try {

            final Menu SavedMenuDetails = menuRepository.save(menu);

            return new Create_Menu_ADMIN_response_DTO(SavedMenuDetails.getId(),SavedMenuDetails.getName(), "Menu created successfully", 201);

        } catch (Exception e) {
            throw new MenuService_ADMIN_Exception("Something went wrong. Unable to create menu.");
        }
    }

    // Get All Menu
    public List<Get_All_Menu_response_DTO> get_All_Menu(){
  
        try {

            final List<Menu> menu = menuRepository.findAll();

            final List<Get_All_Menu_response_DTO> response = new ArrayList<>();

            for(Menu displayMenu:menu){

                response.add(new Get_All_Menu_response_DTO(displayMenu.getId(), displayMenu.getName(), displayMenu.getDescription(), displayMenu.getPrice(),
                                                                displayMenu.getImageUrl(), displayMenu.getIsAvailable(), displayMenu.getIsVeg(), 
                                                                displayMenu.getPreprationtime(), displayMenu.getStock(), displayMenu.getCategory().getId(), displayMenu.getCategory().getName()));
            }

            return response;

        } catch (Exception e) {
            throw new MenuService_ADMIN_Exception("Something went wrong on viewing all menus.");
        }
    }

    // Get_Available_Menu
    public List<Get_Available_Menu_ADMIN_response> get_Available_Menu(){

        try {
            
            List<Menu> menu = menuRepository.findByIsAvailableTrue();

            List<Get_Available_Menu_ADMIN_response> response = new ArrayList<>();

            for(Menu displayAvailableMenu : menu){

                response.add(new Get_Available_Menu_ADMIN_response(displayAvailableMenu.getId(), displayAvailableMenu.getName(), displayAvailableMenu.getDescription(), displayAvailableMenu.getPrice(),
                     displayAvailableMenu.getStock(), displayAvailableMenu.getCategory().getId(), displayAvailableMenu.getCategory().getName()));
            }

            return response;

        } catch (Exception e) {
            throw new MenuService_ADMIN_Exception("Something went wrong. Unable to fetch available menus.");
        }
    }
 
    // Soft Delete Menu
    @Transactional
    public Delete_Menu_ADMIN_response_DTO soft_Delete_menu(final Long id,final Delete_Menu_ADMIN_request_DTO delete_Menu_ADMIN_request_DTO){

        final Menu menu = menuRepository.findById(id)
        .orElseThrow(() -> new MenuIdNotFoundException("Menu item not found")); 

        menu.setIsAvailable(delete_Menu_ADMIN_request_DTO.getIsAvailable());
        menu.setUpdateAt(LocalDateTime.now());

        try {

            final Menu Save_Menu_Details = menuRepository.save(menu);

            if(Boolean.FALSE.equals(Save_Menu_Details.getIsAvailable())){
                return new Delete_Menu_ADMIN_response_DTO(Save_Menu_Details.getName(),"Menu item changed to Non-Available");
            }
            else{
                return new Delete_Menu_ADMIN_response_DTO(Save_Menu_Details.getName(),"Menu item changed to Available");
            }
            
        } catch (Exception e) {
            LOGGER.error("Unexpected error while updating category availability for category id: {}",menu.getId(),e);
                        
            throw new MenuService_ADMIN_Exception("Unable to update category availability. Please try again later.");

        }
    }
 
    
    // Update Menu 
    @Transactional
    public Update_Menu_ADMIN_response_DTO update_Menu(final Long id,final Update_Menu_ADMIN_request_DTO update_Menu_ADMIN_request_DTO){

        final String menuName = update_Menu_ADMIN_request_DTO.getName().trim();

        final Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new MenuIdNotFoundException("Menu item not found"));

        if(!menu.getName().equalsIgnoreCase(menuName) && menuRepository.existsByNameIgnoreCase(menuName)){
            throw new NameAlreadyExistException("Menu item already exists");
        } 

        final Category category_Id = categoryRepository.findById(update_Menu_ADMIN_request_DTO.getCategory_id())
            .orElseThrow(()-> new CategoryIdNotFoundException("Category id not found"));

            if(category_Id == null){
                throw new IllegalArgumentException("Category id cannot be empty");
            }

        menu.setName(menuName);
        menu.setDescription(update_Menu_ADMIN_request_DTO.getDescription());
        menu.setPrice(update_Menu_ADMIN_request_DTO.getPrice());
        menu.setImageUrl(update_Menu_ADMIN_request_DTO.getImageUrl());
        menu.setIsVeg(update_Menu_ADMIN_request_DTO.getIsVeg());
        menu.setPreprationtime(update_Menu_ADMIN_request_DTO.getPreprationtime());
        menu.setStock(update_Menu_ADMIN_request_DTO.getStock());
        menu.setCategory(category_Id);

        try {

            final Menu saved_menu_details = menuRepository.save(menu); 
            
            return new Update_Menu_ADMIN_response_DTO(saved_menu_details.getId(), saved_menu_details.getName(), "Menu item update successfully", 200);

        } catch (Exception e) {
           throw new MenuService_ADMIN_Exception("Something went wrong. Unable to update menu.");
        }
    }
}
