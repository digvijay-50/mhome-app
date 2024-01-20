package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNotification implements Serializable
{

@SerializedName("total_count")
@Expose
private String totalCount;
@SerializedName("JSON_DATA")
@Expose
private List<GetNotification_Inner> jsonData = null;
private final static long serialVersionUID = -4438021884900588586L;

public String getTotalCount() {
return totalCount;
}

public void setTotalCount(String totalCount) {
this.totalCount = totalCount;
}

public List<GetNotification_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<GetNotification_Inner> jsonData) {
this.jsonData = jsonData;
}

}