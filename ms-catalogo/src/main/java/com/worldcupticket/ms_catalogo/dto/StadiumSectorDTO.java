package com.worldcupticket.ms_catalogo.dto;

import com.worldcupticket.ms_catalogo.enums.SectorType;

public record StadiumSectorDTO(
    String id,
    String name,
    int capacity,
    SectorType type
) {}
