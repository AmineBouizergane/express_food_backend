package com.example.expressfood.dto.request;

import com.example.expressfood.shared.CustomisationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomisationRequest implements Serializable {
    private Long itemId;
    private Long rawMaterialId;
    private CustomisationStatus customisationStatus;
}
