package com.worldcupticket.ms_sales.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
    String matchId,
    String sectorId,
    Integer quantity,
    BigDecimal unitPrice
) {}
