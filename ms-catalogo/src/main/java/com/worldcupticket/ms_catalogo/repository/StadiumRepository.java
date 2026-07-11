package com.worldcupticket.ms_catalogo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldcupticket.ms_catalogo.domain.Stadium;

public interface StadiumRepository extends MongoRepository<Stadium, String> {

}
