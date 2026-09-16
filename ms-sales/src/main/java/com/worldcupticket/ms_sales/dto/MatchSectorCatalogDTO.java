package com.worldcupticket.ms_sales.dto;

import java.math.BigDecimal;

public record MatchSectorCatalogDTO(
    String sectorId,
    BigDecimal price
)
{}
