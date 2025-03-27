package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class OrderItems {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    @ManyToOne
    private Orders order;
    @ManyToOne
    private Product product;
    private int qte;
    private double unitePrice;
    private double totalPrice;
}
