package com.example.expressfood.dao;

import com.example.expressfood.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepos extends JpaRepository<Admin, Long> {
}
