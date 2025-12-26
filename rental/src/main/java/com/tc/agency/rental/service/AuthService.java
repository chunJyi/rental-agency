package com.tc.agency.rental.service;

import com.tc.agency.rental.domain.Role;
import com.tc.agency.rental.domain.User;
import com.tc.agency.rental.dto.auth.RegisterRequest;
import com.tc.agency.rental.repository.RoleRepository;
import com.tc.agency.rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest req) {
        User user = new User();
        user.setUserName(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role r = new Role();
            r.setName("ROLE_USER");
            return roleRepository.save(r);
        });
        user.setRoles(Collections.singleton(role));
        return userRepository.save(user);
    }
}
