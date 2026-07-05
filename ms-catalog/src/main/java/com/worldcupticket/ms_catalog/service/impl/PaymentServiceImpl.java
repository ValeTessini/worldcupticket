package com.worldcupticket.ms_catalog.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.worldcupticket.ms_catalog.domain.OrderRecord;
import com.worldcupticket.ms_catalog.repository.OrderRepository;
import com.worldcupticket.ms_catalog.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;

    public PaymentServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderRecord initiatePayment(String orderId) {
        OrderRecord order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
        order.setStatus("PAYMENT_INITIATED");
        order.setPaymentId(UUID.randomUUID().toString());
        return orderRepository.save(order);
    }

    @Override
    public OrderRecord completePayment(String orderId, String paymentId) {
        OrderRecord order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
        if (!paymentId.equals(order.getPaymentId())) {
            throw new IllegalArgumentException("Payment id inválido");
        }
        order.setStatus("PAID");
        order.setPaid(true);
        return orderRepository.save(order);
    }
}
