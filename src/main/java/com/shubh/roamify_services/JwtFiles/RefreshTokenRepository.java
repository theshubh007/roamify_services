package com.shubh.roamify_services.JwtFiles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shubh.roamify_services.JwtFiles.entity.RefreshToken;
import com.shubh.roamify_services.UserFiles.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);
}

