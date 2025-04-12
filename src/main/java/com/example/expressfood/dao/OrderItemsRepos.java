package com.example.expressfood.dao;

import com.example.expressfood.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepos extends JpaRepository<OrderItems, Long> {
}
