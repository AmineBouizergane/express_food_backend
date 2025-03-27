package com.example.expressfood.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleResponse implements Serializable {
    public Long roleId;
    public String roleName;
}
