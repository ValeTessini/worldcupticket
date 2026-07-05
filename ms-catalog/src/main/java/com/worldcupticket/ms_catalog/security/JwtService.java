package com.worldcupticket.ms_catalog.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token);
    String extractUsername(String token);
}
