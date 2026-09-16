package com.worldcupticket.ms_catalog.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.worldcupticket.ms_catalog.enums.MatchStage;

public record MatchDTO(
    String id,
    StadiumDTO stadium,
    List<MatchSectorDTO> sectorsPrice,
    TeamDTO localTeam,
    TeamDTO awayTeam,
    LocalDateTime date,
    MatchStage stage
) {}
