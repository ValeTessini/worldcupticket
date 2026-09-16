package com.worldcupticket.ms_catalog.dto;

import java.util.List;

public record StadiumDTO(
    String id,
    String name,
    String city,
    String country,
    List<StadiumSectorDTO> sectors
) {}
