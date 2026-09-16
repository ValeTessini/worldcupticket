package com.worldcupticket.ms_catalog.service;

import java.util.List;

import com.worldcupticket.ms_catalog.dto.StadiumDTO;

public interface StadiumService {

    List<StadiumDTO> getAllStadiums();
    StadiumDTO getStadiumById(String id);

}
