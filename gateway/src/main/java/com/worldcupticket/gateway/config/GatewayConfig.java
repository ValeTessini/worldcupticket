package com.worldcupticket.gateway.config;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import com.worldcupticket.gateway.security.JwtValidationFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class GatewayConfig {

    private final JwtValidationFilter jwtValidationFilter;

    @Bean
    public RouterFunction<ServerResponse> routeFunctionWithFilter() {
        return RouterFunctions.route()
            .route(RequestPredicates.path("/api/users/**"), http())
            .filter(lb("ms-users"))
            .filter(jwtValidationFilter)
            .build();
    }

}
