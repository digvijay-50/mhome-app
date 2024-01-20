package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ImagestorageModel_Inner implements Serializable {

    private final static long serialVersionUID = -9133767424537001321L;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("image_storage_id")
    @Expose
    private String image_storage_id;

    @SerializedName("image_path")
    @Expose
    private String image_path;

    public String getImage_storage_id() {
        return image_storage_id;
    }

    public void setImage_storage_id(String image_storage_id) {
        this.image_storage_id = image_storage_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}