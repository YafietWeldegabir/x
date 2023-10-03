package com.x.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    public AuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }

}
