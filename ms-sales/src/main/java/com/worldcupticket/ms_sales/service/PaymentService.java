package com.worldcupticket.ms_sales.service;

import java.util.Map;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.worldcupticket.ms_sales.dto.OrderResponseDTO;
import com.worldcupticket.ms_sales.dto.PaymentResponseDTO;

public interface PaymentService {

    public PaymentResponseDTO createPaymentPreference(OrderResponseDTO order) throws MPException, MPApiException;

    public void processMercadoPagoWebHook(Map<String, Object> payload) throws MPException, MPApiException;

}
