package com.example.expressfood.dto.response;

import com.example.expressfood.entities.DeliveryPerson;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor @ToString
public class DeliveryPersonResponse extends UserResponse implements Serializable {
    public DeliveryPersonResponse(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, Boolean isActivated) {
        super(id, firstName, lastName, birthDay, phoneNumber, address, avatarUrl, isActivated);
    }
    public static DeliveryPersonResponse fromEntity(DeliveryPerson deliveryPerson){
        DeliveryPersonResponse deliveryPersonResponse = new DeliveryPersonResponse();
        BeanUtils.copyProperties(deliveryPerson, deliveryPersonResponse);
        return deliveryPersonResponse;
    }
}
