package com.example.expressfood.dao;

import com.example.expressfood.entities.Category;
import com.example.expressfood.entities.Product;
import com.example.expressfood.entities.RawMaterials;
import com.example.expressfood.entities.Unite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepos extends JpaRepository<Product, Long> {
    Optional<Product> findByProductIdAndIsDeletedFalse(Long productId);
    Page<Product> findProductByIsDeletedFalse(Pageable pageable);
    Page<Product> findProductByIsDeletedFalseAndIsAvailableTrue(Pageable pageable);
    Page<Product> findProductByCategoryAndIsDeletedFalse(Category category, Pageable pageable);
    Page<Product> findProductByCategoryAndIsDeletedFalseAndIsAvailableTrue(Category category, Pageable pageable);
    Page<Product> findByNameContainingAndIsDeletedFalse(String name, Pageable pageable);
    Page<Product> findByNameContainingAndIsDeletedFalseAndIsAvailableTrue(String name, Pageable pageable);

    Page<Product> findByCategoryAndNameContainingAndIsDeletedFalseAndIsAvailableTrue(Category category, String name, Pageable pageable);
    @Query("SELECT COUNT(p) FROM Product p WHERE p.unite = :unite")
    Long countProductsByUnit(Unite unite);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category = :category")
    Long countProductsByCategory(Category category);

    @Query("SELECT rm FROM Product p JOIN p.rawMaterials rm WHERE p.productId = :productId AND rm.isCustomised = true")
    List<RawMaterials> findCustomizedRawMaterialsByProductId(Long productId);

}
