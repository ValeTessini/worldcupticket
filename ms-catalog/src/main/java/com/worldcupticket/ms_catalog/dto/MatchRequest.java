package com.worldcupticket.ms_catalog.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MatchRequest {

    @NotBlank
    private String homeTeam;
    @NotBlank
    private String awayTeam;
    @NotBlank
    private String group;
    @NotNull
    private LocalDateTime kickoff;
    @NotBlank
    private String stadiumId;
    private List<SectorDto> sectors;

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public LocalDateTime getKickoff() {
        return kickoff;
    }

    public void setKickoff(LocalDateTime kickoff) {
        this.kickoff = kickoff;
    }

    public String getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(String stadiumId) {
        this.stadiumId = stadiumId;
    }

    public List<SectorDto> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorDto> sectors) {
        this.sectors = sectors;
    }
}
