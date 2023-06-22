package com.shubh.roamify_services.TourPackages;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.shubh.roamify_services.Utils.GenericRepository;

@Repository
public interface CityCrudRepo extends GenericRepository<City, Long>{

  boolean existsByCityName(String cityName);

  Optional<City> findByCityName(String cityName);
  // List<City> findByCityNameStartsWithIgnoreCase(String cityName);

  List<City> findByCityNameStartsWithIgnoreCase(String searchInput);


 
  
}