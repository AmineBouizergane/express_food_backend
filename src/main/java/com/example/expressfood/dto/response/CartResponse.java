package com.example.expressfood.dto.response;

import com.example.expressfood.entities.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class CartResponse implements Serializable {
    private Long cartId;
    private Long clientId;
    private Collection<CartItemsResponse> cartItems;

    public static CartResponse fromEntity(Cart cart){
        CartResponse cartResponse = new CartResponse();
        BeanUtils.copyProperties(cart, cartResponse);
        cartResponse.setClientId(cart.getClient().getId());
        cartResponse.cartItems = new ArrayList<>();
        cartResponse.setCartItems(cart.getCartItems().stream()
                .map(CartItemsResponse::fromEntity)
                .collect(Collectors.toList()));
        return cartResponse;
    }

}
