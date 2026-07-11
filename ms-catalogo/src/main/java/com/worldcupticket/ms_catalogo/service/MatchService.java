package com.worldcupticket.ms_catalogo.service;

import java.util.List;

import com.worldcupticket.ms_catalogo.dto.MatchDTO;

public interface MatchService {

    List<MatchDTO> getAllMatches();
    List<MatchDTO> getMatchesByGroup(String group);
    MatchDTO getMatchById(String id);

}
