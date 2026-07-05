package com.worldcupticket.ms_catalog.service;

import java.util.List;
import java.util.Optional;

import com.worldcupticket.ms_catalog.domain.Match;
import com.worldcupticket.ms_catalog.domain.Stadium;

public interface CatalogService {
    Match createMatch(Match match);
    Optional<Match> getMatchById(String id);
    List<Match> searchMatches(String stadiumId, String city, String group);
    List<Match> listMatches();
    Match updateMatch(String id, Match match);
    void deleteMatch(String id);

    Stadium createStadium(Stadium stadium);
    Optional<Stadium> getStadiumById(String id);
    List<Stadium> listStadiums();
    Stadium updateStadium(String id, Stadium stadium);
    void deleteStadium(String id);
}
