package com.worldcupticket.ms_catalogo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worldcupticket.ms_catalogo.domain.Team;
import com.worldcupticket.ms_catalogo.dto.TeamDTO;
import com.worldcupticket.ms_catalogo.repository.TeamRepository;
import com.worldcupticket.ms_catalogo.service.TeamService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

	@Override
	public List<TeamDTO> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
		return teams.stream().map(this::toDTO).toList();
	}

	@Override
	public List<TeamDTO> getTeamsByGroup(String group) {
	    List<Team> teams = teamRepository.findByGroup(group);
		return teams.stream().map(this::toDTO).toList();
	}

	@Override
	public TeamDTO getTeamById(String id) {
	    Team team = teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Team not found"));
	    return toDTO(team);
	}

	private TeamDTO toDTO(Team team) {
		return new TeamDTO(
		    team.getId(),
			team.getCountry(),
			team.getFlag(),
			team.getGroup());
	}

}
