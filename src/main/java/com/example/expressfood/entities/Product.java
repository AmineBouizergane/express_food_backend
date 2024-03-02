package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String name;
    @ManyToOne
    private Category category;
    private double unitPrice;
    private double wholesalePrice;
    @ManyToOne
    private Unite unite;
    @OneToMany
    private Collection<OrderItems> orderItems;

}
