package com.worldcupticket.ms_catalogo.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.worldcupticket.ms_catalogo.enums.MatchStage;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "matches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Match {

    @Id
    private String id;
    private String stadiumId;
    private List<MatchSector> sectorsPrice;
    private String localTeamId;
    private String awayTeamId;
    private LocalDateTime date;
    private MatchStage stage;

}
