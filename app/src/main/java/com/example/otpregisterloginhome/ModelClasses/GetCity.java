package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetCity implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<GetCity_Inner> jsonData = null;
private final static long serialVersionUID = 4336234219739873038L;

public List<GetCity_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<GetCity_Inner> jsonData) {
this.jsonData = jsonData;
}

}