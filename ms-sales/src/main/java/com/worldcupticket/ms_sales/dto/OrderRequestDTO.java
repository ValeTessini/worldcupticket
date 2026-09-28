package com.worldcupticket.ms_sales.dto;

import java.util.List;

public record OrderRequestDTO(
    List<OrderItemRequestDTO> items
    )
{}
