package com.worldcupticket.ms_sales.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldcupticket.ms_sales.dto.StockResponseDTO;
import com.worldcupticket.ms_sales.service.StockService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/sales/matches")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/{matchId}/stock")
    public ResponseEntity<List<StockResponseDTO>> getStockByMatch(@PathVariable("matchId") String matchId) {
        return ResponseEntity.ok(stockService.getAvailabilityByMatch(matchId));
    }

}
