package com.example.expressfood.shared;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public enum RoleEnum {
    ADMIN("ADMIN"),
    COOK("COOK"),
    DELIVERY("DELIVERY"),
    CLIENT("CLIENT"),
    USER("USER");

    private final String role;

    public String value() {
        return role;
    }
}
