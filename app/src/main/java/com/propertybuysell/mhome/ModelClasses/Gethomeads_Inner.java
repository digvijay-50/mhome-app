package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Gethomeads_Inner implements Serializable {
    @SerializedName("home_property_ads_top_id")
    @Expose
    private String homePropertyAdsTopId;
    @SerializedName("home_property_top_for")
    @Expose
    private String homePropertyTopFor;
    @SerializedName("home_property_top_type")
    @Expose
    private String homePropertyTopType;
    @SerializedName("home_property_top_sub_type")
    @Expose
    private String homePropertyTopSubType;
    @SerializedName("home_property_top_image")
    @Expose
    private String homePropertyTopImage;
    @SerializedName("product_image_thumb")
    @Expose
    private String productImageThumb;
    @SerializedName("status")
    @Expose
    private String status;

    public String getHomePropertyAdsTopId() {
        return homePropertyAdsTopId;
    }

    public void setHomePropertyAdsTopId(String homePropertyAdsTopId) {
        this.homePropertyAdsTopId = homePropertyAdsTopId;
    }

    public String getHomePropertyTopFor() {
        return homePropertyTopFor;
    }

    public void setHomePropertyTopFor(String homePropertyTopFor) {
        this.homePropertyTopFor = homePropertyTopFor;
    }

    public String getHomePropertyTopType() {
        return homePropertyTopType;
    }

    public void setHomePropertyTopType(String homePropertyTopType) {
        this.homePropertyTopType = homePropertyTopType;
    }

    public String getHomePropertyTopSubType() {
        return homePropertyTopSubType;
    }

    public void setHomePropertyTopSubType(String homePropertyTopSubType) {
        this.homePropertyTopSubType = homePropertyTopSubType;
    }

    public String getHomePropertyTopImage() {
        return homePropertyTopImage;
    }

    public void setHomePropertyTopImage(String homePropertyTopImage) {
        this.homePropertyTopImage = homePropertyTopImage;
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
