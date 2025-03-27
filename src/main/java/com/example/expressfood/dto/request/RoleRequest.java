package com.example.expressfood.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleRequest implements Serializable {
    public Long roleId;
    public String roleName;
}
