package com.example.expressfood.service;

import com.example.expressfood.dto.request.UniteRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.UniteResponse;

import java.util.Collection;

public interface IUniteService {

    UniteResponse addOrUpdateUnite(UniteRequest uniteRequest);
    MessageResponse deleteUnite(Long uniteId);
    Collection<UniteResponse> getAllUnites();

}
