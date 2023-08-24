package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class UpdateModel implements Serializable {

    private final static long serialVersionUID = 6512977906108701238L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<UpdateDetailModel> jsonData = null;

    public List<UpdateDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<UpdateDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}