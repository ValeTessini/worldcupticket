package com.worldcupticket.ms_catalogo.service;

public interface JWTService {

    public boolean validateToken(String token);
    public String extractId(String token);
    public String extractUserType(String token);

}
