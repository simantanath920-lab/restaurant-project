package com.simanta.restaurant_backend.controller;
import java.util.List;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.Get_All_Category_USER_response;
import com.simanta.restaurant_backend.service.CategoryService_USER;

@RestController 
@RequestMapping("/restaurant/user/api/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CategoryController_USER {

    private final CategoryService_USER categoryService_USER;

    public CategoryController_USER(CategoryService_USER categoryService_USER) {
        this.categoryService_USER = categoryService_USER;
    }


    // Get All Category
    @GetMapping("/get-all-category")
    public ResponseEntity<List<Get_All_Category_USER_response>> get_All_category(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService_USER.get_All_Category());
    }

}
