package com.shubh.roamify_services.UserFiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/user")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Object> signup(@RequestBody User user) {
    return userService.signup(user);
    
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody User user) {
    return userService.login(user);
  }

  @PutMapping("/update")
  public ResponseEntity<Object> update(@RequestBody User user) {
    return userService.update(user);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Object> resetPassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword) {
    return userService.resetPassword(email, newPassword);
  }
}
