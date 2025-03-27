package com.example.expressfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class CategoryRequest implements Serializable{
    private Long categoryId;
    private String name;
}
