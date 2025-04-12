package com.example.expressfood.controller;

import com.example.expressfood.dto.request.ClientRequest;
import com.example.expressfood.dto.response.ClientResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final IClientService iClientService;

    @PostMapping("/client")
    public ClientResponse addClient(@RequestBody ClientRequest clientRequest){
        return iClientService.addClient(clientRequest);
    }

    @PutMapping("/client")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ClientResponse updateClient(@RequestBody ClientRequest clientRequest){
        return iClientService.updateClient(clientRequest);
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('CLIENT')")
    public ClientResponse getClientById(@PathVariable("clientId") Long clientId){
        return iClientService.getClientById(clientId);
    }

    @GetMapping("/client")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<ClientResponse> getAllClient(@RequestParam(required = false, defaultValue = "0") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size){
        return iClientService.getAllClient(page, size);
    }

    @GetMapping("/client/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<ClientResponse> searchForClient(@RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int size,
                                                    @RequestParam String keyword){
        return iClientService.searchClient(page, size, keyword);
    }

}
