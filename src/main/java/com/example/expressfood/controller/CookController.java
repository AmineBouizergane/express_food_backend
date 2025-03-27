package com.example.expressfood.controller;

import com.example.expressfood.dto.request.CookRequest;
import com.example.expressfood.dto.response.CookResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.service.ICookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CookController {

    @Autowired
    ICookService iCookService;

    @PostMapping("/cook")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CookResponse addCook(@RequestBody CookRequest cookRequest){
        return iCookService.addCook(cookRequest);
    }

    @PutMapping("/cook")
    @PreAuthorize("hasAuthority('COOK')")
    public CookResponse updateCook(@RequestBody CookRequest cookRequest){
        return iCookService.updateCook(cookRequest);
    }

    @GetMapping("/cook/{cookId}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('COOK')")
    public CookResponse getCookById(@PathVariable("cookId") Long cookId){
        return iCookService.getCookById(cookId);
    }

    @GetMapping("/cook")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<CookResponse> getAllCook(){
        return iCookService.getAllCook();
    }

    @GetMapping("/cook/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<CookResponse> searchForCook(@RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int size,
                                                    @RequestParam(required = false) String keyword){
        return iCookService.searchCook(page, size, keyword);
    }

}
