package com.shubh.roamify_services.UserFiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.shubh.roamify_services.TourPackages.TourPackage;
import java.util.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uid")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid") 
    private Long uid;
    private String name;
    private String email;
    private String password;
    private String socialMediaLinks;
    private String contactNumber;
    private String city;
    private String country;
    private String pincode;

    @JsonIgnore
    @OneToMany(mappedBy = "agent",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<TourPackage> createdtourPackages = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "travellerpool",
        joinColumns = @JoinColumn(name = "member_id"),
        inverseJoinColumns = @JoinColumn(name = "tourpackage_id")
    )
    private List<TourPackage> mytourlist = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserReview> reviews;

    public User() {
    }

    public User(Long uid, String name, String email, String password, String contactNumber, String city,
            String country, String pincode) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
    }

  
   
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<TourPackage> getCreatedtourPackages() {
        return createdtourPackages;
    }

    public void setCreatedtourPackages(List<TourPackage> createdtourPackages) {
        this.createdtourPackages = createdtourPackages;
    }

    public List<TourPackage> getMytourlist() {
        return mytourlist;
    }

    public void setMytourlist(List<TourPackage> mytourlist) {
        this.mytourlist = mytourlist;
    }

    public List<UserReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<UserReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return Collections.emptyList();

    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return email; 
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
      return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
      return true;
    }

    public Object orElseThrow(Object object) {
        return null;
    }


 


    

   
   
}
