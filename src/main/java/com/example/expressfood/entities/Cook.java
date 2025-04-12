package com.example.expressfood.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Data
@DiscriminatorValue("cook")
public class Cook extends User{
    @OneToMany(mappedBy = "cook")
    private Collection<Orders> orders;
}
