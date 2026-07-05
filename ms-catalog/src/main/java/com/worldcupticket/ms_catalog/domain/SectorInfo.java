package com.worldcupticket.ms_catalog.domain;

import java.math.BigDecimal;

public class SectorInfo {

    private String id;
    private String name;
    private String color;
    private int capacity;
    private BigDecimal price;
    private String description;

    public SectorInfo() {
    }

    public SectorInfo(String id, String name, String color, int capacity, BigDecimal price, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
