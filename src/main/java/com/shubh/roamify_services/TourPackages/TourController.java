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

  @PostMapping("/explore-tours")
  public ResponseEntity<Object> exploretours() {
    return tourService.exploretours();
  }


  @PostMapping("/get-cities")
  public ResponseEntity<Object> getcities(@RequestBody Map<String, String> request) {
    String tid = request.get("tid");
    return tourService.getAllCitiesByTourPackage(tid);
  }

  @PostMapping("/get-agent")
  public ResponseEntity<Object> getAgent(@RequestBody Map<String, String> request) {
    String tid = request.get("tid");
    return tourService.getAgentDetails(tid);
  }

    @PostMapping("/get-images")
  public ResponseEntity<Object> getimages(@RequestBody Map<String, String> request) {
    String tid = request.get("tid");
    return tourService.getTourImagesByTourPackage(tid);
  }

  
  @PostMapping("/get-tour-members")
    public ResponseEntity<Object> getmembers(@RequestBody Map<String, String> request) {
      String tid = request.get("tid");
      return tourService.getAllMembers_of_tour(tid);
  }
  


  @PostMapping("/book-package")
  public ResponseEntity<Object> bookpackage(@RequestBody Map<String, String> request) {
    String uid = request.get("uid");
    String tid = request.get("tid");
    return tourService.bookpackage(uid, tid);
  }
  

  @PostMapping("/search-tour-bycity")
  public ResponseEntity<Object> searchPackage(@RequestBody Map<String, String> request) {
  String search = request.get("searchinput");
    return tourService.searchTourPackagesByCityNames(search);
  }
  

   @PostMapping("/get-boooked-tours")
   public ResponseEntity<Object> getbookedtours(@RequestBody Map<String, String> request) {
     String uid = request.get("uid");
     return tourService.getMybookedTours(uid);
   }
  
   @PostMapping("/get-created-tours")
   public ResponseEntity<Object> getcreatedtours(@RequestBody Map<String, String> request) {
     String uid = request.get("uid");
     return tourService.getMycreatedTours(uid);
   }
  
    
   @PostMapping("/delete-tours")
   public ResponseEntity<Object> delete(@RequestBody Map<String, String> request) {
     String uid = request.get("uid");
     String tid = request.get("tid");
     return tourService.deleteTourPackage(uid, tid);
   }
  


}
