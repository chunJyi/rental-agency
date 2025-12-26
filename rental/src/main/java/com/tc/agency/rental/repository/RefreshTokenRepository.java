package com.tc.agency.rental.repository;

import com.tc.agency.rental.domain.RefreshToken;
import com.tc.agency.rental.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    int deleteByToken(String token);
}
