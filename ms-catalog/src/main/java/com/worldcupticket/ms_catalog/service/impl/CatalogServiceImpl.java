package com.worldcupticket.ms_catalog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worldcupticket.ms_catalog.domain.Match;
import com.worldcupticket.ms_catalog.domain.Stadium;
import com.worldcupticket.ms_catalog.repository.MatchRepository;
import com.worldcupticket.ms_catalog.repository.StadiumRepository;
import com.worldcupticket.ms_catalog.service.CatalogService;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    private final MatchRepository matchRepository;
    private final StadiumRepository stadiumRepository;

    public CatalogServiceImpl(MatchRepository matchRepository, StadiumRepository stadiumRepository) {
        this.matchRepository = matchRepository;
        this.stadiumRepository = stadiumRepository;
    }

    @Override
    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public Optional<Match> getMatchById(String id) {
        return matchRepository.findById(id);
    }

    @Override
    public List<Match> searchMatches(String stadiumId, String city, String group) {
        if (stadiumId != null && !stadiumId.isBlank()) {
            return matchRepository.findByStadiumId(stadiumId);
        }
        if (city != null && !city.isBlank()) {
            return matchRepository.findByCity(city);
        }
        if (group != null && !group.isBlank()) {
            return matchRepository.findByGroup(group);
        }
        return matchRepository.findAll();
    }

    @Override
    public List<Match> listMatches() {
        return matchRepository.findAll();
    }

    @Override
    public Match updateMatch(String id, Match match) {
        match.setId(id);
        return matchRepository.save(match);
    }

    @Override
    public void deleteMatch(String id) {
        matchRepository.deleteById(id);
    }

    @Override
    public Stadium createStadium(Stadium stadium) {
        return stadiumRepository.save(stadium);
    }

    @Override
    public Optional<Stadium> getStadiumById(String id) {
        return stadiumRepository.findById(id);
    }

    @Override
    public List<Stadium> listStadiums() {
        return stadiumRepository.findAll();
    }

    @Override
    public Stadium updateStadium(String id, Stadium stadium) {
        stadium.setId(id);
        return stadiumRepository.save(stadium);
    }

    @Override
    public void deleteStadium(String id) {
        stadiumRepository.deleteById(id);
    }
}
