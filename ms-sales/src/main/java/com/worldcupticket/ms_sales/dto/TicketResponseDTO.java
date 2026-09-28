package com.worldcupticket.ms_sales.dto;

import java.time.LocalDateTime;

public record TicketResponseDTO(
    Long ticketId,
    Long orderId,
    String matchId,
    String localTeam,
    String awayTeam,
    String stadiumName,
    String city,
    LocalDateTime date,
    String stage,
    String sectorId,
    String sectorName,
    Integer quantity,
    String qrCodeUrl
) {}
