package com.worldcupticket.ms_users.dto;

public record RegisterRequestDTO(

    String name,
    String lastName,
    String email,
    String password

) {}
