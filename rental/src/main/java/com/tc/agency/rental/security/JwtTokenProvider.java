package com.tc.agency.rental.security;

import com.tc.agency.rental.config.JwtConfig;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtConfig jwtConfig;

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getJwtExpirationMs());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
                .compact();
    }

    public UUID getUserId(String token) {
        return UUID.fromString(
                Jwts.parserBuilder().setSigningKey(jwtConfig.getJwtSecret().getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject()
        );
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT token is expired");
//            logger.error("", ex);
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
//            logger.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
//            logger.error("Invalid JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
//            logger.error("Unsupported JWT token", ex);
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
//            logger.error("JWT claims string is empty.", ex);
        }
        return false;
    }
}
