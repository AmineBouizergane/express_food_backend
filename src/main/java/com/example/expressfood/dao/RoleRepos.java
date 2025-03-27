package com.example.expressfood.dao;

import com.example.expressfood.entities.Orders;
import com.example.expressfood.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepos extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
