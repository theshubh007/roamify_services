package com.shubh.roamify_services.TourPackages;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

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

  
  @Autowired
  private Image_CrudRepo image_CrudRepo;

public byte[] compressImage(byte[] imageBytes) throws IOException {
    // Read the image from the byte array
    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
    BufferedImage image = ImageIO.read(inputStream);

    // Create an output stream to store the compressed image
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // Compress the image as JPEG with a specified quality (0.0 to 1.0)
    float imageQuality = 0.5f; // Adjust this value as needed
    ImageIO.write(image, "jpeg", outputStream);

    // Get the compressed image bytes
    byte[] compressedBytes = outputStream.toByteArray();

    // Close the streams
    inputStream.close();
    outputStream.close();

    return compressedBytes;
}







 public ResponseEntity<Object> getAgentDetails(String tid) {
    try {
     TourPackage tourPackage = tourCrudRepo.findById(Long.parseLong(tid)).orElse(null);
      if (tourPackage == null) {
        return Common_response.errorResponse("Tour with the given id does not exist", HttpStatus.BAD_REQUEST);
      }
      User agent = tourPackage.getAgent();
      return Common_response.successResponse(agent);
    }
    catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    }

  public ResponseEntity<Object> exploretours() {
    try {
      List<TourPackage> tourList = tourCrudRepo.findAll();
      if (tourList.isEmpty()) {
        return Common_response.errorResponse("No tours found", HttpStatus.BAD_REQUEST);
      }
      //  for (TourPackage tour : tourList) {
      //       byte[] compressedImage = compressImage(tour.getTourProfileImage());
      //       tour.setTourProfileImage(compressedImage);
      //   }
      return Common_response.exploretour_response(tourList.size(), tourList);
    }
    catch(Exception e){
      return Common_response.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

    }
   
  } 



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
     User agent = tourPackage.getAgent();
        if (agent != null && agent.getUid().equals(userId)) {
            return Common_response.errorResponse("Agent cannot book their own tour package", HttpStatus.BAD_REQUEST);
        }
      int maxPerson = tourPackage.getMaxPerson();
      List<User> userList = tourPackage.getUserlist();

      if (userList.size() >= maxPerson) {
        return Common_response.errorResponse("Tour package is already full", HttpStatus.BAD_REQUEST);
      }

      // int bookedSeats = 0;
      // for (User bookedUser : userList) {
      //   if (bookedUser.getUid().equals(userId)) {
      //     bookedSeats++;
      //   }
      // }
      // if (bookedSeats >= 2) {
      //   return Common_response.errorResponse("User has already booked 2 seats in this tour package",
      //       HttpStatus.BAD_REQUEST);
      // }

      // if (bookedSeats + 1 > 2) {
      //   return Common_response.errorResponse("User can only book a maximum of 2 seats in this tour package",
      //       HttpStatus.BAD_REQUEST);
      // }

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

  public ResponseEntity<Object> createTourPackage(String userId, TourPackage packageInfo, List<City> cityInfoList) {
    try {
      Long uid = Long.parseLong(userId);
      User user = userCrudRepo.findById(uid).orElse(null);
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
      tourPackage.setCreatedDateTime(LocalDateTime.now());
      tourPackage.setDueDateTime(packageInfo.getDueDateTime());
      tourPackage.setTourstartdate(packageInfo.getTourstartdate());
      tourPackage.setTourProfileImage(packageInfo.getTourProfileImage());

      // Create cities and add them to the tour package
      Set<City> cities = new HashSet<>();
      List<Image_holder> imagesList = new ArrayList<>();
      for (City cityInfo : cityInfoList) {
        City city = new City();
        Image_holder image = new Image_holder();
        //  City city;
            String cityName = cityInfo.getCityName().toLowerCase();
            if (cityCrudRepo.existsByCityName(cityName)) {
                city = cityCrudRepo.findByCityName(cityInfo.getCityName()).orElse(null);
                // Update the existing city with the new data
                city.setVideoLink(cityInfo.getVideoLink());
                city.getTourPackages().add(tourPackage);
            } else {
                city = new City();
                city.setCityName(cityName);
                city.setVideoLink(cityInfo.getVideoLink());
                city.getTourPackages().add(tourPackage);
            }
        // city.setCityName(cityInfo.getCityName());
        image.setPlace_images(cityInfo.getPlace_images());
        image.setTourPackage(tourPackage);
        city.setVideoLink(cityInfo.getVideoLink());
        city.getTourPackages().add(tourPackage);
        cities.add(city);
        imagesList.add(image);
      }
      tourPackage.setCities(cities);
      tourPackage.setImagesList(imagesList);

      // Save the tour package and cities
      user.getCreatedtourPackages().add(tourPackage);
      //save user
      userCrudRepo.save(user);

      return Common_response.successResponse("Tour package created successfully");
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  

  public ResponseEntity<Object> deleteTourPackage(String uid, String tid) {
    try {
        Long tourPackageId = Long.parseLong(tid);
        Long userId = Long.parseLong(uid);
        
        User user = userCrudRepo.findById(userId).orElse(null);
        if (user == null) {
            return Common_response.errorResponse("User with the provided ID does not exist", HttpStatus.NOT_FOUND);
        }

        TourPackage tourPackage = tourCrudRepo.findById(tourPackageId).orElse(null);
        if (tourPackage == null) {
            return Common_response.errorResponse("Tour package with the provided ID does not exist", HttpStatus.NOT_FOUND);
        }

       
// Remove associations between tour package and cities
for (City city : tourPackage.getCities()) {
    // Set<TourPackage> cityTourPackages = city.getTourPackages();
    // cityTourPackages.remove(tourPackage);
    // city.setTourPackages(cityTourPackages);
    // cityCrudRepo.save(city);
      city.getTourPackages().removeIf(tp -> tp.gettId().equals(tourPackageId));
            cityCrudRepo.save(city);
}

// Clear the userlist association
for (User member : tourPackage.getUserlist()) {
    member.getMytourlist().removeIf(tp -> tp.gettId().equals(tourPackageId));
    userCrudRepo.save(member);
}

// Clear the agent association
tourPackage.setAgent(null);
tourCrudRepo.save(tourPackage);

// Remove the tour package from the user's created tour packages
// user.getCreatedtourPackages().remove(tourPackage);
// userCrudRepo.save(user);

// Delete the tour package
tourCrudRepo.deleteById(tourPackageId);

return Common_response.successResponse("Tour package deleted successfully");
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

  public ResponseEntity<Object> getAllCitiesByTourPackage(String tourPackageId) {
    try {
      Long tid = Long.parseLong(tourPackageId);
      TourPackage tourPackage = tourCrudRepo.findById(tid).orElse(null);
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

    public ResponseEntity<Object> getTourImagesByTourPackage(String tourPackageId) {
    try {
      Long tid = Long.parseLong(tourPackageId);
      TourPackage tourPackage = tourCrudRepo.findById(tid).orElse(null);
      if (tourPackage == null) {
        // Handle tour package not found
        return Common_response.errorResponse("Tour package with the provided ID does not exist", HttpStatus.NOT_FOUND);
      }

      List<Image_holder> imagesList = tourPackage.getImagesList();
    List<String> images = new ArrayList<>();

for (Image_holder imageHolder : imagesList) {
    byte[] placeImages = imageHolder.getPlace_images();
    String encodedImage = Base64.getEncoder().encodeToString(placeImages);
    images.add(encodedImage);
}
      
      return Common_response.all_images_response(images.size(), images);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<Object> getAllMembers_of_tour(String tourPackageId) {
    try {
      Long tid = Long.parseLong(tourPackageId);
      TourPackage tourPackage = tourCrudRepo.findById(tid).orElse(null);
      if (tourPackage == null) {
        // Handle tour package not found
        return Common_response.errorResponse("Tour package with the provided ID does not exist", HttpStatus.NOT_FOUND);
      }

      List<User> members = tourPackage.getUserlist();
      return Common_response.successResponse(members);
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






  public ResponseEntity<Object> searchTourPackagesByCityNames(String searchInput) {

    try {

      

    // // Search by city name
    // List<TourPackage> packagesByCityName = tourCrudRepo.findByCitiesCityNameStartsWithIgnoreCaseAndDueDateTimeAfter(searchInput, currentDateTime);
    // tourPackages.addAll(packagesByCityName);

    // // Search by tour package location
       LocalDateTime currentDateTime = LocalDateTime.now();
            List<TourPackage> tourPackages = new ArrayList<>();    
    List<TourPackage> packagesByLocation = tourCrudRepo.findByLocationContainingIgnoreCaseAndDueDateTimeAfter(searchInput, currentDateTime);
    tourPackages.addAll(packagesByLocation);

       List<City> cities = cityCrudRepo.findByCityNameStartsWithIgnoreCase(searchInput);
       
        
        for (City city : cities) {
            Set<TourPackage> cityTourPackages = city.getTourPackages();
            for (TourPackage tourPackage : cityTourPackages) {
              if (tourPackage.getDueDateTime().isAfter(currentDateTime)) {
                    tourPackages.add(tourPackage);
                }
            }
        }

      return Common_response.successResponse(tourPackages);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  

  public ResponseEntity<Object> getMybookedTours(String userId) {
    try {
      Long uid = Long.parseLong(userId);
      User user = userCrudRepo.findById(uid).orElse(null);
      if (user == null) {
        // Handle tour package not found
        return Common_response.errorResponse("User with the provided ID does not exist", HttpStatus.NOT_FOUND);
      }

      List<TourPackage> tourPackages = user.getMytourlist();
      return Common_response.successResponse(tourPackages);
    } catch (Exception e) {
      return Common_response.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

  }
  
  public ResponseEntity<Object> getMycreatedTours(String userId) {
    try {
      Long uid = Long.parseLong(userId);
      User user = userCrudRepo.findById(uid).orElse(null);
      if (user == null) {
        // Handle tour package not found
        return Common_response.errorResponse("User with the provided ID does not exist", HttpStatus.NOT_FOUND);
      }

      List<TourPackage> tourPackages = user.getCreatedtourPackages();
      return Common_response.successResponse(tourPackages);
    }
    catch(Exception e){
      return Common_response.errorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);

    }
   
  } 

}
