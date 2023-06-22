package com.shubh.roamify_services.TourPackages;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.shubh.roamify_services.Utils.GenericRepository;

@Repository
public interface TourCrudRepo extends GenericRepository<TourPackage, Long> {

  List<TourPackage> findByLocationContainingIgnoreCaseAndDueDateTimeAfter(String searchInput,
      LocalDateTime currentDateTime);
  
  
      
//    List<TourPackage> findByLocationContainingIgnoreCaseAndDueDateTimeAfter(String location, LocalDateTime currentDateTime);

    // List<TourPackage> findByCitiesCityNameStartsWithIgnoreCaseAndDueDateTimeAfter(String cityName, LocalDateTime currentDateTime);
  
}
