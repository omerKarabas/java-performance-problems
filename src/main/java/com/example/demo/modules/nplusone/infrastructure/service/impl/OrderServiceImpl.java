package com.example.demo.modules.nplusone.infrastructure.service.impl;

import com.example.demo.modules.nplusone.domain.entity.Order;
import com.example.demo.modules.nplusone.infrastructure.repository.OrderRepository;
import com.example.demo.modules.nplusone.infrastructure.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("nplusOneOrderServiceImpl")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        log.debug("Fetching orders for user ID: {}", userId);
        // This will generate: SELECT * FROM orders WHERE user_id = ?
        return orderRepository.findByUserId(userId);
    }
}
