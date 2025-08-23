package com.example.demo.modules.cache.generatekey.timestampmismatch.infrastructure.repository;

import com.example.demo.modules.cache.generatekey.timestampmismatch.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByCustomerNameAndStatus(String customerName, String status);
}
