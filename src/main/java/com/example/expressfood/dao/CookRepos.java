package com.example.expressfood.dao;

import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Cook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CookRepos extends JpaRepository<Cook, Long> {
    @Query("SELECT c FROM Cook c WHERE c.firstName LIKE %:keyword% OR c.lastName LIKE %:keyword%")
    Page<Cook> findByFirstNameOrLastNameContaining(String keyword, Pageable pageable);
    Optional<Cook> findByUserName(String userName);

}
