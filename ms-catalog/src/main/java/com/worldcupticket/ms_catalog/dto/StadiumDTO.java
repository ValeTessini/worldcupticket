package com.worldcupticket.ms_catalog.dto;

import java.util.List;

import com.worldcupticket.ms_catalog.enums.StadiumLayout;

public record StadiumDTO(
    String id,
    String name,
    String city,
    String country,
    List<StadiumSectorDTO> sectors,
    StadiumLayout layout
) {}
