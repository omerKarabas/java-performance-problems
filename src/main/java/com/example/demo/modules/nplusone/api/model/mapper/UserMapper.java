package com.example.demo.modules.nplusone.mapper;

import com.example.demo.modules.nplusone.dto.UserDTO;
import com.example.demo.modules.nplusone.dto.OrderDTO;
import com.example.demo.modules.nplusone.entity.User;
import com.example.demo.modules.nplusone.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        List<OrderDTO> orderDTOs = user.getOrders() != null ? 
            user.getOrders().stream()
                .map(this::toOrderDTO)
                .collect(Collectors.toList()) : 
            null;
        
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            orderDTOs,
            user.getOrders() != null ? user.getOrders().size() : 0
        );
    }
    
    public List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return null;
        }
        
        return users.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private OrderDTO toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }
        
        return new OrderDTO(
            order.getId(),
            order.getOrderNumber(),
            order.getAmount()
        );
    }
}
