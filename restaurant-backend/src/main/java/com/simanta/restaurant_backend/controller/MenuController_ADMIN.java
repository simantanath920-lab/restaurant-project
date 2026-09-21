package com.simanta.restaurant_backend.controller;

import java.util.List; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.simanta.restaurant_backend.dto.Create_Menu_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Create_Menu_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Delete_Menu_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Delete_Menu_ADMIN_response_DTO;
import com.simanta.restaurant_backend.dto.Get_All_Menu_response_DTO;
import com.simanta.restaurant_backend.dto.Get_Available_Menu_ADMIN_response;
import com.simanta.restaurant_backend.dto.Update_Menu_ADMIN_request_DTO;
import com.simanta.restaurant_backend.dto.Update_Menu_ADMIN_response_DTO;
import com.simanta.restaurant_backend.service.MenuService_ADMIN;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurant/admin/api")
public class MenuController_ADMIN {

    private final MenuService_ADMIN menuService;

    public MenuController_ADMIN(MenuService_ADMIN menuService) {
        this.menuService = menuService;
    }

    // Authenticatinge 
    @GetMapping("/check-menu")
    public ResponseEntity<String> checkMenu(){
        return ResponseEntity.ok("Authenticated");
    }

    // Create Menu
    @PostMapping("/create-menu")
    public ResponseEntity<Create_Menu_ADMIN_response_DTO> create_Menu(
        
        @RequestPart("dto") @Valid Create_Menu_ADMIN_request_DTO create_Menu_ADMIN_request_DTO,
        @RequestPart("imageFile") MultipartFile imageFile){
        
        Create_Menu_ADMIN_response_DTO create_Menu_ADMIN_response = menuService.create_Menu(create_Menu_ADMIN_request_DTO,imageFile);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(create_Menu_ADMIN_response);
    }

    // Get All Menu
    @GetMapping("/get-all-menu")
    public ResponseEntity<List<Get_All_Menu_response_DTO>> get_All_Menu(){
        List<Get_All_Menu_response_DTO> all_Menu_response = menuService.get_All_Menu();
        return ResponseEntity.status(HttpStatus.OK).body(all_Menu_response);
    }

    // Get Available Menu
    @GetMapping("/get-all-Available-menu")
    public ResponseEntity<List<Get_Available_Menu_ADMIN_response>> get_Available_Menu(){
        List<Get_Available_Menu_ADMIN_response> available_Menu_response = menuService.get_Available_Menu();
        return ResponseEntity.status(HttpStatus.OK).body(available_Menu_response);
    }

    // Soft Delete Menu
    @PatchMapping("/delete-menu/{id}")
    public ResponseEntity<Delete_Menu_ADMIN_response_DTO> soft_Delete_Menu(@PathVariable Long id,@Valid @RequestBody Delete_Menu_ADMIN_request_DTO delete_Menu_ADMIN_request_DTO){
        Delete_Menu_ADMIN_response_DTO delete_Menu_ADMIN_response = menuService.soft_Delete_menu(id, delete_Menu_ADMIN_request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(delete_Menu_ADMIN_response);
    }

    // Update Menu
    @PutMapping("/update-menu/{id}")
    public ResponseEntity<Update_Menu_ADMIN_response_DTO> update_Menu(@PathVariable Long id,@Valid @RequestBody Update_Menu_ADMIN_request_DTO update_Menu_ADMIN_request_DTO){
        Update_Menu_ADMIN_response_DTO update_Menu_ADMIN_response = menuService.update_Menu(id, update_Menu_ADMIN_request_DTO);
        return ResponseEntity.status(HttpStatus.OK).body(update_Menu_ADMIN_response);
    }

}
