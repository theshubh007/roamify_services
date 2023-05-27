package com.shubh.roamify_services.AuthFiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Lettuce.Cluster.Refresh;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shubh.roamify_services.JwtFiles.JwtService;
import com.shubh.roamify_services.JwtFiles.RefreshTokenService;
import com.shubh.roamify_services.JwtFiles.entity.RefreshToken;
import com.shubh.roamify_services.TourPackages.TourCrudRepo;
import com.shubh.roamify_services.UserFiles.User;
import com.shubh.roamify_services.UserFiles.UserCrudRepo;
import com.shubh.roamify_services.Utils.dto.Common_response;
import com.shubh.roamify_services.Utils.dto.JwtResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  
  @Autowired
  private UserCrudRepo userCrudRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

  @Autowired
  private final AuthenticationManager authenticationManager;
  
  @Autowired
  private JwtService jwtService;

  @Autowired
  private RefreshTokenService refreshTokenService;

  
  
  public ResponseEntity<Object> signup(User user) {
    try {
      User existinguser = userCrudRepo.findByEmail(user.getEmail());
      if (existinguser != null) {
        return Common_response.errorResponse("User with given email already exists", HttpStatus.BAD_REQUEST);
      }

      String encryptedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encryptedPassword);
      User user1 = userCrudRepo.save(user);
      var jwtToken = jwtService.generateToken(user);
      return Common_response.successResponsetocken(user1, jwtToken);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  public ResponseEntity<Object> login(User user) {
    try {

      System.out.println("entered into login endpoint");
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

      User existingUser = userCrudRepo.findByEmail(user.getEmail());
      if (existingUser == null) {
        return Common_response.errorResponse("User with given email does not exist", HttpStatus.BAD_REQUEST);
      }

      RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
      var jwtToken = jwtService.generateToken(user);
      return Common_response.jwtsuccessRes(refreshToken.getToken(), jwtToken);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  


  public ResponseEntity<Object> refreshToken(String tokenid) {
    return refreshTokenService.findByToken(tokenid)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                String accessToken = jwtService.generateToken(user);
          return Common_response.jwtsuccessRes(accessToken, tokenid);
                      
            })
            .orElseThrow(() -> new RuntimeException("Refresh token is not in the database!"));
}




}
