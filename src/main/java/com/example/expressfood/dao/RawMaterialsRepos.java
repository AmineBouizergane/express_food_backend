package com.example.expressfood.dao;

import com.example.expressfood.entities.RawMaterials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RawMaterialsRepos extends JpaRepository<RawMaterials, Long> {
    List<RawMaterials> findByIsCustomisedTrue();
}
