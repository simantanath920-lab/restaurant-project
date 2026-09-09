package com.simanta.restaurant_backend.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.simanta.restaurant_backend.dto.Create_Category_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Create_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Delete_Category_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Delete_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Get_All_Category_ADMIN_response;
import com.simanta.restaurant_backend.dto.Get_Available_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Update_Category_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Update_category_ADMIN_request_DTO;
import com.simanta.restaurant_backend.service.CategoryService_ADMIN;

import jakarta.validation.Valid;
 
@RestController  
@RequestMapping("/restaurant/admin/api/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CategoryController_ADMIN {

    private final CategoryService_ADMIN categoryService_ADMIN;

    public CategoryController_ADMIN(CategoryService_ADMIN categoryService_ADMIN) {
        this.categoryService_ADMIN = categoryService_ADMIN;
    }
  
    // Authenticatinge
    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth(){
        return ResponseEntity.ok("Authenticated");
    }

    // Create Category
    @PostMapping("/create-category")
    public ResponseEntity<Create_Category_ADMIN_response_DTO> create_Category(

         @RequestPart("dto") @Valid Create_Category_ADMIN_request_DTO category_ADMIN_request_DTO,
         @RequestPart("imageFile") MultipartFile imageFile){

        Create_Category_ADMIN_response_DTO category_Admin_response = categoryService_ADMIN.createCategory(category_ADMIN_request_DTO,imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(category_Admin_response);
    }
 
    // Get Available Category 
    @GetMapping("/get-all-Available-category")
    public ResponseEntity<List<Get_Available_Category_ADMIN_response_DTO>> get_All_Available_Category(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService_ADMIN.getAllAvailableCategories());
    }

    // Get All Category
    @GetMapping("/get-all-category")
    public ResponseEntity<List<Get_All_Category_ADMIN_response>> get_All_category(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService_ADMIN.get_All_Category());
    }

    // Soft Delete Category
    @PatchMapping("/delete-category/{id}")
    public ResponseEntity<Delete_Category_ADMIN_response_DTO> soft_Delete_Category(@PathVariable Long id,@Valid @RequestBody Delete_Category_ADMIN_request_DTO delete_Category_ADMIN_request_DTO){
        Delete_Category_ADMIN_response_DTO category_ADMIN_response = categoryService_ADMIN.soft_Delete_Category(id,delete_Category_ADMIN_request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(category_ADMIN_response);
    }
 
    // Update Category
    @PutMapping("/update-category/{id}")
    public ResponseEntity<Update_Category_ADMIN_response_DTO> update_Category(@PathVariable Long id,@Valid @RequestBody Update_category_ADMIN_request_DTO update_category_ADMIN_request_DTO){
        Update_Category_ADMIN_response_DTO category_ADMIN_response = categoryService_ADMIN.update_Category(id,update_category_ADMIN_request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(category_ADMIN_response);
    }
    
}
