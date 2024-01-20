package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHomePropertyCityList implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<GetCityList_Inner> jsonData;
private final static long serialVersionUID = 1581567931460400869L;

    public List<GetCityList_Inner> getJsonData() {
        return jsonData;
    }

    public void setJsonData(List<GetCityList_Inner> jsonData) {
        this.jsonData = jsonData;
    }
}