package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class FavModel implements Serializable {

    private final static long serialVersionUID = 6512977906108701238L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<FavDetailModel> jsonData = null;

    public List<FavDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<FavDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}