package com.simanta.restaurant_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simanta.restaurant_backend.dto.Get_All_Menu_By_CategoryID_USER_response_DTO;
import com.simanta.restaurant_backend.service.MenuService_USER;

@RestController 
@RequestMapping("/restaurant/user/api/auth")
public class MenuController_USER {

    private final MenuService_USER menuService_USER;

    public MenuController_USER(MenuService_USER menuService_USER) {
        this.menuService_USER = menuService_USER;
    }

    @GetMapping("/get-all-Menu-By-Category/{id}")
    public ResponseEntity<List<Get_All_Menu_By_CategoryID_USER_response_DTO>> Get_Menu_by_CategoryID(@PathVariable Long id){
        List<Get_All_Menu_By_CategoryID_USER_response_DTO> all_Menu_By_CategoryID_USER_response = menuService_USER.Get_Menu_by_CategoryID(id);
        return ResponseEntity.status(HttpStatus.OK).body(all_Menu_By_CategoryID_USER_response);
    }

}
