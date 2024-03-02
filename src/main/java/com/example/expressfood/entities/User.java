package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="user_type", discriminatorType = DiscriminatorType.STRING )
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private Date birthDay;
    @Column(nullable = false)
    private String phoneNumber;
    private String address;
    private String avatarUrl;
    @Column(unique = true, nullable = false)
    private String userName;
    @Column(nullable = false)
    private String encryptedPassword;
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles=new ArrayList<>();
    @Column(nullable = false)
    private Boolean isActivated;
}
