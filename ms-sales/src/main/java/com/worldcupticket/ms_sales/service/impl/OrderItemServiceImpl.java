package com.worldcupticket.ms_sales.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.domain.OrderItem;
import com.worldcupticket.ms_sales.dto.MatchSectorCatalogDTO;
import com.worldcupticket.ms_sales.dto.OrderItemRequestDTO;
import com.worldcupticket.ms_sales.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Override
    public List<OrderItem> buildOrderItems(Order order, List<OrderItemRequestDTO> items, List<MatchSectorCatalogDTO> matchSectorCatalogDTOs) {
        return items.stream()
            .map(item -> new OrderItem(order, item.sectorId(), item.quantity(), searchPrice(matchSectorCatalogDTOs, item.sectorId())))
            .toList();
    }

    @Override
    public BigDecimal calculatePrice(List<MatchSectorCatalogDTO> matchSectorCatalogDTOs, String sectorId, Integer quantity) {
        return searchPrice(matchSectorCatalogDTOs, sectorId).multiply(BigDecimal.valueOf(quantity));
    }

    private BigDecimal searchPrice(List<MatchSectorCatalogDTO> matchSectorCatalogDTOs, String sectorId) {
        return matchSectorCatalogDTOs.stream()
            .filter(sector -> sector.sectorId().equals(sectorId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Sector not found" + sectorId))
            .price();
    }

}
