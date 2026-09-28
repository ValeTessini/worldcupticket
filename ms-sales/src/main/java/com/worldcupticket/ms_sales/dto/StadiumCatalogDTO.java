package com.worldcupticket.ms_sales.dto;

import java.util.List;

public record StadiumCatalogDTO(
    String id,
    String name,
    String city,
    List<StadiumSectorCatalogDTO> sectors
) {}
