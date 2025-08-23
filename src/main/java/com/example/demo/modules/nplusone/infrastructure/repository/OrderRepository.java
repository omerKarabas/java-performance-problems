package com.example.demo.modules.nplusone.repository;

import com.example.demo.modules.nplusone.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUserId(Long userId);
}
