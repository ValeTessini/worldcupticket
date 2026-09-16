package com.worldcupticket.ms_sales.dto;

public record StockResponseDTO(
    String sectorId,
    Integer availableTickets
) {}
