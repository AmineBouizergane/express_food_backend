package com.example.expressfood.dto.response;

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
public class CookResponse extends UserResponse implements Serializable {
    public CookResponse(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, Boolean isActivated) {
        super(id, firstName, lastName, birthDay, phoneNumber, address, avatarUrl, isActivated);
    }

    public static CookResponse fromEntity(Cook cook){
        CookResponse cookResponse = new CookResponse();
        BeanUtils.copyProperties(cook, cookResponse);
        return cookResponse;
    }
}
