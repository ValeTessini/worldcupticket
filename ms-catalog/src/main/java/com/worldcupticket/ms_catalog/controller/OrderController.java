package com.worldcupticket.ms_catalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldcupticket.ms_catalog.domain.OrderRecord;
import com.worldcupticket.ms_catalog.dto.OrderRequest;
import com.worldcupticket.ms_catalog.service.OrderService;

@RestController
@RequestMapping("/api/catalog/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderRecord> createOrder(@Validated @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<OrderRecord> confirmOrder(@PathVariable String orderId, @RequestBody String paymentId) {
        return ResponseEntity.ok(orderService.confirmPayment(orderId, paymentId));
    }
}
