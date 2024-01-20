package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class BannerModel implements Serializable {

    private final static long serialVersionUID = 6439954993855669117L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<BannerDetailModel> jsonData = null;

    public List<BannerDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<BannerDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}