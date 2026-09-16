package com.worldcupticket.ms_catalog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldcupticket.ms_catalog.domain.Match;

public interface MatchRepository extends MongoRepository<Match, String> {

    public List<Match> findByLocalTeamIdOrAwayTeamId(String localTeamId, String awayTeamId);
    public List<Match> findByLocalTeamIdInOrAwayTeamIdIn(List<String> localTeamIds, List<String> awayTeamIds);

}
