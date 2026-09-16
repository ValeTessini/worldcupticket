package com.worldcupticket.ms_sales.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.worldcupticket.ms_sales.dto.OrderRequestDTO;
import com.worldcupticket.ms_sales.dto.OrderResponseDTO;
import com.worldcupticket.ms_sales.dto.OrderWithPaymentDTO;
import com.worldcupticket.ms_sales.dto.PaymentResponseDTO;
import com.worldcupticket.ms_sales.service.OrderService;
import com.worldcupticket.ms_sales.service.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/sales/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(Authentication authentication) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(Long.parseLong(authentication.getName())));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderByOrderId(@PathVariable Long orderId, Authentication authentication) {
        return ResponseEntity.ok(orderService.getOrderByOrderId(orderId, Long.parseLong(authentication.getName())));
    }

    @PostMapping
    public ResponseEntity<OrderWithPaymentDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO, Authentication authentication) throws MPException, MPApiException {
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderRequestDTO, Long.parseLong(authentication.getName()));

        PaymentResponseDTO paymentResponseDTO = paymentService.createPaymentPreference(orderResponseDTO);

        return ResponseEntity.ok(new OrderWithPaymentDTO(orderResponseDTO, paymentResponseDTO));
    }

}
