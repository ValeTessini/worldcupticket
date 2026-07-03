package com.worldcupticket.gateway.security;

import com.worldcupticket.gateway.service.JWTService;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtValidationFilter implements HandlerFilterFunction<ServerResponse, ServerResponse>{

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public final JWTService jwtService;

    @Override
    public ServerResponse filter(ServerRequest request,HandlerFunction<ServerResponse> next) throws Exception {

        String path = request.path();
        if (path.equals("/api/users/register")||path.equals("/api/users/login")) {
            return next.handle(request);
        }

        String token = request.headers().firstHeader(AUTH_HEADER);
        if(token == null || !token.startsWith(BEARER_PREFIX)){
            return ServerResponse.status(401).build();
        }

        String jwtToken = token.substring(7);
        if(!jwtService.validateToken(jwtToken)){
            return ServerResponse.status(401).build();
        }

        return next.handle(request);

    }

}
