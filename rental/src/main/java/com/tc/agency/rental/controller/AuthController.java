package com.tc.agency.rental.controller;

import com.tc.agency.rental.config.CustomHttpStatus;
import com.tc.agency.rental.domain.RefreshToken;
import com.tc.agency.rental.domain.Role;
import com.tc.agency.rental.domain.User;
import com.tc.agency.rental.dto.auth.LoginRequest;
import com.tc.agency.rental.dto.auth.RegisterRequest;
import com.tc.agency.rental.dto.auth.TokenRefreshRequest;
import com.tc.agency.rental.dto.auth.TokenRefreshResponse;
import com.tc.agency.rental.repository.RoleRepository;
import com.tc.agency.rental.repository.UserRepository;
import com.tc.agency.rental.security.JwtTokenProvider;
import com.tc.agency.rental.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUserName(loginRequest.getUsername()).orElse(null);
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        String token = tokenProvider.generateToken(user.getUserName());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return ResponseEntity.ok(new TokenRefreshResponse(token, refreshToken.getToken()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        User user = new User();
        user.setUserName(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role r = new Role();
            r.setName("ROLE_USER");
            return roleRepository.save(r);
        });
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestToken = request.getRefreshToken();

        Optional<ResponseEntity<?>> response = refreshTokenService.findByToken(requestToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {

                    // Get user
                    var user = refreshToken.getUser();
                    // Rotate: delete old token
                    refreshTokenService.deleteByUser(user);

                    // Generate new access + refresh tokens
                    String newAccessToken = tokenProvider.generateToken(user.getUserName());
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

                    return ResponseEntity.ok(new TokenRefreshResponse(
                            newAccessToken,
                            newRefreshToken.getToken()
                    ));
                });

        return response.orElseGet(() ->
                ResponseEntity
                        .status(CustomHttpStatus.TOKEN_INVALID.getCode())
                        .body(CustomHttpStatus.TOKEN_INVALID.getMessage())
        );
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody TokenRefreshRequest request) {
        String requestToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestToken).map(rt -> {
            refreshTokenService.deleteByUser(rt.getUser());
            return ResponseEntity.ok("Log out successful");
        }).orElseGet(() -> ResponseEntity.badRequest().body("Invalid refresh token"));
    }
}
