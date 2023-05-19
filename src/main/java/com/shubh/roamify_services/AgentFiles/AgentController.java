package com.shubh.roamify_services.AgentFiles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.roamify_services.TourPackages.City;
import com.shubh.roamify_services.TourPackages.TourPackage;

@RestController
@RequestMapping("/v1/api/agent")
public class AgentController {
  
  @Autowired
  private AgentService agentService;



   @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody Agent agent) {
        return agentService.signup(agent);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Agent agent) {
        return agentService.login(agent);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody String email, String password) {
        return agentService.resetPassword(email,password);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Agent agent) {
        return agentService.update(agent);
    }

    @PostMapping("/{agentId}/create-tour-packages")
    public ResponseEntity<Object> createTourPackage(
            @PathVariable("agentId") Long agentId,
            @RequestBody TourPackage tourDetails
    ) {
      
        return agentService.createTourPackage(agentId, tourDetails);
    }

    @GetMapping("/{agentId}/tour-packages")
    public ResponseEntity<Object> getAllTourPackages(@PathVariable("agentId") Long agentId) {
        return agentService.getAllTourPackages(agentId);
    }
}
