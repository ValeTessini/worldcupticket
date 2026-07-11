package com.worldcupticket.ms_catalogo.service.impl;

import com.worldcupticket.ms_catalogo.domain.Stadium;
import com.worldcupticket.ms_catalogo.dto.StadiumDTO;
import com.worldcupticket.ms_catalogo.dto.StadiumSectorDTO;
import com.worldcupticket.ms_catalogo.repository.StadiumRepository;
import com.worldcupticket.ms_catalogo.service.StadiumService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;

	@Override
	public List<StadiumDTO> getAllStadiums() {
	return stadiumRepository.findAll().stream().map(this::toDTO).toList();
	}

	@Override
	public StadiumDTO getStadiumById(String id) {
		return stadiumRepository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("Stadium not found"));
	}

	private StadiumDTO toDTO(Stadium stadium) {
	    return new StadiumDTO(
	        stadium.getId(),
	        stadium.getName(),
	        stadium.getCity(),
	        stadium.getCountry(),
	        stadium.getSectors().stream().map(sector -> new StadiumSectorDTO(
	            sector.getId(),
	            sector.getName(),
	            sector.getCapacity(),
	            sector.getType()
	        )).toList()
	    );
	}

}
