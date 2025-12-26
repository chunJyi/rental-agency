package com.tc.agency.rental.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${app.jwt.secret:2B7E151628AED2A6ABF7158809CF4F3C762E7160F38B4DA56B3F9FA1F4D1A2B72B7E151628AED2A6ABF7158809CF4F3C762E7160F38B4DA56B3F9FA1F4D1A2B72B7E151628AED2A6ABF7158809CF4F3C762E7160F38B4DA56B3F9FA1F4D1A2B7}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms:100000000}")
    private long jwtExpirationMs;

    @Value("${app.jwt.refresh-expiration-ms:604800000}") // 7 days default
    private long refreshExpirationMs;

    public String getJwtSecret() { return jwtSecret; }
    public long getJwtExpirationMs() { return jwtExpirationMs; }
    public long getRefreshExpirationMs() { return refreshExpirationMs; }
}
