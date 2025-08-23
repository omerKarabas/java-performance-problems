package com.example.demo.modules.nplusone.infrastructure.service;

import com.example.demo.modules.nplusone.entity.NPlusOneOrder;

import java.util.List;

public interface NPlusOneOrderService {
    
    List<NPlusOneOrder> getOrdersByUserId(Long userId);
}
