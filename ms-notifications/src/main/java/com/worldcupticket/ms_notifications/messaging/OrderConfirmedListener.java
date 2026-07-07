package com.worldcupticket.ms_notifications.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldcupticket.ms_notifications.service.NotificationService;

@Component
public class OrderConfirmedListener {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public OrderConfirmedListener(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = "order.confirmed.queue")
    public void onOrderConfirmed(String payload) {
        try {
            JsonNode event = objectMapper.readTree(payload);
            String orderId = event.path("orderId").asText();
            String email = event.path("email").asText();
            String customer = event.path("customerName").asText("Cliente");
            String ticketUrl = event.path("ticketUrl").asText("https://worldcupticket.local/ticket/" + orderId);

            byte[] qrImage = notificationService.generateQr("order:" + orderId + ";email:" + email);
            notificationService.sendMail(email,
                    "Your WorldCupTicket order is confirmed",
                    "Hola " + customer + ",\n\nTu orden " + orderId + " está confirmada. Visita: " + ticketUrl,
                    qrImage);

            System.out.println("[ms-notifications] Order confirmed event processed for orderId=" + orderId);
        } catch (Exception ex) {
            System.err.println("[ms-notifications] Failed to process order confirmed event: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
