package com.shubh.roamify_services.UserFiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
  
  
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String password;
     private String socialMediaLinks;
    private String contactNumber;
    private String city;
    private String country;
    private String pincode;


    
    public User() {
    }
    

    public User(Long userId, String name, String email,String password ,String contactNumber, String city, String country,
        String pincode) {
      this.userId = userId;
      this.name = name;
      this.email = email;
      this.password = password;
      this.contactNumber = contactNumber;
      this.city = city;
      this.country = country;
      this.pincode = pincode;
    }
    public Long getUserId() {
      return userId;
    }
    public void setUserId(Long userId) {
      this.userId = userId;
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

    public void setEmail(String email) {
      this.email = email;
    }

    public void setPassword(String password) {
      // Encrypt the password before storing
      // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      
      this.password = password;
    }
    
    public String getPassword() {
      return password;
    }

    public boolean verifyPassword(String rawPassword) {
        // Compare the raw password with the encrypted password
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, this.password);
    }
    

    
    public String getContactNumber() {
      return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
      this.contactNumber = contactNumber;
    }
    public String getCity() {
      return city;
    }
    public void setCity(String city) {
      this.city = city;
    }
    public String getCountry() {
      return country;
    }
    public void setCountry(String country) {
      this.country = country;
    }
    public String getPincode() {
      return pincode;
    }
    public void setPincode(String pincode) {
      this.pincode = pincode;
    }


  }
