package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ImagestorageModel implements Serializable {

    private final static long serialVersionUID = 6512977906108701238L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<ImagestorageModel_Inner> jsonData = null;

    public List<ImagestorageModel_Inner> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<ImagestorageModel_Inner> jsonData) {
        this.jsonData = jsonData;
    }

}