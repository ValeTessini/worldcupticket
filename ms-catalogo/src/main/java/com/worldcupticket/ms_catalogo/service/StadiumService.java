package com.worldcupticket.ms_catalogo.service;

import java.util.List;

import com.worldcupticket.ms_catalogo.dto.StadiumDTO;

public interface StadiumService {

    List<StadiumDTO> getAllStadiums();
    StadiumDTO getStadiumById(String id);

}
