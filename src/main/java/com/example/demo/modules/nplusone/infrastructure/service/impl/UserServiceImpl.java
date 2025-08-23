package com.example.demo.modules.nplusone.service;

import com.example.demo.modules.nplusone.dto.UserDTO;
import com.example.demo.modules.nplusone.entity.User;
import com.example.demo.modules.nplusone.entity.Order;
import com.example.demo.modules.nplusone.mapper.UserMapper;
import com.example.demo.modules.nplusone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final UserMapper userMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersWithOrdersNPlusOne() {
        log.info("=== N+1 PROBLEM DEMONSTRATION STARTING ===");
        
        // STEP 1: Get all users WITHOUT orders (lazy loading - orders not fetched yet)
        List<User> users = userRepository.findAll();
        log.info("STEP 1: Retrieved {} users with 1 query (orders not loaded yet)", users.size());
        
        // STEP 2: N+1 PROBLEM - Fetch orders for each user individually and set them
        log.info("STEP 2: N+1 PROBLEM - Fetching orders for each user individually");
        for (User user : users) {
            log.info("Processing user: {} (ID: {})", user.getUsername(), user.getId());
            
            // THIS IS THE N+1 PROBLEM!
            // Each iteration generates: SELECT * FROM orders WHERE user_id = ?
            // Total queries: 1 (users) + N (orders) = N+1
            List<Order> userOrders = orderService.getOrdersByUserId(user.getId());
            
            // Set orders to user (this makes the relationship eager for this user)
            user.setOrders(userOrders);
            
            log.info("User {} now has {} orders loaded", user.getUsername(), userOrders.size());
        }
        
        log.info("=== N+1 PROBLEM COMPLETED ===");
        log.info("Total queries executed: 1 (users) + {} (orders) = {} queries", 
            users.size(), users.size() + 1);
        
        return userMapper.toDTOList(users);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersWithOrdersOptimized() {
        log.info("=== OPTIMIZED SOLUTION DEMONSTRATION STARTING ===");
        
        // STEP 1: Get all users with orders in single JOIN FETCH query
        log.info("STEP 1: Using JOIN FETCH to get all users with orders in 1 query");
        List<User> users = userRepository.findAllWithOrders();
        log.info("Retrieved {} users with all orders in 1 query", users.size());
        
        log.info("=== OPTIMIZED SOLUTION COMPLETED ===");
        log.info("Total queries executed: 1 (JOIN FETCH)");
        
        return userMapper.toDTOList(users);
    }
}
