package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetAdverti_Inner implements Serializable {
    @SerializedName("advertisement_banner_id")
    @Expose
    private String advertisementBannerId;
    @SerializedName("banner_type")
    @Expose
    private String bannerType;
    @SerializedName("banner_property_type")
    @Expose
    private String bannerPropertyType;
    @SerializedName("property_id")
    @Expose
    private String propertyId;

    @SerializedName("banner_link")
    @Expose
    private String banner_link;
    @SerializedName("banner_start_date")
    @Expose
    private String bannerStartDate;
    @SerializedName("banner_end_date")
    @Expose
    private String bannerEndDate;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("banner_status")
    @Expose
    private Integer bannerStatus;

    public String getAdvertisementBannerId() {
        return advertisementBannerId;
    }

    public void setAdvertisementBannerId(String advertisementBannerId) {
        this.advertisementBannerId = advertisementBannerId;
    }


    public String getBanner_link() {
        return banner_link;
    }

    public void setBanner_link(String banner_link) {
        this.banner_link = banner_link;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getBannerPropertyType() {
        return bannerPropertyType;
    }

    public void setBannerPropertyType(String bannerPropertyType) {
        this.bannerPropertyType = bannerPropertyType;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getBannerStartDate() {
        return bannerStartDate;
    }

    public void setBannerStartDate(String bannerStartDate) {
        this.bannerStartDate = bannerStartDate;
    }

    public String getBannerEndDate() {
        return bannerEndDate;
    }

    public void setBannerEndDate(String bannerEndDate) {
        this.bannerEndDate = bannerEndDate;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(Integer bannerStatus) {
        this.bannerStatus = bannerStatus;
    }
}
