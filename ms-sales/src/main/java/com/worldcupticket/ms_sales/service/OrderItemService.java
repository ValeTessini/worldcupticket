package com.worldcupticket.ms_sales.service;

import java.math.BigDecimal;
import java.util.List;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.domain.OrderItem;
import com.worldcupticket.ms_sales.dto.MatchSectorCatalogDTO;
import com.worldcupticket.ms_sales.dto.OrderItemRequestDTO;

public interface OrderItemService {

    List<OrderItem> buildOrderItems(Order order, List<OrderItemRequestDTO> items, List<MatchSectorCatalogDTO> matchSectorCatalogDTOs);
    BigDecimal calculatePrice(List<MatchSectorCatalogDTO> matchSectorCatalogDTOs, String sectorId, Integer quantity);

}
