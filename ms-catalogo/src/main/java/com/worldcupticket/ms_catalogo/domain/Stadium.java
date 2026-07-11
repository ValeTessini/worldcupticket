package com.worldcupticket.ms_catalogo.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "stadiums")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stadium {

    @Id
    private String id;
    private String name;
    private String city;
    private String country;
    private List<StadiumSector> sectors;

}
