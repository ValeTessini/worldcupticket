package com.worldcupticket.ms_sales.service;

import java.util.List;
import java.util.Map;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.domain.OrderItem;
import com.worldcupticket.ms_sales.dto.MatchCatalogDTO;
import com.worldcupticket.ms_sales.dto.OrderItemRequestDTO;

public interface OrderItemService {

    List<OrderItem> buildOrderItems(Order order, List<OrderItemRequestDTO> items, Map<String, MatchCatalogDTO> catalogByMatchId);

}
