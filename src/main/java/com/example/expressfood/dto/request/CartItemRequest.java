package com.example.expressfood.dto.request;

import com.example.expressfood.entities.CartItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class CartItemRequest {
    private Long cardItemId;
    private Long cardId;
    private Long productId;
    private int qte;

    public CartItems toEntity(){
        CartItems cartItems = new CartItems();
        BeanUtils.copyProperties(this, cartItems);
        return cartItems;
    }
}
