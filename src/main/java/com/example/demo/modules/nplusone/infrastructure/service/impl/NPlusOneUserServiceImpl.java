package com.example.demo.modules.nplusone.infrastructure.service.impl;

import com.example.demo.modules.nplusone.api.dto.NPlusOneUserDTO;
import com.example.demo.modules.nplusone.api.mapper.NPlusOneUserMapper;
import com.example.demo.modules.nplusone.entity.NPlusOneUser;
import com.example.demo.modules.nplusone.infrastructure.repository.NPlusOneUserRepository;
import com.example.demo.modules.nplusone.infrastructure.service.NPlusOneUserService;
import com.example.demo.modules.nplusone.infrastructure.service.NPlusOneOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NPlusOneUserServiceImpl implements NPlusOneUserService {
    
    private final NPlusOneUserRepository userRepository;
    private final NPlusOneOrderService orderService;
    private final NPlusOneUserMapper userMapper;
    
    @Override
    public List<NPlusOneUserDTO> getUsersWithOrdersNPlusOne() {
        // N+1 Problem: First query gets all users
        List<NPlusOneUser> users = userRepository.findAll();
        
        // Then for each user, we make a separate query to get orders
        // This results in N+1 queries (1 for users + N for each user's orders)
        return users.stream()
                .map(user -> {
                    // This will trigger a separate query for each user
                    user.getOrders().size(); // Force lazy loading
                    return userMapper.toDTO(user);
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<NPlusOneUserDTO> getUsersWithOrdersOptimized() {
        // Optimized solution: Single query with JOIN FETCH
        List<NPlusOneUser> users = userRepository.findAllWithOrdersOptimized();
        
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
