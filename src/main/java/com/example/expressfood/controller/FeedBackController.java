package com.example.expressfood.controller;

import com.example.expressfood.dto.response.FeedBackResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.service.IFeedBackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedBackController {

    private final IFeedBackService feedBackService;

    @PostMapping("/feed-back")
    @PreAuthorize("hasAuthority('CLIENT')")
    public void addItemToCart(@RequestParam Long productId,
                              @RequestBody FeedBackResponse feedBackResponse) {
        feedBackService.addFeedBack(productId, feedBackResponse);
    }

}
