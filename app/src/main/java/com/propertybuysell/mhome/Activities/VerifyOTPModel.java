package com.propertybuysell.mhome.Activities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class VerifyOTPModel implements Serializable {

    private final static long serialVersionUID = 4426710412643853451L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<VerifyOTPDetailModel> jsonData = null;

    public List<VerifyOTPDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<VerifyOTPDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}