package com.worldcupticket.ms_sales.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.domain.OrderItem;
import com.worldcupticket.ms_sales.dto.MatchCatalogDTO;
import com.worldcupticket.ms_sales.dto.MatchSectorCatalogDTO;
import com.worldcupticket.ms_sales.dto.OrderItemRequestDTO;
import com.worldcupticket.ms_sales.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Override
    public List<OrderItem> buildOrderItems(Order order, List<OrderItemRequestDTO> items, Map<String, MatchCatalogDTO> catalogByMatchId) {
        return items.stream()
            .map(item -> {
                MatchCatalogDTO match = catalogByMatchId.get(item.matchId());
                BigDecimal unitPrice = searchPrice(match.sectorsPrice(), item.sectorId());
                return new OrderItem(order, item.matchId(), item.sectorId(), item.quantity(), unitPrice);
            })
            .toList();
    }

    private BigDecimal searchPrice(List<MatchSectorCatalogDTO> matchSectorCatalogDTOs, String sectorId) {
        return matchSectorCatalogDTOs.stream()
            .filter(sector -> sector.sectorId().equals(sectorId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Sector not found" + sectorId))
            .price();
    }

}
