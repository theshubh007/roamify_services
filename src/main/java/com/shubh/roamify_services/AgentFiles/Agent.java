package com.shubh.roamify_services.AgentFiles;



import java.util.List;

import com.shubh.roamify_services.TourPackages.TourPackage;
import com.shubh.roamify_services.UserFiles.UserReview;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Agent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long agentId;
    private String name;
    private String email;
    private String password;
    private String socialMediaLinks;
    private String contactNumber;
    private String country;
    private String city;
    private String pincode;

    @OneToMany(mappedBy = "agent")
    private List<TourPackage> tourPackages;
  
    @OneToMany(mappedBy = "agent")
    private List<UserReview> reviews;

    public Agent(Long agentId, String name, String emailId,String password ,String socialMediaLinks, String contactNumber,
        String country, String city, String pincode) {
      this.agentId = agentId;
      this.name = name;
      this.email = emailId;
      this.password = password;
      this.socialMediaLinks = socialMediaLinks;
      this.contactNumber = contactNumber;
      this.country = country;
      this.city = city;
      this.pincode = pincode;
    }

    public Long getAgentId() {
      return agentId;
    }

    public void setAgentId(Long agentId) {
      this.agentId = agentId;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String emailId) {
      this.email = emailId;
    }
    public void setPassword(String password) {
      // Encrypt the password before storing
      // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      
      this.password = password;
    }
    
    public String getPassword() {
      return password;
    }
    public String getSocialMediaLinks() {
      return socialMediaLinks;
    }

    public void setSocialMediaLinks(String socialMediaLinks) {
      this.socialMediaLinks = socialMediaLinks;
    }

    public String getContactNumber() {
      return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
      this.contactNumber = contactNumber;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getPincode() {
      return pincode;
    }

    public void setPincode(String pincode) {
      this.pincode = pincode;
    }

    public Agent() {
    }

    public List<TourPackage> getTourPackages() {
    return tourPackages;
  }

  public void setTourPackages(List<TourPackage> tourPackages) {
    this.tourPackages = tourPackages;
  }
    
}
