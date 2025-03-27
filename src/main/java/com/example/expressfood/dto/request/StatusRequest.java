package com.example.expressfood.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@NoArgsConstructor @Data
public class StatusRequest implements Serializable {
    private Long statusId;
    private String label;
}
