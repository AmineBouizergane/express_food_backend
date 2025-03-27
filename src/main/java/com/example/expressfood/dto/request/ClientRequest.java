package com.example.expressfood.dto.request;

import com.example.expressfood.entities.Client;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class ClientRequest extends UserRequest implements Serializable {
    public ClientRequest(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, Boolean isActivated, String userName, String encryptedPassword) {
        super(id, firstName, lastName, birthDay, userName, encryptedPassword, phoneNumber, address, avatarUrl);
    }

    public Client toEntity(){
        Client client = new Client();
        BeanUtils.copyProperties(this, client);
        return client;
    }
}
