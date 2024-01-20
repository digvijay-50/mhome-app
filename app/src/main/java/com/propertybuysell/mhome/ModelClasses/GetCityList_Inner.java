package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetCityList_Inner implements Serializable {

    @SerializedName("home_property_city_id")
    @Expose
    private String homePropertyCityId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("city_image")
    @Expose
    private String cityImage;
    @SerializedName("city_image_thumb")
    @Expose
    private String cityImageThumb;
    @SerializedName("status")
    @Expose
    private String status;

    public String getHomePropertyCityId() {
        return homePropertyCityId;
    }

    public void setHomePropertyCityId(String homePropertyCityId) {
        this.homePropertyCityId = homePropertyCityId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityImage() {
        return cityImage;
    }

    public void setCityImage(String cityImage) {
        this.cityImage = cityImage;
    }

    public String getCityImageThumb() {
        return cityImageThumb;
    }

    public void setCityImageThumb(String cityImageThumb) {
        this.cityImageThumb = cityImageThumb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
