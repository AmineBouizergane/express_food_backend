package com.example.expressfood.service;

import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.entities.User;

public interface IUserService {
    MessageResponse updatePassword(String newPassword);
    User getUserByUserName(String userName);
    MessageResponse enableUser(Long userId, Boolean enabled);
    User getAuthenticatedUser();
}
