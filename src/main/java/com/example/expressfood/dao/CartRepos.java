package com.example.expressfood.dao;

import com.example.expressfood.entities.Admin;
import com.example.expressfood.entities.Cart;
import com.example.expressfood.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepos extends JpaRepository<Cart, Long> {
    Optional<Cart> findCardByClient(Client c);

    @Query("SELECT SUM(ci.qte) FROM Cart c JOIN c.cartItems ci WHERE c.client = :client")
    int countCartItemsByClient(Client client);
}
