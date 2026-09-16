package com.worldcupticket.ms_sales.dto;

import java.util.List;

public record MatchCatalogDTO(
    String id,
    StadiumCatalogDTO stadium,
    List<MatchSectorCatalogDTO> sectorsPrice
) {}
