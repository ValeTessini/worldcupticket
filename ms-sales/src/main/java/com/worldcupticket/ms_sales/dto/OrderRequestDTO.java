package com.worldcupticket.ms_sales.dto;

import java.util.List;

public record OrderRequestDTO(
    String matchId,
    List<OrderItemRequestDTO> items
    )
{}
