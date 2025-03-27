package com.example.expressfood.dto.request;

import com.example.expressfood.entities.Cart;
import com.example.expressfood.entities.CartItems;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
