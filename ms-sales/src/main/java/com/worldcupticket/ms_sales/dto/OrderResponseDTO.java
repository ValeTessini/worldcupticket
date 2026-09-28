package com.worldcupticket.ms_sales.dto;

import java.math.BigDecimal;
import java.util.List;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.enums.Status;

public record OrderResponseDTO(
    Long id,
    String userId,
    List<OrderItemResponseDTO> items,
    Status status,
    BigDecimal totalAmount
) {

    public static OrderResponseDTO from(Order order) {
        return new OrderResponseDTO(
            order.getId(),
            order.getUserId().toString(),
            order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(item.getMatchId(), item.getSectorId(), item.getQuantity(), item.getUnitPrice()))
                .toList(),
            order.getStatus(),
            order.getPrice()
        );
    }
}
