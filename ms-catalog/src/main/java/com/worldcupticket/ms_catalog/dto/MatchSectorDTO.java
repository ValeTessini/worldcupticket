package com.worldcupticket.ms_catalog.dto;

import java.math.BigDecimal;

public record MatchSectorDTO(
    String sectorId,
    BigDecimal price
) {}
