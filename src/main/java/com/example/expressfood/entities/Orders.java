package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Orders {
    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int orderId;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Cook cook;
    @ManyToOne
    private DeliveryPerson deliveryPerson;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @ManyToOne
    private Status status;
    @OneToMany (mappedBy = "order")
    private Collection<OrderItems> orderItems;
    private double totalPrice;
}
