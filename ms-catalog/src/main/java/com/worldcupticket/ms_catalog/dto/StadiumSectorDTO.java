package com.worldcupticket.ms_catalog.dto;

import com.worldcupticket.ms_catalog.enums.SectorType;

public record StadiumSectorDTO(
    String id,
    String name,
    int capacity,
    SectorType type
) {}
