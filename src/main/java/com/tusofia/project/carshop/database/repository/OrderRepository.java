package com.tusofia.project.carshop.database.repository;

import com.tusofia.project.carshop.database.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByCustomer(String username);
}
