package com.worldcupticket.ms_catalog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldcupticket.ms_catalog.domain.Team;

public interface TeamRepository extends MongoRepository<Team, String> {

    List<Team> findByGroup(String group);

}
