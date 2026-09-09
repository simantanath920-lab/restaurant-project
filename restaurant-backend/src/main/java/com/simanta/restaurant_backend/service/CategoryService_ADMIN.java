package com.simanta.restaurant_backend.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simanta.restaurant_backend.dto.Create_Category_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Create_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Delete_Category_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Delete_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Get_All_Category_ADMIN_response;
import com.simanta.restaurant_backend.dto.Get_Available_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Update_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Update_category_ADMIN_request_DTO;
import com.simanta.restaurant_backend.exception.CategoryCreationFailedException;
import com.simanta.restaurant_backend.exception.CategoryFetchFailedException;
import com.simanta.restaurant_backend.exception.CategoryIdNotFoundException;
import com.simanta.restaurant_backend.exception.CategoryNameAlreadyExists;
import com.simanta.restaurant_backend.exception.CategoryService_ADMIN_Exception;
import com.simanta.restaurant_backend.exception.InvalidDataException;
import com.simanta.restaurant_backend.model.Category;
import com.simanta.restaurant_backend.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService_ADMIN {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    public CategoryService_ADMIN(CategoryRepository categoryRepository,FileUploadService fileUploadService) {
        this.categoryRepository = categoryRepository;
        this.fileUploadService = fileUploadService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService_ADMIN.class);


    // Create Category
    @Transactional
    public Create_Category_ADMIN_response_DTO createCategory(final Create_Category_ADMIN_request_DTO category_ADMIN_request_DTO,final MultipartFile imageFile) {

        final String normalizedCategoryName = category_ADMIN_request_DTO.getName().trim();
 
        LOGGER.info("Category creation request received for category: {}",normalizedCategoryName);

        if (categoryRepository.existsByNameIgnoreCase(normalizedCategoryName)) {
            LOGGER.warn("Duplicate category creation attempt detected: {}",normalizedCategoryName);
            throw new CategoryNameAlreadyExists("Category already exists");
        }

        String imageUrl = null;

        if(imageFile != null && !imageFile.isEmpty()){
            imageUrl = fileUploadService.uploadFile(imageFile,"category");
        }
 
        final Category category = new Category();
 
        category.setName(normalizedCategoryName);
        category.setDescription(category_ADMIN_request_DTO.getDescription());
        category.setImageUrl(imageUrl);
        category.setIsAvailable(true);
        category.setCreateAt(LocalDateTime.now());

        try {

            final Category savedCategory = categoryRepository.save(category);

            LOGGER.info("Category created successfully with id: {}",savedCategory.getId());

            return new Create_Category_ADMIN_response_DTO(savedCategory.getId(),savedCategory.getName(),"Category created successfully",201,savedCategory.getImageUrl());

        } catch (DataIntegrityViolationException e) {

            LOGGER.error("Database constraint violation while creating category: {}",normalizedCategoryName,e);
            throw new CategoryNameAlreadyExists("Category already exists.");

        } catch (Exception e) {

            LOGGER.error("Unexpected error while creating category: {}",normalizedCategoryName,e);
            throw new CategoryCreationFailedException("Unable to create category. Please try again later.");
        }
    }
 
 



    // Get All Available Category
    public List<Get_Available_Category_ADMIN_response_DTO> getAllAvailableCategories() {

    LOGGER.info("Fetching all available categories");

    try {

        List<Category> categories = categoryRepository.findByIsAvailableTrue();

        List<Get_Available_Category_ADMIN_response_DTO> response = new ArrayList<>();

        for (Category category : categories) {

            response.add(new Get_Available_Category_ADMIN_response_DTO(category.getId(),category.getName(),category.getDescription()));
        }

        LOGGER.info("Successfully fetched {} available categories", response.size());

        return response;

        } catch (Exception e) {

            LOGGER.error("Failed to fetch available categories", e);

            throw new CategoryFetchFailedException("Unable to fetch available categories");
        }
    }



    // Get All Category
    public List<Get_All_Category_ADMIN_response> get_All_Category() {

    LOGGER.info("Fetching all categories");

    try {
 
        List<Category> categories = categoryRepository.findAll();

        List<Get_All_Category_ADMIN_response> responses = new ArrayList<>();

        for (Category category : categories) {

            responses.add(new Get_All_Category_ADMIN_response(category.getId(),category.getName(),category.getDescription(),category.getIsAvailable(),category.getImageUrl()));
        }

        LOGGER.info("Successfully fetched {} categories", responses.size());

        return responses;

        } catch (Exception e) {

            LOGGER.error("Failed to fetch all categories", e);

            throw new CategoryFetchFailedException("Unable to fetch all categories");
        }
    }

  
 
    // Delete Category
    @Transactional
    public Delete_Category_ADMIN_response_DTO soft_Delete_Category(final Long id,final Delete_Category_ADMIN_request_DTO requestDTO) {

        LOGGER.info("Starting category availability update for category id: {}",id);

        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {

                    LOGGER.warn("Category not found with id: {}",id);

                    return new CategoryIdNotFoundException("Category not found");
                });

        category.setIsAvailable(requestDTO.getIsAvailable());
        category.setUpdateAt(LocalDateTime.now());

        try {

            LOGGER.info("Saving category availability update for category id: {}",category.getId());

            final Category savedCategory = categoryRepository.save(category);

            LOGGER.info("Category availability updated successfully for category id: {}",savedCategory.getId());

            if(Boolean.FALSE.equals(savedCategory.getIsAvailable())){
                return new Delete_Category_ADMIN_response_DTO(savedCategory.getName(),"Category changed to Non-Available.");
            }else{
                return new Delete_Category_ADMIN_response_DTO(savedCategory.getName(),"Category changed to Available.");
            }
        }

        catch (DataIntegrityViolationException e) {
            LOGGER.error("Database constraint violation while updating category id: {}",category.getId(),e);
            throw new InvalidDataException("Unable to update category availability");
        }

        catch (OptimisticLockingFailureException e) {
            LOGGER.error("Concurrent update detected for category id: {}",category.getId(),e);
            throw new CategoryService_ADMIN_Exception("Category was modified by another transaction. Please try again.");
        }

        catch (JpaSystemException e) {
            LOGGER.error("JPA/Hibernate error while updating category id: {}",category.getId(),e);
            throw new CategoryService_ADMIN_Exception("Database error occurred while updating category");
        }

        catch (Exception e) {
            LOGGER.error("Unexpected error while updating category availability for category id: {}",category.getId(),e);
            throw new CategoryService_ADMIN_Exception("Unable to update category availability. Please try again later.");
        }
    }


    // Update Category
    @Transactional
    public Update_Category_ADMIN_response_DTO update_Category(final Long id,final Update_category_ADMIN_request_DTO requestDTO) {

        LOGGER.info("Starting category update process for category id: {}",id);

        final String normalizedName = requestDTO.getName().trim();

        final Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.warn("Category update failed. Category not found with id: {}",id);
                    return new CategoryIdNotFoundException("Category not found");
                });
 
        if(!category.getName().equalsIgnoreCase(normalizedName) && categoryRepository.existsByNameIgnoreCase(normalizedName)){
            throw new CategoryNameAlreadyExists("Category already exists."); 
        }

        category.setName(normalizedName);
        category.setDescription(requestDTO.getDescription());
        category.setUpdateAt(LocalDateTime.now());

        try {

            LOGGER.info("Saving updated category details for id: {}",id);

            final Category updatedCategory = categoryRepository.save(category);

            LOGGER.info("Category updated successfully. Category id: {}, Name: {}",updatedCategory.getId(),updatedCategory.getName());

            return new Update_Category_ADMIN_response_DTO(updatedCategory.getId(),updatedCategory.getName(),"Category updated successfully",200);

        }

        catch (DataIntegrityViolationException e) {
            LOGGER.error("Database constraint violation while updating category id: {}",id, e);
            throw new InvalidDataException("Category data is invalid");
        }

        catch (OptimisticLockingFailureException e) {
            LOGGER.error("Concurrent update detected for category id: {}",id, e);
            throw new CategoryService_ADMIN_Exception("Category was modified by another transaction");
        }

        catch (JpaSystemException e) {
            LOGGER.error("JPA/Hibernate error while updating category id: {}",id, e);
            throw new CategoryService_ADMIN_Exception("Database error occurred while updating category");
        }

        catch (Exception e) {
            LOGGER.error("Unexpected error while updating category id: {}",id, e);
            throw new CategoryService_ADMIN_Exception("Unable to update category");
        }
    }

}