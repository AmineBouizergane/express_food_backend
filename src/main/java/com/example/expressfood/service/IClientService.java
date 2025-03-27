package com.example.expressfood.service;

import com.example.expressfood.dto.request.ClientRequest;
import com.example.expressfood.dto.response.ClientResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.entities.Client;

public interface IClientService {
    ClientResponse addClient(ClientRequest clientRequest);
    ClientResponse updateClient(ClientRequest clientRequest);
    ClientResponse getClientById(Long ClientId);
    PageResponse<ClientResponse> getAllClient(int page, int size);
    PageResponse<ClientResponse> searchClient(int page, int size, String keyword);

    Client getAuthenticatedClient();
}
