package com.worldcupticket.ms_catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.worldcupticket.ms_catalog.domain.Stadium;

public interface StadiumRepository extends MongoRepository<Stadium, String> {

}
