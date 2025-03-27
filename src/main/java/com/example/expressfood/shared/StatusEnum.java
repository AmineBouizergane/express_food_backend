package com.example.expressfood.shared;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public enum StatusEnum {
    PENDING("PENDING"),
    IN_PROCESS("IN_PROCESS"),
    READY_FOR_DISPATCH("READY_FOR_DISPATCH"),
    DISPATCHED("DISPATCHED"),
    DELIVERED("DELIVERED"),
    PAID("PAID"),
    CANCELLED("CANCELLED"),
    REJECTED("REJECTED"),
    RETURNED("RETURNED");

    private final String status;

    public String value() {
        return status;
    }
}
