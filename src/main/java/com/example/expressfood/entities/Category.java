package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String name;

    @OneToMany (mappedBy = "category")
    private List<Product> productList;
}
