package com.example.expressfood.service.impl;

import com.example.expressfood.dao.ProductRepos;
import com.example.expressfood.dao.UniteRepos;
import com.example.expressfood.dto.request.UniteRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.UniteResponse;
import com.example.expressfood.entities.Unite;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.UniteException;
import com.example.expressfood.service.IUniteService;
import com.example.expressfood.shared.MessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UniteServiceImpl implements IUniteService {

    private final UniteRepos uniteRepos;

    private final ProductRepos productRepos;
    @Override
    public UniteResponse addOrUpdateUnite(UniteRequest uniteRequest) {
        if(uniteRequest.getLabel().isEmpty())
            throw new UniteException(ErrorMessages.MISSING_REQUIRED_FILED.getErrorMessage());
        Unite unite = new Unite();
        BeanUtils.copyProperties(uniteRequest, unite);
        Unite savedUnite = uniteRepos.save(unite);
        UniteResponse uniteResponse = new UniteResponse();
        BeanUtils.copyProperties(savedUnite, uniteResponse);
        return uniteResponse;
    }

    @Override
    public MessageResponse deleteUnite(Long uniteId) {
        Unite unite = uniteRepos.findById(uniteId)
                .orElseThrow(() -> new UniteException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Long productsCount = productRepos.countProductsByUnit(unite);
        if(productsCount > 0)
            throw new UniteException(ErrorMessages.CANT_BE_DELETE.getErrorMessage());
        uniteRepos.delete(unite);
        return new MessageResponse(MessagesEnum.UNITE_DELETED_SUCCESSFULLY.getMessage());

    }

    @Override
    public Collection<UniteResponse> getAllUnites() {
        Collection<Unite> unitePage = uniteRepos.findAll();
        Collection<UniteResponse> uniteResponseList = new ArrayList<>();
        unitePage.forEach( u -> {
            UniteResponse uniteResponse = new UniteResponse();
            BeanUtils.copyProperties(u, uniteResponse);
            uniteResponseList.add(uniteResponse);
        });
        return uniteResponseList;
    }
}
