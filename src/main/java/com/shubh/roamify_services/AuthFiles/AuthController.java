package com.shubh.roamify_services.AuthFiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.roamify_services.UserFiles.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {

  @Autowired
  private AuthService authService;

   @PostMapping("/signup")
   public ResponseEntity<Object> signup(@RequestBody User user) {
     return authService.signup(user);

   }
  
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
      return authService.login(user);
    }
  
@PostMapping("/refreshToken/{token}")
public ResponseEntity<Object> refreshToken(@PathVariable("token") String token) {
    return authService.refreshToken(token);
}

      
     @GetMapping("/hh")
     String hh() {
       return "hello";
     }

   

  
}
