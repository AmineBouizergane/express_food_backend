package com.example.expressfood.shared;

import lombok.AllArgsConstructor;

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
