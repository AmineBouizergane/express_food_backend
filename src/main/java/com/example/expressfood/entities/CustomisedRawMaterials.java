package com.example.expressfood.entities;

import com.example.expressfood.shared.CustomisationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomisedRawMaterials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CustomisationStatus type;
    @ManyToOne
    private RawMaterials rawMaterial;
    @ManyToOne
    private OrderItems orderItem;
}
