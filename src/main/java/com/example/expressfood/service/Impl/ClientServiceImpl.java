package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.CartRepos;
import com.example.expressfood.dao.ClientRepos;
import com.example.expressfood.dao.RoleRepos;
import com.example.expressfood.dto.request.ClientRequest;
import com.example.expressfood.dto.response.ClientResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.entities.Cart;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Role;
import com.example.expressfood.entities.User;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.UserException;
import com.example.expressfood.service.IClientService;
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
public class ClientServiceImpl implements IClientService {

    @Autowired
    ClientRepos clientRepos;
    @Autowired
    RoleRepos roleRepos;
    @Autowired
    CartRepos cartRepos;
    @Autowired
    IUserService iUserService;
    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public ClientResponse addClient(ClientRequest clientRequest) {
        Client client = clientRequest.toEntity();
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepos.findByRoleName(RoleEnum.USER.value()));
        roles.add(roleRepos.findByRoleName(RoleEnum.CLIENT.value()));
        client.setRoles(roles);
        client.setEncryptedPassword(bCryptPasswordEncoder().encode(client.getEncryptedPassword()));
        client.setIsActivated(true);
        Client savedClient = clientRepos.save(client);
        // Create Cart for client
        Cart cart = new Cart(null, savedClient, null);
        cartRepos.save(cart);
        // End
        return ClientResponse.fromEntity(savedClient);
    }

    @Override
    public ClientResponse updateClient(ClientRequest clientRequest) {
        Client client = clientRequest.toEntity();
        Client searchedClient = clientRepos.findById(client.getId())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        if(!client.getUserName().equals(searchedClient.getUserName())
                || !client.getEncryptedPassword().equals(searchedClient.getEncryptedPassword()))
            throw new UserException(ErrorMessages.ATTRIBUTE_UPDATING_ERROR.getErrorMessage());
        Client savedClient = clientRepos.save(client);
        return ClientResponse.fromEntity(savedClient);
    }

    @Override
    public ClientResponse getClientById(Long clientId) {
        User user = iUserService.getAuthenticatedUser();
        if(user.getRoles().stream()
                .anyMatch(role -> RoleEnum.CLIENT.value().equals(role.getRoleName())) && !Objects.equals(user.getId(), clientId))
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        Client client = clientRepos.findById(clientId)
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        return ClientResponse.fromEntity(client);
    }

    @Override
    public PageResponse<ClientResponse> getAllClient(int page, int size) {
        Page<Client> clientList = clientRepos.findAll(PageRequest.of(page, size));
        PageResponse<ClientResponse> clientResponseList = new PageResponse<>();
        clientResponseList.setPage(page);
        clientResponseList.setSize(size);
        clientResponseList.setTotalPage(clientList.getTotalPages());
        List<ClientResponse> clientResponses = clientList.getContent().stream()
                .map(ClientResponse::fromEntity)
                .collect(Collectors.toList());
        clientResponseList.setContent(clientResponses);
        if (clientResponseList.getContent().isEmpty())
            throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return clientResponseList;
    }

    @Override
    public PageResponse<ClientResponse> searchClient(int page, int size, String keyword) {
        Page<Client> clientsList = clientRepos.findByFirstNameOrLastNameContaining(keyword, PageRequest.of(page, size));
        PageResponse<ClientResponse> clientResponseList = new PageResponse<>();
        clientResponseList.setPage(page);
        clientResponseList.setSize(size);
        clientResponseList.setTotalPage(clientsList.getTotalPages());
        List<ClientResponse> clientResponses = clientsList.getContent().stream()
                .map(ClientResponse::fromEntity)
                .collect(Collectors.toList());
        clientResponseList.setContent(clientResponses);
        if (clientResponseList.getContent().isEmpty())
            throw new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        else
            return clientResponseList;
    }

    @Override
    public Client getAuthenticatedClient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return clientRepos.findByUserName(auth.getName())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }
}
