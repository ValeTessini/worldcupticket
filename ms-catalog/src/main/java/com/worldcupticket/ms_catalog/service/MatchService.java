package com.worldcupticket.ms_catalog.service;

import java.util.List;

import com.worldcupticket.ms_catalog.dto.MatchDTO;

public interface MatchService {

    List<MatchDTO> getAllMatches();
    List<MatchDTO> getMatchesByGroup(String group);
    MatchDTO getMatchById(String id);

}
