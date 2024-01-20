package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gethomebottomads implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<Gethomebottomads_Inner> jsonData;
private final static long serialVersionUID = -5046207609677172575L;

public List<Gethomebottomads_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<Gethomebottomads_Inner> jsonData) {
this.jsonData = jsonData;
}

}