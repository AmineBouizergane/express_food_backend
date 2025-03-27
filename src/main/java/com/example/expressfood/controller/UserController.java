package com.example.expressfood.controller;

import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.UserResponse;
import com.example.expressfood.entities.User;
import com.example.expressfood.security.Account;
import com.example.expressfood.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    IUserService iUserService;

    @PutMapping("/user/changePassword")
    @PostAuthorize("hasAuthority('USER')")
    public MessageResponse updatePassword(@RequestParam String newPassword){
        return iUserService.updatePassword(newPassword);
    }

    @PutMapping("/user/status")
    @PostAuthorize("hasAuthority('ADMIN')")
    public MessageResponse enableUser(@RequestParam("userId") Long userId, @RequestParam("enabled") Boolean enabled){
        return iUserService.enableUser(userId, enabled);
    }

    @GetMapping("/user/authenticated")
    @PostAuthorize("hasAuthority('USER')")
    public UserResponse getAuthenticatedUser(){
        return UserResponse.fromEntity(iUserService.getAuthenticatedUser());
    }
}
