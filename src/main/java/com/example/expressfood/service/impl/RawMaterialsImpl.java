package com.example.expressfood.service.impl;

import com.example.expressfood.dao.ProductRepos;
import com.example.expressfood.dto.response.ProductResponse;
import com.example.expressfood.dto.response.RawMaterialsResponse;
import com.example.expressfood.entities.RawMaterials;
import com.example.expressfood.service.IRawMaterials;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RawMaterialsImpl implements IRawMaterials {

    private final ProductRepos productRepos;

    @Override
    public List<RawMaterialsResponse> getCustomisedRawMaterials(Long productId) {
        List<RawMaterials> rawMaterials = productRepos.findCustomizedRawMaterialsByProductId(productId);
        return rawMaterials.stream()
                .map(RawMaterialsResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
