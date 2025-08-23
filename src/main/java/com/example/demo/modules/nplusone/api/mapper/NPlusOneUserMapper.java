package com.example.demo.modules.nplusone.api.mapper;

import com.example.demo.modules.nplusone.api.dto.NPlusOneOrderDTO;
import com.example.demo.modules.nplusone.api.dto.NPlusOneUserDTO;
import com.example.demo.modules.nplusone.domain.entity.NPlusOneOrder;
import com.example.demo.modules.nplusone.domain.entity.NPlusOneUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NPlusOneUserMapper {
    
    public NPlusOneUserDTO toDTO(NPlusOneUser user) {
        if (user == null) {
            return null;
        }
        
        NPlusOneUserDTO dto = new NPlusOneUserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        
        // Map orders if they exist
        if (user.getOrders() != null) {
            List<NPlusOneOrderDTO> orderDTOs = user.getOrders().stream()
                    .map(this::orderToDTO)
                    .collect(Collectors.toList());
            dto.setOrders(orderDTOs);
        }
        
        return dto;
    }
    
    private NPlusOneOrderDTO orderToDTO(NPlusOneOrder order) {
        if (order == null) {
            return null;
        }
        
        NPlusOneOrderDTO dto = new NPlusOneOrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setAmount(order.getAmount());
        return dto;
    }
}
