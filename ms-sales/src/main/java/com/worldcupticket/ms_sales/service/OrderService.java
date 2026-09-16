package com.worldcupticket.ms_sales.service;

import java.util.List;

import com.worldcupticket.ms_sales.dto.OrderRequestDTO;
import com.worldcupticket.ms_sales.dto.OrderResponseDTO;

public interface OrderService {

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, Long userId);
    public List<OrderResponseDTO> getOrdersByUserId(Long userId);
    public OrderResponseDTO getOrderByOrderId(Long orderId, Long userId);
    public void completeOrder(Long orderId);
    public void cancelOrder(Long orderId);

}
