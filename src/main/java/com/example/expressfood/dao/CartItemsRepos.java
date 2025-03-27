package com.example.expressfood.dao;

import com.example.expressfood.entities.Cart;
import com.example.expressfood.entities.CartItems;
import com.example.expressfood.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CartItemsRepos extends JpaRepository<CartItems, Long> {
    Collection<CartItems> findByCart(Cart cart);
    Optional<CartItems> findByCartAndProduct(Cart cart, Product product);
}
