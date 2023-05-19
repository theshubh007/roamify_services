package com.shubh.roamify_services.TourPackages;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shubh.roamify_services.AgentFiles.Agent;
import com.shubh.roamify_services.UserFiles.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "a_id")
    private Agent agent;

    private String tourName;
    private String location;
    private String description;
    private int days;
    private double chargePerPerson;
    private int maxPerson;
    private boolean approved;
    private int minPerson;

 
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "tour_cities",
            joinColumns = @JoinColumn(name = "t_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private Set<City> cities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public TourPackage() {
    }

    
    


    public TourPackage( Agent agent, String tourName, String location, String description, int days,
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





    public TourPackage(Long tId, Agent agent, String tourName, String location, String description, int days,
            double chargePerPerson, int maxPerson, boolean approved, int minPerson, Set<City> cities, User user) {
        this.tId = tId;
        this.agent = agent;
        this.tourName = tourName;
        this.location = location;
        this.description = description;
        this.days = days;
        this.chargePerPerson = chargePerPerson;
        this.maxPerson = maxPerson;
        this.approved = approved;
        this.minPerson = minPerson;
        this.cities = cities;
        this.user = user;
    }




    public Long gettId() {
        return tId;
    }


    public void settId(Long tId) {
        this.tId = tId;
    }


    public Agent getAgent() {
        return agent;
    }


    public void setAgent(Agent agent) {
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


    public Set<City> getCities() {
        return cities;
    }


    public void setCities(Set<City> cities) {
        this.cities = cities;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
    


}
