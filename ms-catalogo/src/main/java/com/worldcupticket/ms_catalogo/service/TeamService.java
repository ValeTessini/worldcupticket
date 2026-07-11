package com.worldcupticket.ms_catalogo.service;

import java.util.List;

import com.worldcupticket.ms_catalogo.dto.TeamDTO;

public interface TeamService {

    List<TeamDTO> getAllTeams();
    List<TeamDTO> getTeamsByGroup(String group);
    TeamDTO getTeamById(String id);

}
