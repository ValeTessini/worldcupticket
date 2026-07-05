package com.worldcupticket.ms_catalog.domain;

import java.math.BigDecimal;

public class MatchSector {

    private String sectorId;
    private String name;
    private String color;
    private int capacity;
    private int available;
    private BigDecimal price;

    public MatchSector() {
    }

    public MatchSector(String sectorId, String name, String color, int capacity, int available, BigDecimal price) {
        this.sectorId = sectorId;
        this.name = name;
        this.color = color;
        this.capacity = capacity;
        this.available = available;
        this.price = price;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
