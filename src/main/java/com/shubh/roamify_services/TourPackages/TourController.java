package com.shubh.roamify_services.TourPackages;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/tour")
public class TourController {

  @Autowired
  private TourService tourService;

  @PostMapping("/book-package")
  public ResponseEntity<Object> bookpackage(@RequestBody Map<String, String> request) {
    String uid = request.get("uid");
    String tid = request.get("tid");
    return tourService.bookpackage(uid, tid);
  }
  

  @PostMapping("/search-tour")
public ResponseEntity<Object> searchPackage(@RequestBody Map<String, List<String>> request) {
    List<String> cityNames = request.get("cityNames");
    return tourService.searchTourPackagesByCityNames(cityNames);
}
  
}
