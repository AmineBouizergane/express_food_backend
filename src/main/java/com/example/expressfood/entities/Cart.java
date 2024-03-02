package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    @ManyToOne
    private Client client;
    @OneToMany (mappedBy = "cart")
    private List<CartItems> cartItems;
}
