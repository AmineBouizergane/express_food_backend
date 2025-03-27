package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.CookRepos;
import com.example.expressfood.dao.RoleRepos;
import com.example.expressfood.dto.request.CookRequest;
import com.example.expressfood.dto.response.ClientResponse;
import com.example.expressfood.dto.response.CookResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Cook;
import com.example.expressfood.entities.Role;
import com.example.expressfood.entities.User;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.UserException;
import com.example.expressfood.service.ICookService;
import com.example.expressfood.service.IUserService;
import com.example.expressfood.shared.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CookServiceImpl implements ICookService {

    @Autowired
    CookRepos cookRepos;

    @Autowired
    RoleRepos roleRepos;
    @Autowired
    IUserService iUserService;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public CookResponse addCook(CookRequest cookRequest) {
        Cook cook = cookRequest.toEntity();
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepos.findByRoleName(RoleEnum.USER.value()));
        roles.add(roleRepos.findByRoleName(RoleEnum.COOK.value()));
        cook.setRoles(roles);
        cook.setEncryptedPassword(bCryptPasswordEncoder().encode("First"));
        cook.setUserName(cook.getFirstName().toLowerCase()+"_"+cook.getLastName().toLowerCase());
        Cook savedCook = cookRepos.save(cook);
        return CookResponse.fromEntity(savedCook);
    }

    @Override
    public CookResponse updateCook(CookRequest cookRequest) {
        Cook cook = cookRequest.toEntity();
        Cook searchedCook = cookRepos.findById(cook.getId())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        if(!cook.getUserName().equals(searchedCook.getUserName())
                || !cook.getEncryptedPassword().equals(searchedCook.getEncryptedPassword()))
            throw new UserException(ErrorMessages.ATTRIBUTE_UPDATING_ERROR.getErrorMessage());
        Cook savedCook = cookRepos.save(cook);
        return CookResponse.fromEntity(savedCook);
    }
    @Override
    public CookResponse getCookById(Long cookId) {
        User user = iUserService.getAuthenticatedUser();
        if(user.getRoles().stream()
                .anyMatch(role -> RoleEnum.COOK.value().equals(role.getRoleName())) && !Objects.equals(user.getId(), cookId))
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        Cook cook = cookRepos.findById(cookId)
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        return CookResponse.fromEntity(cook);
    }

    @Override
    public Collection<CookResponse> getAllCook() {
        Collection<Cook> cooksList = cookRepos.findAll();
        Collection<CookResponse> pageResponse = cooksList.stream()
                .map(CookResponse::fromEntity)
                .collect(Collectors.toList());
        if (pageResponse.isEmpty())
            throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public PageResponse<CookResponse> searchCook(int page, int size, String keyword) {
        Page<Cook> cooksList;
        if(keyword != null)
            cooksList = cookRepos.findByFirstNameOrLastNameContaining(keyword, PageRequest.of(page, size));
        else
            cooksList = cookRepos.findAll(PageRequest.of(page, size));
        PageResponse<CookResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(cooksList.getTotalPages());
        List<CookResponse> cookResponseList = cooksList.getContent().stream()
                .map(CookResponse::fromEntity)
                .collect(Collectors.toList());
        pageResponse.setContent(cookResponseList);
        if (pageResponse.getContent().isEmpty())
            throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public Cook getAuthenticatedCook() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return cookRepos.findByUserName(auth.getName())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }
}
