package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.UserRepos;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.entities.User;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.UserException;
import com.example.expressfood.service.IUserService;
import com.example.expressfood.shared.MessagesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepos userRepos;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public MessageResponse updatePassword(String newPassword) {
        User userVerification = getAuthenticatedUser();
        User user = userRepos.findById(userVerification.getId())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        user.setEncryptedPassword(bCryptPasswordEncoder().encode(newPassword));
        User updatedUser = userRepos.save(user);
        return new MessageResponse(MessagesEnum.USER_PSW_CHANGED_SUCCESSFULLY.getMessage());
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepos.findByUserName(userName)
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    @Override
    public MessageResponse enableUser(Long userId, Boolean enabled) {
        User user = userRepos.findById(userId)
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        user.setIsActivated(enabled);
        User updatedUser = userRepos.save(user);
        if(updatedUser.getIsActivated() == enabled && enabled)
            return new MessageResponse(MessagesEnum.USER_ENABLED_SUCCESSFULLY.getMessage());
        else if(updatedUser.getIsActivated() == enabled && !enabled)
            return new MessageResponse(MessagesEnum.USER_DISABLED_SUCCESSFULLY.getMessage());
        else
            throw new UserException(ErrorMessages.UPDATE_RECORD_ERROR.getErrorMessage());
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepos.findByUserName(auth.getName())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }
}
