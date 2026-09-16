package com.worldcupticket.ms_users.dto;

import com.worldcupticket.ms_users.domain.User;

public record UserDTO(

    Long id,
    String name,
    String lastName,
    String email

) {

    public static UserDTO from(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getLastName(),
            user.getEmail()
        );
    }

}
