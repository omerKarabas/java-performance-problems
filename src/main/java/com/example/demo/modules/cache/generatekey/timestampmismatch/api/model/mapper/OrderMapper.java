package com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.mapper;

import com.example.demo.modules.cache.generatekey.timestampmismatch.api.model.dto.response.OrderDTO;
import com.example.demo.modules.cache.generatekey.timestampmismatch.domain.entity.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    
    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        
        return new OrderDTO(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomerName(),
                order.getProductName(),
                order.getAmount(),
                order.getQuantity(),
                order.getOrderDate(),
                order.getStatus(),
                order.getActive()
        );
    }
    
    public List<OrderDTO> toDTOList(List<Order> orders) {
        if (orders == null) {
            return null;
        }
        
        return orders.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        
        return new Order(
                orderDTO.getId(),
                orderDTO.getOrderNumber(),
                orderDTO.getCustomerName(),
                orderDTO.getProductName(),
                orderDTO.getAmount(),
                orderDTO.getQuantity(),
                orderDTO.getStatus()
        );
    }
}
