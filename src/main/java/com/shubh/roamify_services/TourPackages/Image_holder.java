package com.shubh.roamify_services.TourPackages;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Image_holder {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Image_id;

      @Lob
    @Column(name = "place_images",length = 1048576)
      private byte[] place_images;

      @ManyToOne
      private TourPackage tourPackage;

      public TourPackage getTourPackage() {
        return tourPackage;
      }

      public void setTourPackage(TourPackage tourPackage) {
        this.tourPackage = tourPackage;
      }

      public Long getImage_id() {
        return Image_id;
      }

      public void setImage_id(Long image_id) {
        Image_id = image_id;
      }

      public byte[] getPlace_images() {
        return place_images;
      }

      public void setPlace_images(byte[] place_images) {
        this.place_images = place_images;
      }
    
     
  
}
