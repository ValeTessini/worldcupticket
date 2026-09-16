package com.worldcupticket.ms_sales.dto;

public record OrderWithPaymentDTO(
    OrderResponseDTO order,
    PaymentResponseDTO payment
)
{}
