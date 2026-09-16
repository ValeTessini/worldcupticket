package com.worldcupticket.ms_sales.service;

import java.util.List;

import com.worldcupticket.ms_sales.domain.Stock;
import com.worldcupticket.ms_sales.dto.StockResponseDTO;

public interface StockService {

    Stock findByMatchIdAndSectorId(String matchId, String sectorId);
    void reserveStock(String matchId, String sectorId, Integer quantity);
    void releaseStock(String matchId, String sectorId, Integer quantity);
    List<StockResponseDTO> getAvailabilityByMatch(String matchId);

}
