package com.worldcupticket.ms_catalogo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldcupticket.ms_catalogo.domain.Team;

public interface TeamRepository extends MongoRepository<Team, String> {

    List<Team> findByGroup(String group);

}
