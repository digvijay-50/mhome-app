package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class UserProfileModel implements Serializable {

    private final static long serialVersionUID = -6997185181092156011L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<UserProfileDetailModel> jsonData = null;

    public List<UserProfileDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<UserProfileDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}