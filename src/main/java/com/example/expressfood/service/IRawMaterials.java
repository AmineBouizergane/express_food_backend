package com.example.expressfood.service;

import com.example.expressfood.dto.response.ProductResponse;
import com.example.expressfood.dto.response.RawMaterialsResponse;

import java.util.List;

public interface IRawMaterials {
    List<RawMaterialsResponse> getCustomisedRawMaterials(Long productId);
}
