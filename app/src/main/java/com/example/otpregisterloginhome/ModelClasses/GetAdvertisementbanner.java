package com.example.otpregisterloginhome.ModelClasses;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdvertisementbanner implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<GetAdverti_Inner> jsonData = null;
private final static long serialVersionUID = 4079074467587830251L;

public List<GetAdverti_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<GetAdverti_Inner> jsonData) {
this.jsonData = jsonData;
}

}