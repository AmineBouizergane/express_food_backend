package com.example.expressfood.service;

import com.example.expressfood.dto.request.OrderRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.OrderResponse;
import com.example.expressfood.dto.response.PageResponse;

import java.util.Date;

public interface IOrderService {
    OrderResponse addOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(Long orderId);
    MessageResponse changeOrderStatus(Long orderId, Long statusId);
    PageResponse<OrderResponse> getOrderOfClient(int page, int size);
    PageResponse<OrderResponse> getCurrentOrderOfClient(int page, int size);
    MessageResponse setOrderToCook(Long orderId, Long cookId);
    PageResponse<OrderResponse> getOrderOfCook(int page, int size);
    MessageResponse setOrderToDeliveryPerson(Long orderId, Long deliveryPersonId);
    PageResponse<OrderResponse> getOrderOfDeliveryPerson(int page, int size);
    PageResponse<OrderResponse> getOrders(Long statusId, Date deliveryDate, int page, int size);
}
