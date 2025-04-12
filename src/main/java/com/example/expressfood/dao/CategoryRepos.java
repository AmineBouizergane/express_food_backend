package com.example.expressfood.dao;

import com.example.expressfood.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepos extends JpaRepository<Category, Long> {
}
