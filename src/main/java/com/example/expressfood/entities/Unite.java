package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Unite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uniteId;
    private String label;
    @OneToMany (mappedBy = "unite")
    private Collection<Product> products;
}
