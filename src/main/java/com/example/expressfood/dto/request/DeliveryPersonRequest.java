package com.example.expressfood.dto.request;

import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.DeliveryPerson;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class DeliveryPersonRequest extends UserRequest implements Serializable {
    public DeliveryPersonRequest(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, Boolean isActivated, String userName, String encryptedPassword) {
        super(id, firstName, lastName, birthDay, userName, encryptedPassword, phoneNumber, address, avatarUrl);
    }

    public DeliveryPerson toEntity(){
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        BeanUtils.copyProperties(this, deliveryPerson);
        return deliveryPerson;
    }
}
