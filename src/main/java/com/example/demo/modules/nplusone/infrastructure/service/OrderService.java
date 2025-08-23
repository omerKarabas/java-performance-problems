package com.example.demo.modules.nplusone.service;

import com.example.demo.modules.nplusone.entity.Order;
import java.util.List;

public interface OrderService {
    
    List<Order> getOrdersByUserId(Long userId);
}
