package com.example.expressfood.dto.response;

import com.example.expressfood.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class CartItemsResponse implements Serializable {
    private Long cardItemId;
    private ProductResponse product;
    private int qte;
    private double price;

    public static CartItemsResponse fromEntity(CartItems cartItems) {
        CartItemsResponse cartItemsResponse = new CartItemsResponse();
        BeanUtils.copyProperties(cartItems, cartItemsResponse);
        cartItemsResponse.setProduct(ProductResponse.fromEntity(cartItems.getProduct()));
        cartItemsResponse.price = cartItems.getProduct().getUnitPrice();

        return cartItemsResponse;
    }
}
