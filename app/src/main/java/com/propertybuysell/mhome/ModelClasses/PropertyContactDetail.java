package com.propertybuysell.mhome.ModelClasses;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyContactDetail implements Serializable {

    @SerializedName("propertycontacts_id")
    @Expose
    private String propertycontactsId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("propety_contact_date")
    @Expose
    private String propety_contact_date;
    @SerializedName("imageurl")
    @Expose
    private String imageurl;

    public String getPropety_contact_date() {
        return propety_contact_date;
    }

    public void setPropety_contact_date(String propety_contact_date) {
        this.propety_contact_date = propety_contact_date;
    }

    private final static long serialVersionUID = -8598129115770954612L;

    public String getPropertycontactsId() {
        return propertycontactsId;
    }

    public void setPropertycontactsId(String propertycontactsId) {
        this.propertycontactsId = propertycontactsId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
