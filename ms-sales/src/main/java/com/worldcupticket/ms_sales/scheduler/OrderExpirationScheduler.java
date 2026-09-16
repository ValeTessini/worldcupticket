package com.worldcupticket.ms_sales.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.enums.Status;
import com.worldcupticket.ms_sales.repository.OrderRepository;
import com.worldcupticket.ms_sales.service.OrderService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class OrderExpirationScheduler {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Scheduled(fixedRate = 60000) // every 60 seconds
    @Transactional
    public void checkOrderExpiration() {
        List<Order> expiredOrders = orderRepository.findByStatusAndExpiresAtBefore(Status.PENDING, LocalDateTime.now());

        for (Order order : expiredOrders) {
            orderService.cancelOrder(order.getId());
        }
    }
}
