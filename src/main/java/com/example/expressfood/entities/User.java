package com.example.expressfood.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name="user_type", discriminatorType = DiscriminatorType.STRING )
@Data @AllArgsConstructor @NoArgsConstructor
@ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String firstName;
    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String lastName;
    @Past
    private Date birthDay;
    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d{10}")
    @Column(nullable = false)
    private String phoneNumber;
    private String address;
    private String avatarUrl;
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String userName;
    private String encryptedPassword;
    @NotNull
    @NotEmpty
    @Size(min = 1)
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles=new ArrayList<>();
    @NotNull
    @Column(nullable = false)
    private Boolean isActivated;
}
