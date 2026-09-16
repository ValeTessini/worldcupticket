package com.worldcupticket.ms_sales.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.worldcupticket.ms_sales.domain.Stock;
import com.worldcupticket.ms_sales.dto.MatchCatalogDTO;
import com.worldcupticket.ms_sales.dto.StockResponseDTO;
import com.worldcupticket.ms_sales.repository.StockRepository;
import com.worldcupticket.ms_sales.service.StockService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final RestTemplate restTemplate;

    @Override
    public Stock findByMatchIdAndSectorId(String matchId, String sectorId) {
        return getOrCreateStockForMatch(matchId).stream()
            .filter(stock -> stock.getSectorId().equals(sectorId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    @Override
    @Transactional
    public void reserveStock(String matchId, String sectorId, Integer quantity) {
        Stock stock = findByMatchIdAndSectorId(matchId, sectorId);

        if (stock.getSoldTickets() + quantity > stock.getTotalCapacity()) {
            throw new RuntimeException("Not enough stock for sector " + sectorId);
        }

        stock.setSoldTickets(stock.getSoldTickets() + quantity);
        stockRepository.save(stock);
    }

    @Override
    @Transactional
    public void releaseStock(String matchId, String sectorId, Integer quantity) {
        Stock stock = findByMatchIdAndSectorId(matchId, sectorId);
        stock.setSoldTickets(stock.getSoldTickets() - quantity);
        stockRepository.save(stock);
    }

    @Override
    public List<StockResponseDTO> getAvailabilityByMatch(String matchId) {
        return getOrCreateStockForMatch(matchId).stream()
            .map(stock -> new StockResponseDTO(stock.getSectorId(), stock.getTotalCapacity() - stock.getSoldTickets()))
            .toList();
    }

    private List<Stock> getOrCreateStockForMatch(String matchId) {
        List<Stock> existing = stockRepository.findByMatchId(matchId);

        if (!existing.isEmpty()) {
            return existing;
        }

        MatchCatalogDTO match = getMatchFromCatalog(matchId);

        List<Stock> newStock = match.stadium().sectors().stream()
            .map(sector -> {
                Stock stock = new Stock();
                stock.setMatchId(matchId);
                stock.setSectorId(sector.id());
                stock.setTotalCapacity(sector.capacity());
                stock.setSoldTickets(0);
                return stock;
            })
            .toList();

        return stockRepository.saveAll(newStock);
    }

    private MatchCatalogDTO getMatchFromCatalog(String matchId) {
        String url = "http://ms-catalog/api/catalog/matches/" + matchId;
        return restTemplate.getForObject(url, MatchCatalogDTO.class);
    }

}
