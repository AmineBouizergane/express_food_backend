package com.example.expressfood.controller;

import com.example.expressfood.dto.request.UniteRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.dto.response.UniteResponse;
import com.example.expressfood.entities.Unite;
import com.example.expressfood.service.IUniteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UniteController {

    @Autowired
    IUniteService iUniteService;

    @PostMapping("/unite")
    @PostAuthorize("hasAuthority('ADMIN')")
    public UniteResponse addUnite(@RequestBody UniteRequest uniteRequest){
        return iUniteService.addUnite(uniteRequest);
    }

    @DeleteMapping("/unite")
    @PostAuthorize("hasAuthority('ADMIN')")
    public MessageResponse deleteUnite(@RequestParam("uniteId") Long uniteId){
        return iUniteService.deleteUnite(uniteId);
    }

    @PutMapping("/unite")
    @PostAuthorize("hasAuthority('ADMIN')")
    public UniteResponse updateUnite(@RequestBody UniteRequest uniteRequest){
        return iUniteService.updateUnite(uniteRequest);
    }

    @GetMapping("/unite")
    @PostAuthorize("hasAuthority('ADMIN')")
    public Collection<UniteResponse> getAllUnites(){
        return iUniteService.getAllUnites();
    }
}
