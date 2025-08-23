package com.example.demo.modules.nplusone.api.mapper;

import com.example.demo.modules.nplusone.api.dto.NPlusOneOrderDTO;
import com.example.demo.modules.nplusone.api.dto.NPlusOneUserDTO;
import com.example.demo.modules.nplusone.entity.NPlusOneUser;
import com.example.demo.modules.nplusone.entity.NPlusOneOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NPlusOneUserMapper {
    
    public NPlusOneUserDTO toDTO(NPlusOneUser user) {
        if (user == null) {
            return null;
        }
        
        List<NPlusOneOrderDTO> orderDTOs = user.getOrders().stream()
                .map(this::toOrderDTO)
                .collect(Collectors.toList());
        
        return new NPlusOneUserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                orderDTOs
        );
    }
    
    public NPlusOneOrderDTO toOrderDTO(NPlusOneOrder order) {
        if (order == null) {
            return null;
        }
        
        return new NPlusOneOrderDTO(
                order.getId(),
                order.getOrderNumber(),
                order.getAmount()
        );
    }
}
