package com.worldcupticket.ms_catalog.service;

import com.worldcupticket.ms_catalog.domain.OrderRecord;
import com.worldcupticket.ms_catalog.dto.OrderRequest;

public interface OrderService {
    OrderRecord createOrder(OrderRequest request);
    OrderRecord confirmPayment(String orderId, String paymentId);
}
