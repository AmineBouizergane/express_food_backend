package com.example.expressfood.dto.response;

import com.example.expressfood.entities.Product;
import com.example.expressfood.entities.RawMaterials;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RawMaterialsResponse implements Serializable {
    private Long id;
    private String label;
    private Boolean isCustomised;
    private Boolean isAvailable;

    public static RawMaterialsResponse fromEntity(RawMaterials rawMaterial) {
        RawMaterialsResponse rawMaterialsResponse = new RawMaterialsResponse();
        BeanUtils.copyProperties(rawMaterial, rawMaterialsResponse);
        return rawMaterialsResponse;
    }
}
