package com.example.expressfood.dao;

import com.example.expressfood.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface OrdersRepos extends JpaRepository<Orders, Long> {
    Page<Orders> findByClient(Client client, Pageable pageable);
    @Query("SELECT o FROM Orders o WHERE o.client = :client AND o.status.statusId < :statusId")
    Page<Orders> findClientOrdersWithStatusIdLessThan(@Param("client") Client client, @Param("statusId") Long statusId, Pageable pageable);
    @Query("SELECT o FROM Orders o WHERE o.cook = :cook AND (o.status.label = :status1 OR o.status.label = :status2)")
    Page<Orders> findByCookAndStatusOrStatus(
            @Param("cook") Cook cook,
            @Param("status1") String status1,
            @Param("status2") String status2,
            Pageable pageable
    );
    Page<Orders> findByDeliveryPersonAndStatusOrStatusOrStatus(DeliveryPerson deliveryPerson, Status status1, Status status2, Status status3, Pageable pageable);
    Page<Orders> findByStatus(Status status, Pageable pageable);
    Page<Orders> findByDeliveryDate(Date deliveryDate, Pageable pageable);
    Page<Orders> findByStatusAndDeliveryDate(Status status, Date deliveryDate, Pageable pageable);
}
