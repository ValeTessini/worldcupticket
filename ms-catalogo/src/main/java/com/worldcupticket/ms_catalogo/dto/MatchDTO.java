package com.worldcupticket.ms_catalogo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.worldcupticket.ms_catalogo.enums.MatchStage;

public record MatchDTO(
    String id,
    StadiumDTO stadium,
    List<MatchSectorDTO> sectorsPrice,
    TeamDTO localTeam,
    TeamDTO awayTeam,
    LocalDateTime date,
    MatchStage stage
) {}
