package com.example.otpregisterloginhome.Activities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ProfileUpdateModel implements Serializable {

    private final static long serialVersionUID = -2562972589248905024L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<ProfileUpdateDetailModel> jsonData = null;

    public List<ProfileUpdateDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<ProfileUpdateDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}