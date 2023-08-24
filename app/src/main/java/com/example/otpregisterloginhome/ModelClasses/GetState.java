package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetState implements Serializable
{

@SerializedName("JSON_DATA")
@Expose
private List<GetState_Inner> jsonData = null;
private final static long serialVersionUID = 4474771970430301668L;

public List<GetState_Inner> getJsonData() {
return jsonData;
}

public void setJsonData(List<GetState_Inner> jsonData) {
this.jsonData = jsonData;
}

}