package com.example.expressfood.controller;

import com.example.expressfood.dto.response.CartResponse;
import com.example.expressfood.dto.response.RawMaterialsResponse;
import com.example.expressfood.service.IRawMaterials;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RawMaterialsController {

    private final IRawMaterials rawMaterialsService;

    @GetMapping("/raw-materials/{productId}/customised")
    @PreAuthorize("hasAuthority('USER')")
    public List<RawMaterialsResponse> getCustomisedRawMaterials(@PathVariable("productId") Long productId) {
        return rawMaterialsService.getCustomisedRawMaterials(productId);
    }
}
