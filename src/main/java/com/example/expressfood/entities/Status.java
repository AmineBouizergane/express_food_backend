package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Status {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;
    private String label;
    @OneToMany(mappedBy = "status")
    private Collection<Orders> orders;
}
