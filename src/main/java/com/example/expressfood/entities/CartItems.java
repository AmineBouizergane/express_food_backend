package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class CartItems {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardItemId;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Product product;
    private int qte;
}
