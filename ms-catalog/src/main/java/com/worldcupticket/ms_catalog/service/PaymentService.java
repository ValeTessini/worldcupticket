package com.worldcupticket.ms_catalog.service;

import com.worldcupticket.ms_catalog.domain.OrderRecord;

public interface PaymentService {
    OrderRecord initiatePayment(String orderId);
    OrderRecord completePayment(String orderId, String paymentId);
}
