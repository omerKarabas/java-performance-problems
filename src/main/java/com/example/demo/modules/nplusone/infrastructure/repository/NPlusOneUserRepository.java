package com.example.demo.modules.nplusone.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.modules.nplusone.domain.entity.NPlusOneUser;

import java.util.List;

@Repository
public interface NPlusOneUserRepository extends JpaRepository<NPlusOneUser, Long> {
    
    @Query("SELECT u FROM NPlusOneUser u LEFT JOIN FETCH u.orders")
    List<NPlusOneUser> findAllWithOrdersOptimized();
}
