package com.worldcupticket.ms_sales.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.domain.OrderItem;
import com.worldcupticket.ms_sales.dto.MatchCatalogDTO;
import com.worldcupticket.ms_sales.dto.OrderItemRequestDTO;
import com.worldcupticket.ms_sales.dto.OrderRequestDTO;
import com.worldcupticket.ms_sales.dto.OrderResponseDTO;
import com.worldcupticket.ms_sales.enums.Status;
import com.worldcupticket.ms_sales.repository.OrderRepository;
import com.worldcupticket.ms_sales.service.OrderItemService;
import com.worldcupticket.ms_sales.service.OrderService;
import com.worldcupticket.ms_sales.service.StockService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    private final StockService stockService;
    private final OrderItemService orderItemService;

    @Override
	@Transactional
	public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, Long userId) {

		List<OrderItemRequestDTO> cartItems = orderRequestDTO.items();
		if (cartItems == null || cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		for (OrderItemRequestDTO item : cartItems) {
			stockService.reserveStock(item.matchId(), item.sectorId(), item.quantity());
		}

		Map<String, MatchCatalogDTO> catalogByMatchId = cartItems.stream()
			.map(OrderItemRequestDTO::matchId)
			.distinct()
			.collect(Collectors.toMap(Function.identity(), this::getMatchFromCatalog));

	    Order order = new Order(userId, List.of(), Status.PENDING, BigDecimal.ZERO);
	    List<OrderItem> orderItems = orderItemService.buildOrderItems(order, cartItems, catalogByMatchId);
		order.setItems(orderItems);
		order.setPrice(calculateTotalPrice(orderItems));
		order.setExpiresAt(LocalDateTime.now().plusMinutes(15));

	    return OrderResponseDTO.from(orderRepository.save(order));
	}

	@Override
	public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
		return orderRepository.findByUserId(userId).stream()
			.map(OrderResponseDTO::from)
			.toList();
	}

	@Override
	public OrderResponseDTO getOrderByOrderId(Long orderId, Long userId) {

	    Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		if(!order.getUserId().equals(userId)) {
            throw new RuntimeException("This order does not belong to you");
		}

	    return OrderResponseDTO.from(order);
	}

	@Override
	@Transactional
	public void completeOrder(Long orderId) {

	    Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
	    if(order.getStatus() != Status.PENDING) {
            throw new RuntimeException("Order is not pending");
        }

		order.setStatus(Status.COMPLETED);
		orderRepository.save(order);
	}

	@Override
	@Transactional
	public void cancelOrder(Long orderId) {
	    Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
	    if(order.getStatus() != Status.PENDING) {
            throw new RuntimeException("Order is not pending");
        }

		for (OrderItem item : order.getItems()) {

			stockService.releaseStock(item.getMatchId(), item.getSectorId(), item.getQuantity());
		}

		order.setStatus(Status.CANCELLED);
		orderRepository.save(order);
	}

	private BigDecimal calculateTotalPrice(List<OrderItem> items) {

	    return items.stream()
						.map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
						.reduce(BigDecimal.ZERO, BigDecimal::add);

	}

	private MatchCatalogDTO getMatchFromCatalog(String matchId) {
	    String url = "http://ms-catalog/api/catalog/matches/" + matchId;
	    return restTemplate.getForObject(url, MatchCatalogDTO.class);
	}

}
