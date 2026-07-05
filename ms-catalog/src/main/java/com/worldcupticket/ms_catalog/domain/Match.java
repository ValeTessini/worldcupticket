package com.worldcupticket.ms_catalog.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "matches")
public class Match {

    @Id
    private String id;

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

    private String stadiumName;
    private String city;
    private String country;

    @NotNull
    private List<MatchSector> sectors;

    private boolean active = true;

    public Match() {
    }

    public Match(String homeTeam, String awayTeam, String group, LocalDateTime kickoff, String stadiumId, String stadiumName,
            String city, String country, List<MatchSector> sectors) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.group = group;
        this.kickoff = kickoff;
        this.stadiumId = stadiumId;
        this.stadiumName = stadiumName;
        this.city = city;
        this.country = country;
        this.sectors = sectors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<MatchSector> getSectors() {
        return sectors;
    }

    public void setSectors(List<MatchSector> sectors) {
        this.sectors = sectors;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
