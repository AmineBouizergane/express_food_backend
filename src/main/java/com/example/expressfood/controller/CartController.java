package com.example.expressfood.controller;

import com.example.expressfood.dto.request.CartItemRequest;
import com.example.expressfood.dto.response.CartResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.entities.Cart;
import com.example.expressfood.entities.CartItems;
import com.example.expressfood.entities.Client;
import com.example.expressfood.exception.CartException;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.UserException;
import com.example.expressfood.service.ICartService;
import com.example.expressfood.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    ICartService iCartService;

    @GetMapping("/cart")
    @PreAuthorize("hasAuthority('CLIENT')")
    public CartResponse getCartOfClient() {
        return iCartService.getCartOfClient();
    }

    @GetMapping("/cartItemCount")
    @PreAuthorize("hasAuthority('CLIENT')")
    public int getCartItemCount() {
        return iCartService.getCartItemCount();
    }

    @PostMapping("/cart")
    @PreAuthorize("hasAuthority('CLIENT')")
    public MessageResponse addItemToCart(@RequestParam Long itemId,
                                         @RequestParam(required = false, defaultValue = "1") int qty) {
         return iCartService.addItemToCart(itemId, qty);
    }

    @DeleteMapping("/cart")
    @PreAuthorize("hasAuthority('CLIENT')")
    public MessageResponse removeItemFromCart(@RequestParam Long itemId) {
        return iCartService.removeItemFromCart(itemId);
    }

    @PutMapping("/cart")
    @PreAuthorize("hasAuthority('CLIENT')")
    public MessageResponse changeItemQuantity(@RequestParam Long itemId,@RequestParam int qty) {
        return iCartService.changeItemQuantity(itemId, qty);
    }

}
