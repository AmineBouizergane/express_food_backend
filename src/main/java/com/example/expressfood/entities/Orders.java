package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Orders {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long orderId;
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
    @OneToMany (mappedBy = "order", cascade = CascadeType.ALL)
    private Collection<OrderItems> orderItems;
    @NonNull
    private String address;
    @Column(length = 10_000)
    private String description;
    private double totalPrice;

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", client=" + client +
                ", cook=" + cook +
                ", deliveryPerson=" + deliveryPerson +
                ", createdDate=" + createdDate +
                ", deliveryDate=" + deliveryDate +
                ", status=" + status +
                ", orderItems=" + orderItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
