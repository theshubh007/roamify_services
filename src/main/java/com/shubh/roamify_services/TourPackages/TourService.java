package com.shubh.roamify_services.TourPackages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.webresources.Cache;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shubh.roamify_services.UserFiles.User;
import com.shubh.roamify_services.UserFiles.UserCrudRepo;
import com.shubh.roamify_services.Utils.dto.Common_response;

import jakarta.transaction.Transactional;

@Service
public class TourService {

  @Autowired
  private TourCrudRepo tourCrudRepo;

  @Autowired
  private UserCrudRepo userCrudRepo;

  @Autowired
  private CityCrudRepo cityCrudRepo;

  public ResponseEntity<Object> bookpackage(String uid, String tid) {
    try {
      Long userId = Long.parseLong(uid);
      Long tourPackageId = Long.parseLong(tid);

      TourPackage tourPackage = tourCrudRepo.findById(tourPackageId).orElse(null);
      if (tourPackage == null) {
        return Common_response.errorResponse("Tour with the given id does not exist", HttpStatus.BAD_REQUEST);
      }

      User user = userCrudRepo.findById(userId).orElse(null);
      if (user == null) {
        return Common_response.errorResponse("User with the given id does not exist", HttpStatus.BAD_REQUEST);
      }

      int maxPerson = tourPackage.getMaxPerson();
      List<User> userList = tourPackage.getUserlist();

      if (userList.size() >= maxPerson) {
        return Common_response.errorResponse("Tour package is already full", HttpStatus.BAD_REQUEST);
      }

      int bookedSeats = 0;
      for (User bookedUser : userList) {
        if (bookedUser.getUid().equals(userId)) {
          bookedSeats++;
        }
      }
      if (bookedSeats >= 2) {
        return Common_response.errorResponse("User has already booked 2 seats in this tour package",
            HttpStatus.BAD_REQUEST);
      }

      if (bookedSeats + 1 > 2) {
        return Common_response.errorResponse("User can only book a maximum of 2 seats in this tour package",
            HttpStatus.BAD_REQUEST);
      }

      userList.add(user);
      tourPackage.setUserlist(userList);
      tourCrudRepo.save(tourPackage);

      // Add tour package to user's mytourlist
      List<TourPackage> myTourList = user.getMytourlist();
      myTourList.add(tourPackage);
      user.setMytourlist(myTourList);
      userCrudRepo.save(user);
      return Common_response.successResponse("User successfully booked the tour package");
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<Object> createTourPackage(Long userId, TourPackage packageInfo, List<City> cityInfoList) {
    try {
      User user = userCrudRepo.findById(userId).orElse(null);
      if (user == null) {
        return Common_response.errorResponse("User with the provided ID does not exist", HttpStatus.NOT_FOUND);
      }
      // Create a new tour package
      TourPackage tourPackage = new TourPackage();
      tourPackage.setAgent(user);
      tourPackage.setTourName(packageInfo.getTourName());
      tourPackage.setLocation(packageInfo.getLocation());
      tourPackage.setDescription(packageInfo.getDescription());
      tourPackage.setDays(packageInfo.getDays());
      tourPackage.setChargePerPerson(packageInfo.getChargePerPerson());
      tourPackage.setMaxPerson(packageInfo.getMaxPerson());
      tourPackage.setApproved(packageInfo.isApproved());
      tourPackage.setMinPerson(packageInfo.getMinPerson());
      // tourPackage.setDueDateTime(packageInfo.getDueDateTime());

      // Create cities and add them to the tour package
      Set<City> cities = new HashSet<>();
      for (City cityInfo : cityInfoList) {
        City city = new City();
        city.setCityName(cityInfo.getCityName());
        city.setPlace_images(cityInfo.getPlace_images());
        city.setVideoLink(cityInfo.getVideoLink());
        city.getTourPackages().add(tourPackage);
        cities.add(city);
      }
      tourPackage.setCities(cities);

      // Save the tour package and cities
      user.getCreatedtourPackages().add(tourPackage);
      //save user
      userCrudRepo.save(user);

      return Common_response.successResponse("Tour package created successfully");
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  public ResponseEntity<Object> getAllPackagesByAgent(Long userId) {
    try{
    User user = userCrudRepo.findById(userId).orElse(null);
    if (user == null) {
      return Common_response.errorResponse("User with the provided ID does not exist", HttpStatus.NOT_FOUND);

    }
     Hibernate.initialize(user.getCreatedtourPackages());
     List<TourPackage> tourPackages = user.getCreatedtourPackages();
     for (TourPackage tourPackage : tourPackages) {
       Hibernate.initialize(tourPackage.getCities());
     }
    
     
    return Common_response.successResponse(tourPackages);
  }catch(Exception e){
    return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  }

  public ResponseEntity<Object> getAllCitiesByTourPackage(Long tourPackageId) {
    try {
      TourPackage tourPackage = tourCrudRepo.findById(tourPackageId).orElse(null);
      if (tourPackage == null) {
        // Handle tour package not found
        return Common_response.errorResponse("Tour package with the provided ID does not exist", HttpStatus.NOT_FOUND);
      }

      Set<City> cities = tourPackage.getCities();
      return Common_response.successResponse(cities);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<Object> getAllDetailsByTourPackage(Long tourPackageId) {
    try {
      TourPackage tourPackage = tourCrudRepo.findById(tourPackageId).orElse(null);
      if (tourPackage == null) {
        // Handle tour package not found
        return Common_response.errorResponse("Tour package with the provided ID does not exist", HttpStatus.NOT_FOUND);
      }

      List<User> users = tourPackage.getUserlist();
      return Common_response.successResponse(users);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }






  public ResponseEntity<Object> searchTourPackagesByCityNames(List<String> cityNames) {

    try {
      // LocalDateTime currentDateTime = LocalDateTime.now();
      // List<TourPackage> allTourPackages = tourCrudRepo.findAll();

      // List<TourPackage> filteredPackages = allTourPackages.stream()
      //     .filter(tourPackage -> tourPackage.getCreatedDateTime().isAfter(currentDateTime))
      //     .filter(tourPackage -> tourPackage.getCities().stream()
      //         .anyMatch(city -> cityNames.contains(city.getCityName())))
      //     .collect(Collectors.toList());

      // return Common_response.successResponse(filteredPackages);

        LocalDateTime currentDateTime = LocalDateTime.now();
        List<TourPackage> allTourPackages = tourCrudRepo.findAll();

        List<TourPackage> filteredPackages = allTourPackages.stream()
                .filter(tourPackage -> {
                    LocalDateTime createdDateTime = tourPackage.getCreatedDateTime();
                    return createdDateTime != null && createdDateTime.isAfter(currentDateTime);
                })
                .filter(tourPackage -> tourPackage.getCities().stream()
                        .anyMatch(city -> cityNames.contains(city.getCityName())))
            .collect(Collectors.toList());
                
        return Common_response.successResponse(filteredPackages);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
}
