package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.ProductRepos;
import com.example.expressfood.dao.UniteRepos;
import com.example.expressfood.dto.request.UniteRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.dto.response.UniteResponse;
import com.example.expressfood.entities.Unite;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.UniteException;
import com.example.expressfood.service.IUniteService;
import com.example.expressfood.shared.MessagesEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UniteServiceImpl implements IUniteService {

    @Autowired
    UniteRepos uniteRepos;

    @Autowired
    ProductRepos productRepos;
    @Override
    public UniteResponse addUnite(UniteRequest uniteRequest) {
        if(uniteRequest.getLabel().equals(""))
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
    public UniteResponse updateUnite(UniteRequest uniteRequest) {
        if(uniteRequest.getLabel().equals(""))
            throw new UniteException(ErrorMessages.MISSING_REQUIRED_FILED.getErrorMessage());
        Unite unite = new Unite();
        BeanUtils.copyProperties(uniteRequest, unite);
        Unite updatedUnite = uniteRepos.save(unite);
        UniteResponse uniteResponse = new UniteResponse();
        BeanUtils.copyProperties(updatedUnite, uniteResponse);
        return uniteResponse;
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
