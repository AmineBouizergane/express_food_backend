package com.example.expressfood.service;

import com.example.expressfood.dto.response.CartResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.entities.CartItems;
import com.example.expressfood.entities.Client;

import java.util.Collection;

public interface ICartService {
    CartResponse getCartOfClient();
    MessageResponse addItemToCart(Long itemId, int qty);
    MessageResponse removeItemFromCart(Long itemId);
    MessageResponse changeItemQuantity(Long itemId, int qty);
    void cleanCart(Client client);
    Collection<CartItems> getCartItemByClient(Client client);

    int getCartItemCount();
}
