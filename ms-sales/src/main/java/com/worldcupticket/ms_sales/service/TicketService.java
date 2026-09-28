package com.worldcupticket.ms_sales.service;

import java.util.List;

import com.worldcupticket.ms_sales.dto.TicketResponseDTO;

public interface TicketService {

    List<TicketResponseDTO> getMyTickets(Long userId);
    byte[] getTicketQr(Long ticketId, Long userId);

}
