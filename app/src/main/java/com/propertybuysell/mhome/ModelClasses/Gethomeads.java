package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gethomeads implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<Gethomeads_Inner> jsonData;
private final static long serialVersionUID = 2732527589034650556L;

public List<Gethomeads_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<Gethomeads_Inner> jsonData) {
this.jsonData = jsonData;
}

}