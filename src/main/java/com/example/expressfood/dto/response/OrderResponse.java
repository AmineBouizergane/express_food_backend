package com.example.expressfood.dto.response;

import com.example.expressfood.entities.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class OrderResponse implements Serializable {

    private Long orderId;
    private ClientResponse client;
    private CookResponse cook;
    private DeliveryPersonResponse deliveryPerson;
    private Date createdDate;
    private Date deliveryDate;
    private StatusResponse status;
    private Collection<OrderItemsResponse> orderItems;
    private String address;
    private String description;
    private double totalPrice;

    public static OrderResponse fromEntity(Orders order) {
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        orderResponse.setClient(ClientResponse.fromEntity(order.getClient()));
        if(order.getCook() != null)
            orderResponse.setCook(CookResponse.fromEntity(order.getCook()));
        if(order.getDeliveryPerson() != null)
            orderResponse.setDeliveryPerson(DeliveryPersonResponse.fromEntity(order.getDeliveryPerson()));
        orderResponse.setStatus(StatusResponse.fromEntity(order.getStatus()));
        orderResponse.setOrderItems(order.getOrderItems().stream()
                .map(OrderItemsResponse::fromEntity)
                .collect(Collectors.toList()));
        return orderResponse;
    }

}
