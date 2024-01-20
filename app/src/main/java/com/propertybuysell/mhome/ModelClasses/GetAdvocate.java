package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdvocate implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<GetAdvocate_Inner> jsonData = null;
private final static long serialVersionUID = -3646036005069084657L;

public List<GetAdvocate_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<GetAdvocate_Inner> jsonData) {
this.jsonData = jsonData;
}

}