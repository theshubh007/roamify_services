package com.shubh.roamify_services.Utils.dto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shubh.roamify_services.JwtFiles.entity.RefreshToken;
import com.shubh.roamify_services.TourPackages.TourPackage;


public class Common_response {

    public static ResponseEntity<Object> successResponsetocken(Object data, String tocken) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("tocken", tocken);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
    

      public static ResponseEntity<Object> exploretour_response(int i, List<TourPackage> tourList) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("totalResponse", i);
        response.put("tourList", tourList);
         return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Object> successResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
    
    public static ResponseEntity<Object> jwtsuccessRes(String refreshToken, String accessTocken) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("tockenid", refreshToken);
        response.put("tocken", accessTocken);
        
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<Object> errorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }

  


}
