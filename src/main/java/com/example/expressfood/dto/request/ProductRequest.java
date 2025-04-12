package com.example.expressfood.dto.request;

import com.example.expressfood.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class ProductRequest implements Serializable {
    private Long productId;
    private String name;
    private String imageUrl;
    private String description;
    private double unitPrice;
    private double wholesalePrice;
    private long unite;
    private long category;
    private Boolean isAvailable;


    public Product toEntity(){
        Product product = new Product();
        BeanUtils.copyProperties(this, product);
        return product;
    }
}
