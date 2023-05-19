package com.shubh.roamify_services.AgentFiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shubh.roamify_services.TourPackages.City;
import com.shubh.roamify_services.TourPackages.TourCrudRepo;
import com.shubh.roamify_services.TourPackages.TourPackage;
import com.shubh.roamify_services.Utils.Common_response;

@Service
public class AgentService {
  
  @Autowired
  private AgentCrudRepo agentCrudRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TourCrudRepo tourCrudRepo;

    //signup
    public ResponseEntity<Object> signup(Agent agent) {
      try {
        Agent existingagent = agentCrudRepo.findByEmail(agent.getEmail());
        if (existingagent != null) {
          return Common_response.errorResponse("Agent with given email already exists", HttpStatus.BAD_REQUEST);
        }

        String encryptedPassword = passwordEncoder.encode(agent.getPassword());
        agent.setPassword(encryptedPassword);
        Agent agent1 = agentCrudRepo.save(agent);
        return Common_response.successResponse(agent1);
      } catch (Exception e) {
        return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
  


  //login
    public ResponseEntity<Object> login(Agent agent) {
        try {
            Agent existingAgent = agentCrudRepo.findByEmail(agent.getEmail());
            if (existingAgent == null) {
                return Common_response.errorResponse("Agent with the given email does not exist", HttpStatus.BAD_REQUEST);
            }

            if (!passwordEncoder.matches(agent.getPassword(), existingAgent.getPassword())) {
                return Common_response.errorResponse("Invalid password", HttpStatus.BAD_REQUEST);
            }

            return Common_response.successResponse(existingAgent);
        } catch (Exception e) {
            return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//reset password
    public ResponseEntity<Object> resetPassword(String email, String newPassword) {
        try {
            Agent existingAgent = agentCrudRepo.findByEmail(email);
            if (existingAgent == null) {
                return Common_response.errorResponse("Agent with the provided email does not exist", HttpStatus.BAD_REQUEST);
            }

            String encryptedPassword = passwordEncoder.encode(newPassword);
            existingAgent.setPassword(encryptedPassword);
            Agent updatedAgent = agentCrudRepo.save(existingAgent);

            return Common_response.successResponse(updatedAgent);
        } catch (Exception e) {
            return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//update
    public ResponseEntity<Object> update(Agent agent) {
        try {
            Agent existingAgent = agentCrudRepo.findByEmail(agent.getEmail());
            if (existingAgent == null) {
                return Common_response.errorResponse("Agent with the given email does not exist", HttpStatus.BAD_REQUEST);
            }
            existingAgent.setName(agent.getName());
            existingAgent.setContactNumber(agent.getContactNumber());
            existingAgent.setCity(agent.getCity());
            existingAgent.setCountry(agent.getCountry());
            existingAgent.setPincode(agent.getPincode());
            Agent updatedAgent = agentCrudRepo.save(existingAgent);
            return Common_response.successResponse(updatedAgent);
        } catch (Exception e) {
            return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




   // Fetch all tour packages associated with an agent
   public ResponseEntity<Object> getAllTourPackages(Long agentId) {
     try {
       Agent agent = agentCrudRepo.findById(agentId).orElse(null);
       if (agent == null) {
         return Common_response.errorResponse("Agent with the provided ID does not exist", HttpStatus.NOT_FOUND);
       }

       return Common_response.successResponse(agent.getTourPackages());
     } catch (Exception e) {
       return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
     }
   }
  

   //create tour package
public ResponseEntity<Object> createTourPackage(Long Agentid,TourPackage tourDetails) {
    try {
        Agent agent = agentCrudRepo.findById(Agentid).orElse(null);
        if (agent == null) {
            return Common_response.errorResponse("Agent with the provided ID does not exist", HttpStatus.NOT_FOUND);
        }

        TourPackage tourPackage = new TourPackage();
        tourPackage.setAgent(agent);
        tourPackage.setTourName(tourDetails.getTourName());
        tourPackage.setLocation(tourDetails.getLocation());
        tourPackage.setDescription(tourDetails.getDescription());
        tourPackage.setDays(tourDetails.getDays());
        tourPackage.setChargePerPerson(tourDetails.getChargePerPerson());
        tourPackage.setMaxPerson(tourDetails.getMaxPerson());
        tourPackage.setApproved(tourDetails.isApproved());
        tourPackage.setMinPerson(tourDetails.getMinPerson());
        // tourPackage.setCities(new HashSet<>(cities));

        TourPackage createdPackage = tourCrudRepo.save(tourPackage);
        return Common_response.successResponse(createdPackage);
    } catch (Exception e) {
        return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
