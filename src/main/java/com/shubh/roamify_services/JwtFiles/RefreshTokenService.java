package com.shubh.roamify_services.JwtFiles;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shubh.roamify_services.JwtFiles.entity.RefreshToken;
import com.shubh.roamify_services.UserFiles.User;
import com.shubh.roamify_services.UserFiles.UserCrudRepo;

@Service
public class RefreshTokenService {
 
  
   @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
  private UserCrudRepo userCrudRepo;

  public RefreshToken createRefreshToken(String useremail) {
      
     User user = userCrudRepo.findByEmail(useremail);
    Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
RefreshToken refreshToken;
if (existingToken.isPresent()) {
    refreshToken = existingToken.get();
    refreshToken.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));
} else {
    refreshToken = RefreshToken.builder()
            .token(UUID.randomUUID().toString())
            .user(user)
            .expiryDate(Instant.now().plus(30, ChronoUnit.DAYS))
            .build();
}


                
    return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
