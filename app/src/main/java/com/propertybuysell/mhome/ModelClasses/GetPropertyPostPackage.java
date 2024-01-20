package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPropertyPostPackage implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<GetPostPackage_Inner> jsonData = null;
private final static long serialVersionUID = -6568768952056874032L;

public List<GetPostPackage_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<GetPostPackage_Inner> jsonData) {
this.jsonData = jsonData;
}

}