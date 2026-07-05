package com.worldcupticket.ms_catalog.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class OrderRequest {

    @NotBlank
    private String matchId;

    @NotEmpty
    private List<OrderSectorRequest> sectors;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public List<OrderSectorRequest> getSectors() {
        return sectors;
    }

    public void setSectors(List<OrderSectorRequest> sectors) {
        this.sectors = sectors;
    }
}
