package com.example.expressfood.service;

import com.example.expressfood.dto.response.FeedBackResponse;

public interface IFeedBackService {
    void addFeedBack(Long productId, FeedBackResponse feedBackResponse);
}
