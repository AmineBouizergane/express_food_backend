package com.example.expressfood.service;

import com.example.expressfood.dto.request.DeliveryPersonRequest;
import com.example.expressfood.dto.response.DeliveryPersonResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.entities.DeliveryPerson;

import java.util.Collection;

public interface IDeliveryService {
    DeliveryPersonResponse addDeliveryPerson(DeliveryPersonRequest deliveryPersonRequest);
    DeliveryPersonResponse updateDeliveryPerson(DeliveryPersonRequest deliveryPersonRequest);
    DeliveryPersonResponse getDeliveryPersonById(Long deliveryPersonId);
    Collection<DeliveryPersonResponse> getAllDeliveryPerson();
    PageResponse<DeliveryPersonResponse> searchDeliveryPerson(int page, int size, String keyword);
    DeliveryPerson getAuthenticatedDelivery();
}
