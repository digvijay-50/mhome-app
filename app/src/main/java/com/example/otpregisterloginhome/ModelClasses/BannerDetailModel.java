package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class BannerDetailModel implements Serializable {

    private final static long serialVersionUID = 5807381282604682465L;
    @SerializedName("banner_id")
    @Expose
    private String bId;
    @SerializedName("banner_name")
    @Expose
    private String bName;
    @SerializedName("banner_image")
    @Expose
    private String bImage;
    @SerializedName("banner_status")
    @Expose
    private String bStatus;

    @SerializedName("banner_link")
    @Expose
    private String banner_link;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBanner_link() {
        return banner_link;
    }

    public void setBanner_link(String banner_link) {
        this.banner_link = banner_link;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbImage() {
        return bImage;
    }

    public void setbImage(String bImage) {
        this.bImage = bImage;
    }

    public String getbStatus() {
        return bStatus;
    }

    public void setbStatus(String bStatus) {
        this.bStatus = bStatus;
    }

}