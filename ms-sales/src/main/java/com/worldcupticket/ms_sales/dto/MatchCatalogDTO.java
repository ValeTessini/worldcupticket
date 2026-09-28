package com.worldcupticket.ms_sales.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MatchCatalogDTO(
    String id,
    StadiumCatalogDTO stadium,
    List<MatchSectorCatalogDTO> sectorsPrice,
    TeamCatalogDTO localTeam,
    TeamCatalogDTO awayTeam,
    LocalDateTime date,
    String stage
) {}
