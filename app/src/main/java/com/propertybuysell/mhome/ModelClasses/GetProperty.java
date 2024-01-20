package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class    GetProperty implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<GetProperty_Inner> jsonData = null;
private final static long serialVersionUID = -2928335268776281306L;

public List<GetProperty_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<GetProperty_Inner> jsonData) {
this.jsonData = jsonData;
}

}