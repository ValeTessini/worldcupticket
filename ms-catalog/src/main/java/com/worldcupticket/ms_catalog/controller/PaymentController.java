package com.worldcupticket.ms_catalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldcupticket.ms_catalog.domain.OrderRecord;
import com.worldcupticket.ms_catalog.service.PaymentService;

@RestController
@RequestMapping("/api/catalog/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}/initiate")
    public ResponseEntity<OrderRecord> initiate(@PathVariable String orderId) {
        return ResponseEntity.ok(paymentService.initiatePayment(orderId));
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<OrderRecord> complete(@PathVariable String orderId, @Validated @RequestBody String paymentId) {
        return ResponseEntity.ok(paymentService.completePayment(orderId, paymentId));
    }
}
