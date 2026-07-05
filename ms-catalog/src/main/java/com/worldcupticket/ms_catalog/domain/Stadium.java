package com.worldcupticket.ms_catalog.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

@Document(collection = "stadiums")
public class Stadium {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    private String description;

    private List<SectorInfo> sectors;

    public Stadium() {
    }

    public Stadium(String name, String city, String country, String description, List<SectorInfo> sectors) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.description = description;
        this.sectors = sectors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SectorInfo> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorInfo> sectors) {
        this.sectors = sectors;
    }
}
