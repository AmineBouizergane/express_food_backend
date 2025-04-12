package com.example.expressfood.entities;

import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.ProductException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NonNull
    private String name;
    private String imageUrl;
    @Column(length = 1000)
    private String description;
    private LocalDateTime createdAt;
    @NonNull
    private Double unitPrice;
    @ManyToOne
    @NonNull
    private Unite unite;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<OrderItems> orderItems;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<FeedBack> feedBacks;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<RawMaterials> rawMaterials;
    private Boolean isDeleted;
    private Boolean isAvailable;

    public void isValid(){

        if(this.name.isEmpty() || this.unitPrice < 0 || this.category == null || this.isDeleted == null || this.isAvailable == null)
            throw new ProductException(ErrorMessages.INVALID_ENTITY.getErrorMessage());

    }

}
