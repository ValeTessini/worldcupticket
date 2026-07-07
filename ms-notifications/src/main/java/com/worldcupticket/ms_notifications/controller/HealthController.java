package com.worldcupticket.ms_notifications.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/public/status")
    public String status() {
        return "ms-notifications OK";
    }
}
