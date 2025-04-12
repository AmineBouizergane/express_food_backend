package com.example.expressfood.dto.response;

import com.example.expressfood.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor @ToString
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String phoneNumber;
    private String address;
    private String avatarUrl;
    private Boolean isActivated;

    public static UserResponse fromEntity(User user){
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }
}
