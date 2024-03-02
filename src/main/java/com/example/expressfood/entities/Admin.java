package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
@DiscriminatorValue("admin")
public class Admin extends User{
    public Admin(int id, String firstName, String lastName, Date birthDay, String phoneNumber, String address, String avatarUrl, String userName, String encryptedPassword, Collection<Role> roles, Boolean isActivated) {
        super(id, firstName, lastName, birthDay, phoneNumber, address, avatarUrl, userName, encryptedPassword, roles, isActivated);
    }
}
