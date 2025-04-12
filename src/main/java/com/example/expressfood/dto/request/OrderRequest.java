package com.example.expressfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class OrderRequest implements Serializable {
    private String address;
    private String description;
    private List<CustomisationRequest> customisation;

}
