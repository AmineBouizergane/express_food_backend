package com.example.expressfood.dao;

import com.example.expressfood.entities.DeliveryPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeliveryPersonRepos extends JpaRepository<DeliveryPerson, Long> {
    @Query("SELECT d FROM DeliveryPerson d WHERE d.firstName LIKE %:keyword% OR d.lastName LIKE %:keyword%")
    Page<DeliveryPerson> findByFirstNameOrLastNameContaining(String keyword, Pageable pageable);
    Optional<DeliveryPerson> findByUserName(String userName);
}
