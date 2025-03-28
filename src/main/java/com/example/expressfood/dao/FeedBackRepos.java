package com.example.expressfood.dao;

import com.example.expressfood.entities.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedBackRepos extends JpaRepository<FeedBack, Long> {
    @Query("SELECT AVG(f.rating) FROM FeedBack f WHERE f.product.productId = :productId")
    Double getAverageRatingByProductId(@Param("productId") Long productId);
}
