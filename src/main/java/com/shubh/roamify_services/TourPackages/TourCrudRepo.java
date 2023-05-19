package com.shubh.roamify_services.TourPackages;
import org.springframework.stereotype.Repository;

import com.shubh.roamify_services.Utils.GenericRepository;

@Repository
public interface TourCrudRepo extends GenericRepository<TourPackage, Long>{
  
}
