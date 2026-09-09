package com.simanta.restaurant_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.simanta.restaurant_backend.dto.Get_All_Menu_By_CategoryID_USER_response_DTO;
import com.simanta.restaurant_backend.exception.CategoryIdNotFoundException;
import com.simanta.restaurant_backend.model.Category;
import com.simanta.restaurant_backend.model.Menu;
import com.simanta.restaurant_backend.repository.CategoryRepository;
import com.simanta.restaurant_backend.repository.MenuRepository;

@Service
public class MenuService_USER {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    public MenuService_USER(MenuRepository menuRepository,CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
    }
 
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuService_ADMIN.class);
 

    // Get Menu by Category
    public List<Get_All_Menu_By_CategoryID_USER_response_DTO> Get_Menu_by_CategoryID(final Long categoryID){

        LOGGER.info("");

        final Category category = categoryRepository.findById(categoryID)
            .orElseThrow(()-> new CategoryIdNotFoundException("Category not found"));

            List<Menu> menus = menuRepository.findByCategoryAndIsAvailableTrue(category);

            List<Get_All_Menu_By_CategoryID_USER_response_DTO> response = new ArrayList<>();

            for(Menu MenusByCategory:menus){

                    response.add(new Get_All_Menu_By_CategoryID_USER_response_DTO(MenusByCategory.getId(), MenusByCategory.getName(), MenusByCategory.getDescription(), MenusByCategory.getPrice(),
                                        MenusByCategory.getImageUrl(), MenusByCategory.getIsAvailable(), MenusByCategory.getIsVeg(), 
                                        MenusByCategory.getPreprationtime(), MenusByCategory.getStock(), MenusByCategory.getCategory().getId(), MenusByCategory.getCategory().getName()));
             }
            return response;
    }


}
