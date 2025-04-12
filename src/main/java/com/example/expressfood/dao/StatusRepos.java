package com.example.expressfood.dao;

import com.example.expressfood.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepos extends JpaRepository<Status, Long> {
    Status findByLabel(String label);
}
