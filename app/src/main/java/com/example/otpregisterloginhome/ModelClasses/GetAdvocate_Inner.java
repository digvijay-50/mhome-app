package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetAdvocate_Inner implements Serializable {

    @SerializedName("advocate_id")
    @Expose
    private String advocateId;
    @SerializedName("advocate_name")
    @Expose
    private String advocateName;
    @SerializedName("advocate_mobile")
    @Expose
    private String advocateMobile;
    @SerializedName("advocate_email")
    @Expose
    private String advocateEmail;
    @SerializedName("advocate_experience")
    @Expose
    private String advocateExperience;
    @SerializedName("advocate_qualification")
    @Expose
    private String advocateQualification;
    @SerializedName("advocate_address")
    @Expose
    private String advocateAddress;
    @SerializedName("advocate_district")
    @Expose
    private String advocateDistrict;
    @SerializedName("advocate_state")
    @Expose
    private String advocateState;
    @SerializedName("advocate_status")
    @Expose
    private String advocateStatus;


    @SerializedName("advocate_profile")
    @Expose
    private String advocateProfile;

    @SerializedName("advocate_tehsil")
    @Expose
    private String advocate_tehsil;

    @SerializedName("advocate_city")
    @Expose
    private String advocate_city;

    public String getAdvocate_tehsil() {
        return advocate_tehsil;
    }

    public void setAdvocate_tehsil(String advocate_tehsil) {
        this.advocate_tehsil = advocate_tehsil;
    }

    public String getAdvocate_city() {
        return advocate_city;
    }

    public void setAdvocate_city(String advocate_city) {
        this.advocate_city = advocate_city;
    }

    public String getAdvocateId() {
        return advocateId;
    }

    public void setAdvocateId(String advocateId) {
        this.advocateId = advocateId;
    }

    public String getAdvocateName() {
        return advocateName;
    }

    public void setAdvocateName(String advocateName) {
        this.advocateName = advocateName;
    }

    public String getAdvocateMobile() {
        return advocateMobile;
    }

    public void setAdvocateMobile(String advocateMobile) {
        this.advocateMobile = advocateMobile;
    }

    public String getAdvocateEmail() {
        return advocateEmail;
    }

    public void setAdvocateEmail(String advocateEmail) {
        this.advocateEmail = advocateEmail;
    }

    public String getAdvocateExperience() {
        return advocateExperience;
    }

    public void setAdvocateExperience(String advocateExperience) {
        this.advocateExperience = advocateExperience;
    }

    public String getAdvocateQualification() {
        return advocateQualification;
    }

    public void setAdvocateQualification(String advocateQualification) {
        this.advocateQualification = advocateQualification;
    }

    public String getAdvocateAddress() {
        return advocateAddress;
    }

    public void setAdvocateAddress(String advocateAddress) {
        this.advocateAddress = advocateAddress;
    }

    public String getAdvocateDistrict() {
        return advocateDistrict;
    }

    public void setAdvocateDistrict(String advocateDistrict) {
        this.advocateDistrict = advocateDistrict;
    }

    public String getAdvocateState() {
        return advocateState;
    }

    public void setAdvocateState(String advocateState) {
        this.advocateState = advocateState;
    }

    public String getAdvocateStatus() {
        return advocateStatus;
    }

    public void setAdvocateStatus(String advocateStatus) {
        this.advocateStatus = advocateStatus;
    }

    public String getAdvocateProfile() {
        return advocateProfile;
    }

    public void setAdvocateProfile(String advocateProfile) {
        this.advocateProfile = advocateProfile;
    }
}
