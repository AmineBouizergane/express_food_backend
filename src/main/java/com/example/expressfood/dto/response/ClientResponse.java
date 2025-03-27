package com.example.expressfood.dto.response;

import com.example.expressfood.entities.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor @ToString
public class ClientResponse extends UserResponse implements Serializable {
    public ClientResponse(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, Boolean isActivated) {
        super(id, firstName, lastName, birthDay, phoneNumber, address, avatarUrl, isActivated);
    }

    public static ClientResponse fromEntity(Client client){
        ClientResponse clientResponse = new ClientResponse();
        BeanUtils.copyProperties(client, clientResponse);
        return clientResponse;
    }
}
