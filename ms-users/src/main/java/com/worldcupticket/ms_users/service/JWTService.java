package com.worldcupticket.ms_users.service;

import com.worldcupticket.ms_users.domain.User;

public interface JWTService {

    public String generateToken(User user);
    public boolean validateToken(String token);
    public String extractId(String token);
    public String extractUserType(String token);

}
