package com.shubh.roamify_services.TourPackages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.shubh.roamify_services.UserFiles.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"cities", "userlist"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tId")
public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tId;

   
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonBackReference
    @JoinColumn(name = "agent_id")
    private User agent;

    private String tourName;
    private String location;
    private String description;
    private int days;
    private double chargePerPerson;
    private int maxPerson;
    private boolean approved;
    private int minPerson;
    
    private LocalDateTime createdDateTime;
    private LocalDateTime tourstartdate;
    private LocalDateTime dueDateTime;
  @Lob
@Column(name = "tour_profile_image", length = 1048576) // Example: Set the length to 1 MB (1048576 bytes)
private byte[] tourProfileImage;

 

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "tourpackage_city",
            joinColumns = @JoinColumn(name = "tourpackage_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id"))
    private Set<City> cities = new HashSet<>();
    

    @JsonIgnore
    @ManyToMany(mappedBy = "mytourlist",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> userlist = new ArrayList<>();


    @OneToMany(mappedBy = "tourPackage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image_holder> imagesList = new ArrayList<>();


   public TourPackage(User agent, String tourName, String location, String description, int days,
            double chargePerPerson, int maxPerson, boolean approved, int minPerson, LocalDateTime createdDateTime,
            LocalDateTime dueDateTime,
            LocalDateTime tourstartdate,
            Set<City> cities) {
        this.agent = agent;
        this.tourName = tourName;
        this.location = location;
        this.description = description;
        this.days = days;
        this.chargePerPerson = chargePerPerson;
        this.maxPerson = maxPerson;
        this.approved = approved;
        this.minPerson = minPerson;
        this.createdDateTime = createdDateTime;
        this.dueDateTime = dueDateTime;
        this.cities = cities;

    }

    public TourPackage( User agent, String tourName, String location, String description, int days,
            double chargePerPerson, int maxPerson, boolean approved, int minPerson) {
    
        this.agent = agent;
        this.tourName = tourName;
        this.location = location;
        this.description = description;
        this.days = days;
        this.chargePerPerson = chargePerPerson;
        this.maxPerson = maxPerson;
        this.approved = approved;
        this.minPerson = minPerson;
    }

  
    
    



 public byte[] getTourProfileImage() {
        return tourProfileImage;
    }

    public void setTourProfileImage(byte[] tourProfileImage) {
        this.tourProfileImage = tourProfileImage;
    }



    public Long gettId() {
        return tId;
    }











    public void settId(Long tId) {
        this.tId = tId;
    }











    public User getAgent() {
        return agent;
    }











    public void setAgent(User agent) {
        this.agent = agent;
    }











    public String getTourName() {
        return tourName;
    }











    public void setTourName(String tourName) {
        this.tourName = tourName;
    }











    public String getLocation() {
        return location;
    }











    public void setLocation(String location) {
        this.location = location;
    }











    public String getDescription() {
        return description;
    }











    public void setDescription(String description) {
        this.description = description;
    }











    public int getDays() {
        return days;
    }



    public void setDays(int days) {
        this.days = days;
    }

    public double getChargePerPerson() {
        return chargePerPerson;
    }

    public void setChargePerPerson(double chargePerPerson) {
        this.chargePerPerson = chargePerPerson;
    }

    public int getMaxPerson() {
        return maxPerson;
    }


    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }


    public boolean isApproved() {
        return approved;
    }


    public void setApproved(boolean approved) {
        this.approved = approved;
    }


    public int getMinPerson() {
        return minPerson;
    }


    public void setMinPerson(int minPerson) {
        this.minPerson = minPerson;
    }


    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }


    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }


    public Set<City> getCities() {
        return cities;
    }











    public void setCities(Set<City> cities) {
        this.cities = cities;
    }











    public List<User> getUserlist() {
        return userlist;
    }











    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }







    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }







    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public LocalDateTime getTourstartdate() {
        return tourstartdate;
    }

    public void setTourstartdate(LocalDateTime tourstartdate) {
        this.tourstartdate = tourstartdate;
    }

    public List<Image_holder> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Image_holder> imagesList) {
        this.imagesList = imagesList;
    }













  



}
