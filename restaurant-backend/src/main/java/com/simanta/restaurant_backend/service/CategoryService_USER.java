package com.simanta.restaurant_backend.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.simanta.restaurant_backend.dto.Get_All_Category_USER_response;
import com.simanta.restaurant_backend.exception.CategoryFetchFailedException;
import com.simanta.restaurant_backend.model.Category;
import com.simanta.restaurant_backend.repository.CategoryRepository;

@Service 
public class CategoryService_USER {

    private final CategoryRepository categoryRepository;

    public CategoryService_USER(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get All Category
    public List<Get_All_Category_USER_response> get_All_Category() {

    try {
 
        List<Category> categories = categoryRepository.findAll();

        List<Get_All_Category_USER_response> responses = new ArrayList<>();

        for (Category category : categories) {

            responses.add(new Get_All_Category_USER_response(category.getId(),category.getName(),category.getDescription(),category.getIsAvailable(),category.getImageUrl()));
        }

        return responses;

        } catch (Exception e) {

            throw new CategoryFetchFailedException("Unable to fetch all categories");
        }
    }


}
