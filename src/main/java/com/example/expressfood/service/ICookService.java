package com.example.expressfood.service;

import com.example.expressfood.dto.request.CookRequest;
import com.example.expressfood.dto.response.CookResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Cook;

import java.util.Collection;

public interface ICookService {
    CookResponse addCook(CookRequest cookRequest);
    CookResponse updateCook(CookRequest cookRequest);
    CookResponse getCookById(Long cookId);
    Collection<CookResponse> getAllCook();
    PageResponse<CookResponse> searchCook(int page, int size, String keyword);
    Cook getAuthenticatedCook();
}
