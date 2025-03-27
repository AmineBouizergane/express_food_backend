package com.example.expressfood.controller;

import com.example.expressfood.dto.request.ClientRequest;
import com.example.expressfood.dto.request.OrderRequest;
import com.example.expressfood.dto.response.ClientResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.OrderResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.service.ICartService;
import com.example.expressfood.service.IClientService;
import com.example.expressfood.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin("*")
public class OrderController {
    @Autowired
    IOrderService iOrderService;

    @PostMapping("/order")
    @PostAuthorize("hasAuthority('CLIENT')")
    public OrderResponse addOrder(@RequestBody OrderRequest orderRequest){
        return iOrderService.addOrder(orderRequest);
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('USER')")
    public OrderResponse getOrderById(@PathVariable Long orderId){
        return iOrderService.getOrderById(orderId);
    }

    @PutMapping("/order")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('COOK') OR hasAuthority('DELIVERY')")
    public MessageResponse changeOrderStatus(@RequestParam Long orderId,@RequestParam Long statusId){
        return iOrderService.changeOrderStatus(orderId, statusId);
    }

    @GetMapping("/order/client")
    @PreAuthorize("hasAuthority('CLIENT')")
    public PageResponse<OrderResponse> getOrderOfClient(@RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size){
            return iOrderService.getOrderOfClient(page, size);
    }

    @GetMapping("/order/client/current")
    @PreAuthorize("hasAuthority('CLIENT')")
    public PageResponse<OrderResponse> getCurrentOrderOfClient(@RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size){
        return iOrderService.getCurrentOrderOfClient(page, size);
    }

    @PutMapping("/order/cook")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MessageResponse setOrderToCook(@RequestParam Long orderId,@RequestParam Long cookId){
        return iOrderService.setOrderToCook(orderId, cookId);
    }

    @GetMapping("/order/cook")
    @PreAuthorize("hasAuthority('COOK')")
    public PageResponse<OrderResponse> getOrderOfCook(@RequestParam(required = false, defaultValue = "0") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size){
        return iOrderService.getOrderOfCook(page, size);
    }

    @PutMapping("/order/deliveryPerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MessageResponse setOrderToDeliveryPerson(@RequestParam Long orderId,@RequestParam Long deliveryPersonId){
        return iOrderService.setOrderToDeliveryPerson(orderId, deliveryPersonId);
    }

    @GetMapping("/order/deliveryPerson")
    @PreAuthorize("hasAuthority('DELIVERY')")
    public PageResponse<OrderResponse> getOrderOfDeliveryPerson(@RequestParam(required = false, defaultValue = "0") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size){
        return iOrderService.getOrderOfDeliveryPerson(page, size);
    }

    @GetMapping("/order")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<OrderResponse> getOrder(@RequestParam(required = false) Long statusId,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deliveryDate,
                                                        @RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size){
        return iOrderService.getOrders(statusId, deliveryDate, page, size);
    }


}
