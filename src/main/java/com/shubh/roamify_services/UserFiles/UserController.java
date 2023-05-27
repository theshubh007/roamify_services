package com.shubh.roamify_services.UserFiles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.roamify_services.TourPackages.City;
import com.shubh.roamify_services.TourPackages.TourPackage;
import com.shubh.roamify_services.TourPackages.TourService;
import com.shubh.roamify_services.Utils.dto.CreateTourPackageRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/user")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private TourService tourService;

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
  public ResponseEntity<Object> resetPassword(@RequestParam("email") String email,
      @RequestParam("newPassword") String newPassword) {
    return userService.resetPassword(email, newPassword);
  }

  //     @PostMapping("/create-tour-packages")
  //   public ResponseEntity<Object> createTourPackage(@RequestBody Long userId,
  //     @RequestBody TourPackage packageInfo, @RequestBody List<City> cityInfoList) {
  //   return tourService.createTourPackage(userId, packageInfo, cityInfoList);
  // }

  @PostMapping("/create-tour-packages")
  public ResponseEntity<Object> createTourPackage(@RequestBody CreateTourPackageRequest request) {
    return tourService.createTourPackage(request.getUserId(), request.getPackageInfo(), request.getCityInfoList());
  }


  @GetMapping("/{agentId}/get-my-packages")
  public ResponseEntity<Object> getAllTourPackages(@PathVariable("agentId") Long agentId) {
    return tourService.getAllPackagesByAgent(agentId);
  }

  @GetMapping("/check")
  public String check() {
    return "working jwt";
  }
}
