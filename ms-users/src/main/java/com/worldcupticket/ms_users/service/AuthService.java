package com.worldcupticket.ms_users.service;

import com.worldcupticket.ms_users.dto.RegisterRequestDTO;
import com.worldcupticket.ms_users.dto.LoginRequestDTO;
import com.worldcupticket.ms_users.dto.AuthResponseDTO;

public interface AuthService {

    

    public AuthResponseDTO register(RegisterRequestDTO request);
    public AuthResponseDTO login(LoginRequestDTO request);

}
