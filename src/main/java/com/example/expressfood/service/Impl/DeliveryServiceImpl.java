package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.DeliveryPersonRepos;
import com.example.expressfood.dao.RoleRepos;
import com.example.expressfood.dto.request.DeliveryPersonRequest;
import com.example.expressfood.dto.response.DeliveryPersonResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.entities.DeliveryPerson;
import com.example.expressfood.entities.Role;
import com.example.expressfood.entities.User;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.UserException;
import com.example.expressfood.service.IDeliveryService;
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
public class DeliveryServiceImpl implements IDeliveryService {
    @Autowired
    DeliveryPersonRepos deliveryPersonRepos;
    @Autowired
    RoleRepos roleRepos;
    @Autowired
    IUserService iUserService;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public DeliveryPersonResponse addDeliveryPerson(DeliveryPersonRequest deliveryPersonRequest) {
        DeliveryPerson deliveryPerson = deliveryPersonRequest.toEntity();
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepos.findByRoleName(RoleEnum.USER.value()));
        roles.add(roleRepos.findByRoleName(RoleEnum.DELIVERY.value()));
        deliveryPerson.setRoles(roles);
        deliveryPerson.setEncryptedPassword(bCryptPasswordEncoder().encode("First"));
        deliveryPerson.setUserName(deliveryPerson.getFirstName().toLowerCase()+"_"+deliveryPerson.getLastName().toLowerCase());
        deliveryPerson.setEncryptedPassword(bCryptPasswordEncoder().encode(deliveryPerson.getEncryptedPassword()));
        DeliveryPerson savedDelivery = deliveryPersonRepos.save(deliveryPerson);
        return DeliveryPersonResponse.fromEntity(savedDelivery);
    }

    @Override
    public DeliveryPersonResponse updateDeliveryPerson(DeliveryPersonRequest deliveryPersonRequest) {
        DeliveryPerson deliveryPerson = deliveryPersonRequest.toEntity();
        DeliveryPerson searchedDelivery = deliveryPersonRepos.findById(deliveryPerson.getId())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        if(!deliveryPerson.getUserName().equals(searchedDelivery.getUserName())
                || !deliveryPerson.getEncryptedPassword().equals(searchedDelivery.getEncryptedPassword()))
            throw new UserException(ErrorMessages.ATTRIBUTE_UPDATING_ERROR.getErrorMessage());
        DeliveryPerson savedDelivery = deliveryPersonRepos.save(deliveryPerson);
        return DeliveryPersonResponse.fromEntity(savedDelivery);
    }

    @Override
    public DeliveryPersonResponse getDeliveryPersonById(Long deliveryPersonId) {
        User user = iUserService.getAuthenticatedUser();
        if(user.getRoles().stream()
                .anyMatch(role -> RoleEnum.DELIVERY.value().equals(role.getRoleName())) && !Objects.equals(user.getId(), deliveryPersonId))
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        DeliveryPerson deliveryPerson = deliveryPersonRepos.findById(deliveryPersonId)
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        return DeliveryPersonResponse.fromEntity(deliveryPerson);
    }

    @Override
    public Collection<DeliveryPersonResponse> getAllDeliveryPerson() {
        Collection<DeliveryPerson> deliveryList = deliveryPersonRepos.findAll();
        Collection<DeliveryPersonResponse> deliveryResponseList = deliveryList.stream()
                .map(DeliveryPersonResponse::fromEntity)
                .collect(Collectors.toList());
        if (deliveryList.isEmpty())
            throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return deliveryResponseList;
    }

    @Override
    public PageResponse<DeliveryPersonResponse> searchDeliveryPerson(int page, int size, String keyword) {
        Page<DeliveryPerson> deliveryList ;
        if(keyword != null)
            deliveryList = deliveryPersonRepos.findByFirstNameOrLastNameContaining(keyword, PageRequest.of(page, size));
        else
            deliveryList = deliveryPersonRepos.findAll(PageRequest.of(page, size));
        PageResponse<DeliveryPersonResponse>  pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(deliveryList.getTotalPages());
        List<DeliveryPersonResponse> deliveryPersonResponseList = deliveryList.getContent().stream()
                .map(DeliveryPersonResponse::fromEntity)
                .collect(Collectors.toList());
        pageResponse.setContent(deliveryPersonResponseList);
        if (pageResponse.getContent().isEmpty())
            throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return pageResponse;
    }

    @Override
    public DeliveryPerson getAuthenticatedDelivery() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return deliveryPersonRepos.findByUserName(auth.getName())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }
}
