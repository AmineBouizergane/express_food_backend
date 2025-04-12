package com.example.expressfood.dto.request;

import com.example.expressfood.entities.Cook;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CookRequest extends UserRequest implements Serializable {
    public CookRequest(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, Boolean isActivated, String userName, String encryptedPassword) {
        super(id, firstName, lastName, birthDay, userName, encryptedPassword, phoneNumber, address, avatarUrl);
    }

    public Cook toEntity(){
        Cook cook = new Cook();
        BeanUtils.copyProperties(this, cook);
        return cook;
    }
}
