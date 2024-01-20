package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Gethomebottomads_Inner implements Serializable {
    @SerializedName("home_property_ads_bottom_id")
    @Expose
    private String homePropertyAdsBottomId;
    @SerializedName("home_property_bottom_for")
    @Expose
    private String homePropertyBottomFor;
    @SerializedName("home_property_bottom_type")
    @Expose
    private String homePropertyBottomType;
    @SerializedName("home_property_bottom_sub_type")
    @Expose
    private String homePropertyBottomSubType;
    @SerializedName("home_property_bottom_image")
    @Expose
    private String homePropertyBottomImage;
    @SerializedName("product_image_thumb")
    @Expose
    private String productImageThumb;
    @SerializedName("status")
    @Expose
    private String status;

    public String getHomePropertyAdsBottomId() {
        return homePropertyAdsBottomId;
    }

    public void setHomePropertyAdsBottomId(String homePropertyAdsBottomId) {
        this.homePropertyAdsBottomId = homePropertyAdsBottomId;
    }

    public String getHomePropertyBottomFor() {
        return homePropertyBottomFor;
    }

    public void setHomePropertyBottomFor(String homePropertyBottomFor) {
        this.homePropertyBottomFor = homePropertyBottomFor;
    }

    public String getHomePropertyBottomType() {
        return homePropertyBottomType;
    }

    public void setHomePropertyBottomType(String homePropertyBottomType) {
        this.homePropertyBottomType = homePropertyBottomType;
    }

    public String getHomePropertyBottomSubType() {
        return homePropertyBottomSubType;
    }

    public void setHomePropertyBottomSubType(String homePropertyBottomSubType) {
        this.homePropertyBottomSubType = homePropertyBottomSubType;
    }

    public String getHomePropertyBottomImage() {
        return homePropertyBottomImage;
    }

    public void setHomePropertyBottomImage(String homePropertyBottomImage) {
        this.homePropertyBottomImage = homePropertyBottomImage;
    }

    public String getProductImageThumb() {
        return productImageThumb;
    }

    public void setProductImageThumb(String productImageThumb) {
        this.productImageThumb = productImageThumb;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
