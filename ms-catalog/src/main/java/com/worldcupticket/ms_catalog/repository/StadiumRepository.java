package com.worldcupticket.ms_catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.worldcupticket.ms_catalog.domain.Stadium;

@Repository
public interface StadiumRepository extends MongoRepository<Stadium, String> {
}
