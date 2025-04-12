package com.example.expressfood.dto.response;

import com.example.expressfood.entities.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@ToString
@AllArgsConstructor @NoArgsConstructor @Data
public class OrderItemsResponse {
    private Long orderItemId;
    private ProductResponse product;
    private int qte;
    private double unitePrice;
    private double totalPrice;

    public static OrderItemsResponse fromEntity(OrderItems orderItems) {
        OrderItemsResponse orderItemsResponse = new OrderItemsResponse();
        BeanUtils.copyProperties(orderItems, orderItemsResponse);
        orderItemsResponse.setProduct(ProductResponse.fromEntity(orderItems.getProduct()));
        return orderItemsResponse;
    }
}
