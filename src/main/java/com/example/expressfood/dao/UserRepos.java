package com.example.expressfood.dao;

import com.example.expressfood.entities.Unite;
import com.example.expressfood.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepos extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
