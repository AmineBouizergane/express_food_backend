package com.example.expressfood.dto.request;

import com.example.expressfood.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class UserRequest implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String userName;
    private String encryptedPassword;
    private String phoneNumber;
    private String address;
    private String avatarUrl;
}
