package com.shubh.roamify_services.UserFiles;
import org.springframework.stereotype.Repository;

import com.shubh.roamify_services.Utils.GenericRepository;

@Repository
public interface  UserCrudRepo extends GenericRepository<User, Long> {

  User findByEmail(String email);
    

  
}
