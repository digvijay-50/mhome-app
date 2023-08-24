package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetPostPackage_Inner implements Serializable {
    @SerializedName("property_post_package_id")
    @Expose
    private String propertyPostPackageId;
    @SerializedName("property_post_package_name")
    @Expose
    private String propertyPostPackageName;
    @SerializedName("no_of_propery")
    @Expose
    private String noOfPropery;
    @SerializedName("property_post_package_price")
    @Expose
    private String propertyPostPackagePrice;
    @SerializedName("property_post_package_discount_price")
    @Expose
    private String propertyPostPackageDiscountPrice;
    @SerializedName("package_duration")
    @Expose
    private String packageDuration;
    @SerializedName("property_post_package_description")
    @Expose
    private String propertyPostPackageDescription;
    @SerializedName("property_post_package_status")
    @Expose
    private String propertyPostPackageStatus;

    public String getPropertyPostPackageId() {
        return propertyPostPackageId;
    }

    public void setPropertyPostPackageId(String propertyPostPackageId) {
        this.propertyPostPackageId = propertyPostPackageId;
    }

    public String getPropertyPostPackageName() {
        return propertyPostPackageName;
    }

    public void setPropertyPostPackageName(String propertyPostPackageName) {
        this.propertyPostPackageName = propertyPostPackageName;
    }

    public String getNoOfPropery() {
        return noOfPropery;
    }

    public void setNoOfPropery(String noOfPropery) {
        this.noOfPropery = noOfPropery;
    }

    public String getPropertyPostPackagePrice() {
        return propertyPostPackagePrice;
    }

    public void setPropertyPostPackagePrice(String propertyPostPackagePrice) {
        this.propertyPostPackagePrice = propertyPostPackagePrice;
    }

    public String getPropertyPostPackageDiscountPrice() {
        return propertyPostPackageDiscountPrice;
    }

    public void setPropertyPostPackageDiscountPrice(String propertyPostPackageDiscountPrice) {
        this.propertyPostPackageDiscountPrice = propertyPostPackageDiscountPrice;
    }

    public String getPackageDuration() {
        return packageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        this.packageDuration = packageDuration;
    }

    public String getPropertyPostPackageDescription() {
        return propertyPostPackageDescription;
    }

    public void setPropertyPostPackageDescription(String propertyPostPackageDescription) {
        this.propertyPostPackageDescription = propertyPostPackageDescription;
    }

    public String getPropertyPostPackageStatus() {
        return propertyPostPackageStatus;
    }

    public void setPropertyPostPackageStatus(String propertyPostPackageStatus) {
        this.propertyPostPackageStatus = propertyPostPackageStatus;
    }
}
