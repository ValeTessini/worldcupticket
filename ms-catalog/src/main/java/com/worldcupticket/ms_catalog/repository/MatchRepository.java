package com.worldcupticket.ms_catalog.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.worldcupticket.ms_catalog.domain.Match;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
    List<Match> findByStadiumId(String stadiumId);
    List<Match> findByCity(String city);
    List<Match> findByGroup(String group);
    List<Match> findByKickoffBetween(LocalDateTime start, LocalDateTime end);
}
