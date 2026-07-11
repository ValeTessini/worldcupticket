package com.worldcupticket.ms_catalogo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worldcupticket.ms_catalogo.domain.Match;
import com.worldcupticket.ms_catalogo.dto.MatchDTO;
import com.worldcupticket.ms_catalogo.dto.MatchSectorDTO;
import com.worldcupticket.ms_catalogo.dto.StadiumDTO;
import com.worldcupticket.ms_catalogo.dto.TeamDTO;
import com.worldcupticket.ms_catalogo.repository.MatchRepository;
import com.worldcupticket.ms_catalogo.service.MatchService;
import com.worldcupticket.ms_catalogo.service.StadiumService;
import com.worldcupticket.ms_catalogo.service.TeamService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TeamService teamService;
    private final StadiumService stadiumService;

    @Override
    public List<MatchDTO> getAllMatches() {
        return matchRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Override
    public List<MatchDTO> getMatchesByGroup(String group) {
        List<String> teamsIds = teamService.getTeamsByGroup(group).stream().map(TeamDTO::id).toList();
        List<Match> matches = matchRepository.findByLocalTeamIdInOrAwayTeamIdIn(teamsIds, teamsIds);
        return matches.stream().map(this::toDTO).toList();
    }

    @Override
    public MatchDTO getMatchById(String id) {
        return matchRepository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("Match not found"));
    }

    private StadiumDTO getStadiumDTO(String stadiumId) {
        return stadiumService.getStadiumById(stadiumId);
    }

    private TeamDTO getTeamDTO(String teamId) {
        return teamService.getTeamById(teamId);
    }

    private MatchDTO toDTO(Match match) {
        return new MatchDTO(
            match.getId(),
            this.getStadiumDTO(match.getStadiumId()),
            match.getSectorsPrice().stream().map(sector -> new MatchSectorDTO(
                sector.getSectorId(),
                sector.getPrice()
            )).toList(),
            this.getTeamDTO(match.getLocalTeamId()),
            this.getTeamDTO(match.getAwayTeamId()),
            match.getDate(),
            match.getStage()
        );
    }
}
