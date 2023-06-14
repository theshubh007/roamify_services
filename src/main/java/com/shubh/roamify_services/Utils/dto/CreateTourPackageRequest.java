package com.shubh.roamify_services.Utils.dto;

import com.shubh.roamify_services.TourPackages.City;
import java.util.List;
import com.shubh.roamify_services.TourPackages.TourPackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTourPackageRequest {
  String Userid;
  TourPackage tourPackage;
  List<City> cityList;
 

 public String getUserId() {
        return Userid;
    }

    public void setUserId(String Userid) {
        this.Userid = Userid;
    }

    public TourPackage getPackageInfo() {
        return tourPackage;
    }

    public void setPackageInfo(TourPackage tourPackage) {
        this.tourPackage = tourPackage;
    }

    public List<City> getCityInfoList() {
        return cityList;
    }

    public void setCityInfoList(List<City> cityList) {
        this.cityList = cityList;
    }
  
}
