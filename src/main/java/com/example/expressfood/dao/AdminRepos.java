package com.example.expressfood.dao;

import com.example.expressfood.entities.Admin;
import com.example.expressfood.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepos extends JpaRepository<Admin, Long> {
}
