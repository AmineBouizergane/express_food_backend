package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long roleId;
    @Column(unique = true, nullable = false)
    public String roleName;
}
