package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.*;
import com.example.expressfood.dto.request.OrderRequest;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.dto.response.OrderResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.entities.*;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.OrderException;
import com.example.expressfood.exception.UserException;
import com.example.expressfood.service.*;
import com.example.expressfood.shared.MessagesEnum;
import com.example.expressfood.shared.RoleEnum;
import com.example.expressfood.shared.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    OrdersRepos ordersRepos;
    @Autowired
    ICartService iCartService;
    @Autowired
    StatusRepos statusRepos;
    @Autowired
    ClientRepos clientRepos;
    @Autowired
    OrderItemsRepos orderItemsRepos;
    @Autowired
    CookRepos cookRepos;
    @Autowired
    DeliveryPersonRepos deliveryPersonRepos;
    @Autowired
    IUserService iUserService;
    @Autowired
    IClientService clientService;
    @Autowired
    ICookService cookService;
    @Autowired
    IDeliveryService deliveryService;
    @Override
    @Transactional
    public OrderResponse addOrder(OrderRequest orderRequest) {
        Orders order = new Orders();
        Client client = clientService.getAuthenticatedClient();
        order.setClient(client);
        order.setCreatedDate(new Date());
        LocalDate deliveryDate = LocalDate.now().plusDays(2);
        order.setDeliveryDate(Date.from(deliveryDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Collection<CartItems> cartItemsList = iCartService.getCartItemByClient(order.getClient());
        Collection<OrderItems> orderItemsList = new ArrayList<>();
        AtomicReference<Double> totalPrice = new AtomicReference<>((double) 0);
        Status status = statusRepos.findByLabel(StatusEnum.PENDING.value());
        order.setStatus(status);
        order.setDescription(orderRequest.getDescription());
        order.setAddress(orderRequest.getAddress());
        Orders savedOrder = ordersRepos.save(order);
        cartItemsList.forEach( cartItem -> {
            OrderItems orderItem = new OrderItems();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQte(cartItem.getQte());
            if(cartItem.getQte() > 10)
                orderItem.setUnitePrice(cartItem.getProduct().getWholesalePrice());
            else
                orderItem.setUnitePrice(cartItem.getProduct().getUnitPrice());
            orderItem.setTotalPrice(orderItem.getQte() * orderItem.getUnitePrice());
            totalPrice.updateAndGet(v -> v + orderItem.getTotalPrice());
            orderItemsList.add(orderItem);
            orderItem.setOrder(savedOrder);
            orderItemsRepos.save(orderItem);
        });
        order.setOrderItems(orderItemsList);
        order.setTotalPrice(totalPrice.get());
        if(savedOrder.getOrderId() != null){
            iCartService.cleanCart(order.getClient());
            return OrderResponse.fromEntity(savedOrder);
        }
        else
            throw new OrderException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Orders order = ordersRepos.findById(orderId)
                .orElseThrow(() -> new OrderException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        OrderResponse orderResponse = OrderResponse.fromEntity(order);
        return orderResponse;
    }

    @Override
    public MessageResponse changeOrderStatus(Long orderId, Long statusId) {
        Orders order = ordersRepos.findById(orderId)
                .orElseThrow(() -> new OrderException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Status status = statusRepos.findById(statusId)
                .orElseThrow(() -> new OrderException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        order.setStatus(status);
        ordersRepos.save(order);
        return new MessageResponse(MessagesEnum.ORDER_STATUS_CHANGED_SUCCESSFULLY.getMessage());
    }

    @Override
    public PageResponse<OrderResponse> getOrderOfClient(int page, int size) {
        User user = iUserService.getAuthenticatedUser();
        Client client = clientRepos.findById(user.getId())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Page<Orders> ordersPage;
        ordersPage = ordersRepos.findByClient(client, PageRequest.of(page, size, Sort.by("createdDate").descending()));
        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(ordersPage.getTotalPages());
        List<OrderResponse> orderResponses = ordersPage.getContent().stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
        pageResponse.setContent(orderResponses);
        return pageResponse;
    }

    @Override
    public PageResponse<OrderResponse> getCurrentOrderOfClient(int page, int size) {
        User user = iUserService.getAuthenticatedUser();
        Client client = clientRepos.findById(user.getId())
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Page<Orders> ordersPage;
        ordersPage = ordersRepos.findClientOrdersWithStatusIdLessThan(client, 5L, PageRequest.of(page, size));
        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(ordersPage.getTotalPages());
        List<OrderResponse> orderResponses = ordersPage.getContent().stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
        pageResponse.setContent(orderResponses);
        return pageResponse;
    }

    @Override
    public MessageResponse setOrderToCook(Long orderId, Long cookId) {
        Orders order = ordersRepos.findById(orderId)
                .orElseThrow(() -> new OrderException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Cook cook = cookRepos.findById(cookId)
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        order.setCook(cook);
        ordersRepos.save(order);

        return new MessageResponse(MessagesEnum.ORDER_AFFECTED_COOK_SUCCESSFULLY.getMessage());
    }

    @Override
    public PageResponse<OrderResponse> getOrderOfCook(int page, int size) {
        Cook cook = cookService.getAuthenticatedCook();
        Page<Orders> ordersPage = ordersRepos.findByCookAndStatusOrStatus(cook, StatusEnum.IN_PROCESS.value(), StatusEnum.PENDING.value(), PageRequest.of(page, size));
        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(ordersPage.getTotalPages());
        List<OrderResponse> orderResponses = ordersPage.getContent().stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
        pageResponse.setContent(orderResponses);
        return pageResponse;
    }

    @Override
    public MessageResponse setOrderToDeliveryPerson(Long orderId, Long deliveryPersonId) {
        Orders order = ordersRepos.findById(orderId)
                .orElseThrow(() -> new OrderException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        DeliveryPerson deliveryPerson = deliveryPersonRepos.findById(deliveryPersonId)
                .orElseThrow(() -> new UserException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        order.setDeliveryPerson(deliveryPerson);
        ordersRepos.save(order);

        return new MessageResponse(MessagesEnum.ORDER_AFFECTED_DELIVERY_SUCCESSFULLY.getMessage());
    }

    @Override
    public PageResponse<OrderResponse> getOrderOfDeliveryPerson(int page, int size) {
        DeliveryPerson deliveryPerson = deliveryService.getAuthenticatedDelivery();
        Status status1 = statusRepos.findByLabel(StatusEnum.READY_FOR_DISPATCH.value());
        Status status2 = statusRepos.findByLabel(StatusEnum.DISPATCHED.value());
        Status status3 = statusRepos.findByLabel(StatusEnum.DELIVERED.value());
        Page<Orders> ordersPage = ordersRepos.findByDeliveryPersonAndStatusOrStatusOrStatus(deliveryPerson, status1, status2, status3, PageRequest.of(page, size));
        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(ordersPage.getTotalPages());
        List<OrderResponse> orderResponses = ordersPage.getContent().stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
        pageResponse.setContent(orderResponses);
        return pageResponse;
    }

    @Override
    public PageResponse<OrderResponse> getOrders(Long statusId, Date deliveryDate, int page, int size) {
        Page<Orders> ordersPage=null;
        if (statusId != null && deliveryDate != null) {
            Status status = statusRepos.findById(statusId)
                    .orElseThrow(() -> new OrderException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
            ordersPage = ordersRepos.findByStatusAndDeliveryDate(status, deliveryDate,PageRequest.of(page, size, Sort.by("createdDate").descending()));
        } else if (statusId != null) {
            Status status = statusRepos.findById(statusId)
                    .orElseThrow(() -> new OrderException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
            ordersPage = ordersRepos.findByStatus(status, PageRequest.of(page, size, Sort.by("createdDate").descending()));
        } else if (deliveryDate != null) {
            ordersPage = ordersRepos.findByDeliveryDate(deliveryDate, PageRequest.of(page, size, Sort.by("createdDate").descending()));
        } else {
            ordersPage = ordersRepos.findAll(PageRequest.of(page, size, Sort.by("createdDate").descending()));
        }
        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(page);
        pageResponse.setSize(size);
        pageResponse.setTotalPage(ordersPage.getTotalPages());
        List<OrderResponse> orderResponses = ordersPage.getContent().stream()
                .map(OrderResponse::fromEntity)
                .collect(Collectors.toList());
        pageResponse.setContent(orderResponses);
        return pageResponse;
    }
}
