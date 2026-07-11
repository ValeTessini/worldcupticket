package com.worldcupticket.ms_catalogo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldcupticket.ms_catalogo.dto.StadiumDTO;
import com.worldcupticket.ms_catalogo.service.StadiumService;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
@RequestMapping("/api/catalog/stadiums")
public class StadiumController {

    private final StadiumService stadiumService;

    @GetMapping
    public ResponseEntity<List<StadiumDTO>> getAllStadiums() {
        return ResponseEntity.ok(stadiumService.getAllStadiums());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StadiumDTO> getStadiumById(@PathVariable String id) {
        return ResponseEntity.ok(stadiumService.getStadiumById(id));
    }

}
