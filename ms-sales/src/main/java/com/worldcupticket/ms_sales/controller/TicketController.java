package com.worldcupticket.ms_sales.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldcupticket.ms_sales.dto.TicketResponseDTO;
import com.worldcupticket.ms_sales.service.TicketService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/sales/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getMyTickets(Authentication authentication) {
        return ResponseEntity.ok(ticketService.getMyTickets(Long.parseLong(authentication.getName())));
    }

    @GetMapping(value = "/{ticketId}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getTicketQr(@PathVariable Long ticketId, Authentication authentication) {
        byte[] qr = ticketService.getTicketQr(ticketId, Long.parseLong(authentication.getName()));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qr);
    }

}
