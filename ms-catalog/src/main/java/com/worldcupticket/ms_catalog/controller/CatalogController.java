package com.worldcupticket.ms_catalog.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldcupticket.ms_catalog.domain.Match;
import com.worldcupticket.ms_catalog.domain.Stadium;
import com.worldcupticket.ms_catalog.dto.MatchRequest;
import com.worldcupticket.ms_catalog.dto.MatchResponse;
import com.worldcupticket.ms_catalog.dto.SectorDto;
import com.worldcupticket.ms_catalog.service.CatalogService;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/matches")
    public List<MatchResponse> searchMatches(
            @RequestParam(required = false) String stadiumId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String group) {
        return catalogService.searchMatches(stadiumId, city, group).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<MatchResponse> getMatch(@PathVariable String id) {
        return catalogService.getMatchById(id)
                .map(match -> ResponseEntity.ok(toResponse(match)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/matches")
    public ResponseEntity<MatchResponse> createMatch(@Validated @RequestBody MatchRequest request) {
        Match match = toMatch(request);
        return ResponseEntity.ok(toResponse(catalogService.createMatch(match)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/matches/{id}")
    public ResponseEntity<MatchResponse> updateMatch(@PathVariable String id, @Validated @RequestBody MatchRequest request) {
        Match match = toMatch(request);
        return ResponseEntity.ok(toResponse(catalogService.updateMatch(id, match)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/matches/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable String id) {
        catalogService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stadiums")
    public List<Stadium> listStadiums() {
        return catalogService.listStadiums();
    }

    @GetMapping("/stadiums/{id}")
    public ResponseEntity<Stadium> getStadium(@PathVariable String id) {
        return catalogService.getStadiumById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/stadiums")
    public ResponseEntity<Stadium> createStadium(@Validated @RequestBody Stadium stadium) {
        return ResponseEntity.ok(catalogService.createStadium(stadium));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/stadiums/{id}")
    public ResponseEntity<Stadium> updateStadium(@PathVariable String id, @Validated @RequestBody Stadium stadium) {
        return ResponseEntity.ok(catalogService.updateStadium(id, stadium));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/stadiums/{id}")
    public ResponseEntity<Void> deleteStadium(@PathVariable String id) {
        catalogService.deleteStadium(id);
        return ResponseEntity.noContent().build();
    }

    private MatchResponse toResponse(Match match) {
        MatchResponse response = new MatchResponse();
        response.setId(match.getId());
        response.setHomeTeam(match.getHomeTeam());
        response.setAwayTeam(match.getAwayTeam());
        response.setGroup(match.getGroup());
        response.setKickoff(match.getKickoff());
        response.setStadiumId(match.getStadiumId());
        response.setStadiumName(match.getStadiumName());
        response.setCity(match.getCity());
        response.setCountry(match.getCountry());
        response.setActive(match.isActive());
        response.setSectors(match.getSectors().stream().map(this::toSectorDto).collect(Collectors.toList()));
        return response;
    }

    private SectorDto toSectorDto(com.worldcupticket.ms_catalog.domain.MatchSector sector) {
        SectorDto dto = new SectorDto();
        dto.setId(sector.getSectorId());
        dto.setName(sector.getName());
        dto.setColor(sector.getColor());
        dto.setCapacity(sector.getCapacity());
        dto.setPrice(sector.getPrice());
        dto.setDescription(null);
        return dto;
    }

    private Match toMatch(MatchRequest request) {
        Match match = new Match();
        match.setHomeTeam(request.getHomeTeam());
        match.setAwayTeam(request.getAwayTeam());
        match.setGroup(request.getGroup());
        match.setKickoff(request.getKickoff());
        match.setStadiumId(request.getStadiumId());
        match.setSectors(request.getSectors().stream().map(dto -> new com.worldcupticket.ms_catalog.domain.MatchSector(
                dto.getId(), dto.getName(), dto.getColor(), dto.getCapacity(), dto.getCapacity(), dto.getPrice()))
                .collect(Collectors.toList()));
        return match;
    }
}
