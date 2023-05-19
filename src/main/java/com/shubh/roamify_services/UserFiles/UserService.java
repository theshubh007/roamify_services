package com.shubh.roamify_services.UserFiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.shubh.roamify_services.Utils.Common_response;

@Service
public class UserService {
  
  @Autowired
  private UserCrudRepo userCrudRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;



  //signup
  public ResponseEntity<Object> signup(User user) {
    try {
      User existinguser = userCrudRepo.findByEmail(user.getEmail());
      if (existinguser != null) {
        return Common_response.errorResponse("User with given email already exists", HttpStatus.BAD_REQUEST);
      }

      String encryptedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encryptedPassword);
      User user1 = userCrudRepo.save(user);
      return Common_response.successResponse(user1);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  //login
  public ResponseEntity<Object> login(User user) {
    try {
      User existingUser = userCrudRepo.findByEmail(user.getEmail());
      if (existingUser == null) {
        return Common_response.errorResponse("User with given email does not exist", HttpStatus.BAD_REQUEST);
      }

      if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
        return Common_response.errorResponse(
            "Invalid password" + existingUser.getPassword() + "\n" + passwordEncoder.encode(user.getPassword()),
            HttpStatus.BAD_REQUEST);
      }

      return Common_response.successResponse(existingUser);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //reset password
  public ResponseEntity<Object> resetPassword(String email, String newPassword) {
    try {
      User existingUser = userCrudRepo.findByEmail(email);
      if (existingUser == null) {
        return Common_response.errorResponse("User with the provided email does not exist", HttpStatus.BAD_REQUEST);
      }

      String encryptedPassword = passwordEncoder.encode(newPassword);
      existingUser.setPassword(encryptedPassword);
      User updatedUser = userCrudRepo.save(existingUser);

      return Common_response.successResponse(updatedUser);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  






  //update
  public ResponseEntity<Object> update(User user) {
    try {
      User existinguser = userCrudRepo.findByEmail(user.getEmail());
      if (existinguser == null) {
        return Common_response.errorResponse("User with given email does not exists", HttpStatus.BAD_REQUEST);
      }
      existinguser.setName(user.getName());
      existinguser.setContactNumber(user.getContactNumber());
      existinguser.setCity(user.getCity());
      existinguser.setCountry(user.getCountry());
      existinguser.setPincode(user.getPincode());
      User user1 = userCrudRepo.save(existinguser);
      return Common_response.successResponse(user1);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  


}
