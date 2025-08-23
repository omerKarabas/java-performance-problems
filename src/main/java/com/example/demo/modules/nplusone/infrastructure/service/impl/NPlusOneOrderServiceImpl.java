package com.example.demo.modules.nplusone.infrastructure.service.impl;

import com.example.demo.modules.nplusone.entity.NPlusOneOrder;
import com.example.demo.modules.nplusone.infrastructure.repository.NPlusOneOrderRepository;
import com.example.demo.modules.nplusone.infrastructure.service.NPlusOneOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NPlusOneOrderServiceImpl implements NPlusOneOrderService {
    
    private final NPlusOneOrderRepository orderRepository;
    
    @Override
    public List<NPlusOneOrder> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
