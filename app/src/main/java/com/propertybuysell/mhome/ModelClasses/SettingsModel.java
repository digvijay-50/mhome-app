package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SettingsModel {


    @SerializedName("JSON_DATA")
    @Expose
    private List<SettingsDetailModel> registerDetailModels = new ArrayList<SettingsDetailModel>();


    public List<SettingsDetailModel> getRegisterDetailModels() {
        return registerDetailModels;
    }

    public void setRegisterDetailModels(List<SettingsDetailModel> registerDetailModels) {
        this.registerDetailModels = registerDetailModels;
    }
}
