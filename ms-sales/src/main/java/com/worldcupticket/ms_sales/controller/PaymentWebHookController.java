package com.worldcupticket.ms_sales.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldcupticket.ms_sales.service.PaymentService;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/sales/payments/webhooks")
@AllArgsConstructor
public class PaymentWebHookController {

    private final PaymentService paymentService;

    @PostMapping("/mercadopago")
    public ResponseEntity<Void> receiveMercadoPagoWebHook(@RequestBody Map<String, Object> payload) throws MPException, MPApiException {
        paymentService.processMercadoPagoWebHook(payload);
        return ResponseEntity.ok().build();
    }

}
