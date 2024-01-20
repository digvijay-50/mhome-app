package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class RegisterModel implements Serializable {

    private final static long serialVersionUID = -5771498117823038994L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<RegisterDetailModel> jsonData = null;

    public List<RegisterDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<RegisterDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}