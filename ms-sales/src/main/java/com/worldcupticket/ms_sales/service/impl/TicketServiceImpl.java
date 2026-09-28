package com.worldcupticket.ms_sales.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.worldcupticket.ms_sales.domain.Order;
import com.worldcupticket.ms_sales.domain.OrderItem;
import com.worldcupticket.ms_sales.dto.MatchCatalogDTO;
import com.worldcupticket.ms_sales.dto.StadiumSectorCatalogDTO;
import com.worldcupticket.ms_sales.dto.TicketResponseDTO;
import com.worldcupticket.ms_sales.enums.Status;
import com.worldcupticket.ms_sales.repository.OrderItemRepository;
import com.worldcupticket.ms_sales.repository.OrderRepository;
import com.worldcupticket.ms_sales.service.TicketService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {

    private static final int QR_SIZE = 300;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public List<TicketResponseDTO> getMyTickets(Long userId) {
        List<Order> completedOrders = orderRepository.findByUserIdAndStatus(userId, Status.COMPLETED);

        List<OrderItem> items = completedOrders.stream()
            .flatMap(order -> order.getItems().stream())
            .toList();

        Map<String, MatchCatalogDTO> catalogByMatchId = items.stream()
            .map(OrderItem::getMatchId)
            .distinct()
            .collect(Collectors.toMap(Function.identity(), this::getMatchFromCatalog));

        return items.stream()
            .map(item -> toTicketResponseDTO(item, catalogByMatchId.get(item.getMatchId())))
            .toList();
    }

    @Override
    public byte[] getTicketQr(Long ticketId, Long userId) {
        OrderItem item = orderItemRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!item.getOrder().getUserId().equals(userId)) {
            throw new RuntimeException("This ticket does not belong to you");
        }
        if (item.getOrder().getStatus() != Status.COMPLETED) {
            throw new RuntimeException("Ticket is not available yet");
        }

        return generateQr(buildQrPayload(item));
    }

    private TicketResponseDTO toTicketResponseDTO(OrderItem item, MatchCatalogDTO match) {
        String sectorName = match.stadium().sectors().stream()
            .filter(sector -> sector.id().equals(item.getSectorId()))
            .findFirst()
            .map(StadiumSectorCatalogDTO::name)
            .orElse(item.getSectorId());

        return new TicketResponseDTO(
            item.getId(),
            item.getOrder().getId(),
            item.getMatchId(),
            match.localTeam().country(),
            match.awayTeam().country(),
            match.stadium().name(),
            match.stadium().city(),
            match.date(),
            match.stage(),
            item.getSectorId(),
            sectorName,
            item.getQuantity(),
            "/api/sales/tickets/" + item.getId() + "/qr"
        );
    }

    private String buildQrPayload(OrderItem item) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("orderId", item.getOrder().getId());
        payload.put("ticketId", item.getId());
        payload.put("matchId", item.getMatchId());
        payload.put("sectorId", item.getSectorId());
        payload.put("quantity", item.getQuantity());

        try {
            return objectMapper.writeValueAsString(payload);
        } catch (IOException e) {
            throw new IllegalStateException("Error building QR payload", e);
        }
    }

    private byte[] generateQr(String payload) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(payload, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
                return baos.toByteArray();
            }
        } catch (WriterException | IOException e) {
            throw new IllegalStateException("Error generando QR", e);
        }
    }

    private MatchCatalogDTO getMatchFromCatalog(String matchId) {
        String url = "http://ms-catalog/api/catalog/matches/" + matchId;
        return restTemplate.getForObject(url, MatchCatalogDTO.class);
    }

}
