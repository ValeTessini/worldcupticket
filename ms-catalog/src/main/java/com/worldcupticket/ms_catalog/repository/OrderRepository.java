package com.worldcupticket.ms_catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.worldcupticket.ms_catalog.domain.OrderRecord;

@Repository
public interface OrderRepository extends MongoRepository<OrderRecord, String> {
}
