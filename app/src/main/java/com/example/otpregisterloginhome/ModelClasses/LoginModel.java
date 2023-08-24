package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class LoginModel implements Serializable {

    private final static long serialVersionUID = 7380972110738115413L;
    @SerializedName("JSON_DATA")
    @Expose
    private List<LoginDetailModel> jsonData = null;

    public List<LoginDetailModel> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<LoginDetailModel> jsonData) {
        this.jsonData = jsonData;
    }

}