package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.FeedBackRepos;
import com.example.expressfood.dto.response.FeedBackResponse;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.FeedBack;
import com.example.expressfood.entities.Product;
import com.example.expressfood.service.IClientService;
import com.example.expressfood.service.IFeedBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements IFeedBackService {

    private final IClientService clientService;
    private final FeedBackRepos feedBackRepos;

    @Override
    public void addFeedBack(Long productId, FeedBackResponse feedBackResponse) {
        Client client = clientService.getAuthenticatedClient();
        Product product = new Product();
        product.setProductId(productId);
        FeedBack feedBack = new FeedBack(null, feedBackResponse.getComment(), feedBackResponse.getRating(), client, product);
        feedBackRepos.save(feedBack);
    }
}
