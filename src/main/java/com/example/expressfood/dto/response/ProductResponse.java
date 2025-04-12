package com.example.expressfood.dto.response;

import com.example.expressfood.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class ProductResponse implements Serializable {
    private Long productId;
    private String name;
    private String imageUrl;
    private String description;
    private LocalDateTime createdAt;
    private double unitPrice;
    private UniteResponse unite;
    private CategoryResponse category;
    private Boolean isDeleted;
    private Boolean isAvailable;
    private double avgRating;
    private Collection<FeedBackResponse> feedBacks;
    private Collection<RawMaterialsResponse> rawMaterials;

    public static ProductResponse fromEntity(Product product) {
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);

        UniteResponse uniteResponse = new UniteResponse();
        BeanUtils.copyProperties(product.getUnite(), uniteResponse);

        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(product.getCategory(), categoryResponse);

        productResponse.setCategory(categoryResponse);
        productResponse.setUnite(uniteResponse);
        productResponse.setFeedBacks(product.getFeedBacks().stream()
                .map(FeedBackResponse::fromEntity)
                .collect(Collectors.toList()));
        productResponse.setRawMaterials(product.getRawMaterials().stream()
                .map(RawMaterialsResponse::fromEntity)
                .collect(Collectors.toList()));
        return productResponse;
    }
}
