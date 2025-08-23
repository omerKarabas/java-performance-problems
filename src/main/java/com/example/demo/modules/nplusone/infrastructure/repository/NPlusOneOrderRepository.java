package com.example.demo.modules.nplusone.infrastructure.repository;

import com.example.demo.modules.nplusone.entity.NPlusOneOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NPlusOneOrderRepository extends JpaRepository<NPlusOneOrder, Long> {
    
    List<NPlusOneOrder> findByUserId(Long userId);
}
