package com.example.expressfood.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor @Data
@DiscriminatorValue("client")
public class Client extends User{

    @OneToOne(mappedBy = "client")
    private Cart cart;
    @OneToMany(mappedBy = "client")
    private Collection<Orders> orders;
    public Client(Long id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, String userName, String encryptedPassword, Collection<Role> roles, Boolean isActivated) {
        super(id, firstName, lastName, birthDay, phoneNumber, address, avatarUrl, userName, encryptedPassword, roles, isActivated);
    }


}
