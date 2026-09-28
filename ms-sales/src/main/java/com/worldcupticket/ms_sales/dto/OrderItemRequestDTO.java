package com.worldcupticket.ms_sales.dto;

public record OrderItemRequestDTO(
    String matchId,
    String sectorId,
    int quantity
) {}
