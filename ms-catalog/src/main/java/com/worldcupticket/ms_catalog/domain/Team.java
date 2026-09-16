package com.worldcupticket.ms_catalog.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "teams")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Team {

    @Id
    private String id;
    private String country;
    /* El campo "flag" de Team guarda el codigo ISO 3166-1 alpha-2 en minuscula */
    private String flag;
    private String group;

}
