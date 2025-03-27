package com.example.expressfood.dto.request;

import com.example.expressfood.dto.response.CategoryResponse;
import com.example.expressfood.dto.response.UniteResponse;
import com.example.expressfood.entities.Category;
import com.example.expressfood.entities.Product;
import com.example.expressfood.entities.Unite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
