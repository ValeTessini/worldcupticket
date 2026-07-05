package com.worldcupticket.ms_catalog.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worldcupticket.ms_catalog.domain.Match;
import com.worldcupticket.ms_catalog.domain.OrderRecord;
import com.worldcupticket.ms_catalog.domain.OrderSector;
import com.worldcupticket.ms_catalog.domain.MatchSector;
import com.worldcupticket.ms_catalog.dto.OrderRequest;
import com.worldcupticket.ms_catalog.dto.OrderSectorRequest;
import com.worldcupticket.ms_catalog.repository.MatchRepository;
import com.worldcupticket.ms_catalog.repository.OrderRepository;
import com.worldcupticket.ms_catalog.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final MatchRepository matchRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(MatchRepository matchRepository, OrderRepository orderRepository) {
        this.matchRepository = matchRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderRecord createOrder(OrderRequest request) {
        Match match = matchRepository.findById(request.getMatchId())
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));

        BigDecimal total = BigDecimal.ZERO;
        for (OrderSectorRequest sectorRequest : request.getSectors()) {
            MatchSector sector = match.getSectors().stream()
                    .filter(s -> s.getSectorId().equals(sectorRequest.getSectorId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Sector no encontrado"));

            if (sectorRequest.getQuantity() <= 0 || sectorRequest.getQuantity() > sector.getAvailable()) {
                throw new IllegalArgumentException("Cantidad inválida o stock insuficiente para sector " + sector.getSectorId());
            }

            total = total.add(sector.getPrice().multiply(BigDecimal.valueOf(sectorRequest.getQuantity())));
            sector.setAvailable(sector.getAvailable() - sectorRequest.getQuantity());
        }

        matchRepository.save(match);

        OrderRecord order = new OrderRecord();
        order.setBuyerUsername(getCurrentUsername());
        order.setMatchId(request.getMatchId());
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(total);
        order.setStatus("PENDING_PAYMENT");
        order.setPaid(false);
        order.setSectors(request.getSectors().stream().map(this::toOrderSector).collect(Collectors.toList()));

        return orderRepository.save(order);
    }

    @Override
    public OrderRecord confirmPayment(String orderId, String paymentId) {
        OrderRecord order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));

        order.setStatus("PAID");
        order.setPaid(true);
        order.setPaymentId(paymentId);
        return orderRepository.save(order);
    }

    private OrderSector toOrderSector(OrderSectorRequest request) {
        OrderSector orderSector = new OrderSector();
        orderSector.setSectorId(request.getSectorId());
        orderSector.setQuantity(request.getQuantity());
        orderSector.setPrice(request.getPrice());
        orderSector.setSectorName(request.getSectorId());
        return orderSector;
    }

    private String getCurrentUsername() {
        var authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user");
        }
        return authentication.getName();
    }
}
