package com.shubh.roamify_services.TourPackages;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("tourPackages")
public class City {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    private String cityName;

     @Lob
    @Column(name = "place_images")
     private byte[] place_images;
    
      @Column(name = "video_link")
    private String videoLink;

    @ManyToMany(mappedBy = "cities", fetch = FetchType.LAZY)
    private Set<TourPackage> tourPackages = new HashSet<>();

    public City(String cityName, String videoLink) {
        this.cityName = cityName;
    
        this.videoLink = videoLink;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public byte[] getPlace_images() {
        return place_images;
    }

    public void setPlace_images(byte[] place_images) {
        this.place_images = place_images;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public Set<TourPackage> getTourPackages() {
         Hibernate.initialize(tourPackages);
        return tourPackages;
    }

    public void setTourPackages(Set<TourPackage> tourPackages) {
        this.tourPackages = tourPackages;
    }

  
}
