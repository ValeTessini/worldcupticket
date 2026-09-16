package com.worldcupticket.ms_sales.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.enums.Status;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
    List<Order> findByStatusAndExpiresAtBefore(Status pending, LocalDateTime now);

}
