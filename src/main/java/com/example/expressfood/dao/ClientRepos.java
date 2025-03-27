package com.example.expressfood.dao;

import com.example.expressfood.entities.Category;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Cook;
import com.example.expressfood.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepos extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE c.firstName LIKE %:keyword% OR c.lastName LIKE %:keyword%")
    Page<Client> findByFirstNameOrLastNameContaining(String keyword, Pageable pageable);

    Optional<Client> findByUserName(String userName);
}
