package com.worldcupticket.ms_catalog.domain;

import com.worldcupticket.ms_catalog.enums.SectorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StadiumSector {

    private String id;
    private String name;
    private int capacity;
    private SectorType type;

}
