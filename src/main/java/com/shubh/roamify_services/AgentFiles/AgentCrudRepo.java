package com.shubh.roamify_services.AgentFiles;

import org.springframework.stereotype.Repository;

import com.shubh.roamify_services.Utils.GenericRepository;

@Repository
public interface AgentCrudRepo extends GenericRepository<Agent, Long> {

  Agent findByEmail(String email);
    

}
