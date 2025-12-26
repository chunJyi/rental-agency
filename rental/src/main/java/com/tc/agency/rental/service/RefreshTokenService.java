package com.tc.agency.rental.service;


import com.tc.agency.rental.config.JwtConfig;
import com.tc.agency.rental.domain.RefreshToken;
import com.tc.agency.rental.domain.User;
import com.tc.agency.rental.repository.RefreshTokenRepository;
import com.tc.agency.rental.service.imp.RefreshTokenServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService implements RefreshTokenServiceImpl {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<RefreshToken> findByToken(String rawToken) {
        List<RefreshToken> tokens = refreshTokenRepository.findAll(); // or filter by user
        return tokens.stream()
                .filter(t -> passwordEncoder.matches(rawToken, t.getToken()))
                .findFirst();
           }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        String rawToken = UUID.randomUUID().toString();
        String encodedToken = passwordEncoder.encode(rawToken);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtConfig.getRefreshExpirationMs()));
        refreshToken.setToken(encodedToken);

        refreshTokenRepository.save(refreshToken);

        // Create a copy to return, without affecting the persisted entity
        RefreshToken responseToken = new RefreshToken();
        responseToken.setUser(user);
        responseToken.setExpiryDate(refreshToken.getExpiryDate());
        responseToken.setToken(rawToken); // only the raw one for client

        return responseToken;
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    public int deleteByToken(String token) {
        return refreshTokenRepository.deleteByToken(token);
    }

    @Transactional
    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        refreshTokenRepository.delete(oldToken);
        return createRefreshToken(oldToken.getUser());
    }

    public RefreshToken save(RefreshToken rt) {
        return refreshTokenRepository.save(rt);
    }
}
