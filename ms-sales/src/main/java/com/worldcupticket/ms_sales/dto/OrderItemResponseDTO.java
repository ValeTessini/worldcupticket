package com.worldcupticket.ms_sales.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
    String sectorId,
    Integer quantity,
    BigDecimal unitPrice
) {}
