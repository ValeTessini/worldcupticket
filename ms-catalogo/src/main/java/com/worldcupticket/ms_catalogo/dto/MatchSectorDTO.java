package com.worldcupticket.ms_catalogo.dto;

import java.math.BigDecimal;

public record MatchSectorDTO(
    String sectorId,
    BigDecimal price
) {}
