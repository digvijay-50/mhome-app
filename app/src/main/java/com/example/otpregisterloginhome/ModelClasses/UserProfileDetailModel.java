package com.example.otpregisterloginhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class UserProfileDetailModel implements Serializable {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("imageurl")
    @Expose
    private String imageurl;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("user_category")
    @Expose
    private String userCategory;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("user_lat")
    @Expose
    private String userLat;
    @SerializedName("user_long")
    @Expose
    private String userLong;
    @SerializedName("user_postal_code")
    @Expose
    private String userPostalCode;
    @SerializedName("user_state")
    @Expose
    private String userState;
    @SerializedName("user_city")
    @Expose
    private String userCity;
    @SerializedName("area_of_location")
    @Expose
    private String areaOfLocation;
    @SerializedName("flat_no")
    @Expose
    private String flatNo;
    @SerializedName("aadhar_number")
    @Expose
    private String aadharNumber;

    @SerializedName("property_post_package_id")
    @Expose
    private String property_post_package_id;
    @SerializedName("property_post_package_name")
    @Expose
    private String property_post_package_name;
    @SerializedName("no_of_propery")
    @Expose
    private String no_of_propery;
    @SerializedName("property_post_package_price")
    @Expose
    private String property_post_package_price;
    @SerializedName("property_post_package_discount_price")
    @Expose
    private String property_post_package_discount_price;
    @SerializedName("package_duration")
    @Expose
    private String package_duration;
    @SerializedName("txtsdate")
    @Expose
    private String txtsdate;
    @SerializedName("txtedate")
    @Expose
    private String txtedate;
    @SerializedName("active_post_package")
    @Expose
    private String active_post_package;

    @SerializedName("remaining_post")
    @Expose
    private String remaining_post;
    @SerializedName("total_post_property")
    @Expose
    private String total_post_property;

    public String getTotal_post_property() {
        return total_post_property;
    }

    public void setTotal_post_property(String total_post_property) {
        this.total_post_property = total_post_property;
    }

    public String getRemaining_post() {
        return remaining_post;
    }

    public void setRemaining_post(String remaining_post) {
        this.remaining_post = remaining_post;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserLat() {
        return userLat;
    }

    public void setUserLat(String userLat) {
        this.userLat = userLat;
    }

    public String getUserLong() {
        return userLong;
    }

    public void setUserLong(String userLong) {
        this.userLong = userLong;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }

    public void setUserPostalCode(String userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getAreaOfLocation() {
        return areaOfLocation;
    }

    public void setAreaOfLocation(String areaOfLocation) {
        this.areaOfLocation = areaOfLocation;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProperty_post_package_id() {
        return property_post_package_id;
    }

    public void setProperty_post_package_id(String property_post_package_id) {
        this.property_post_package_id = property_post_package_id;
    }

    public String getProperty_post_package_name() {
        return property_post_package_name;
    }

    public void setProperty_post_package_name(String property_post_package_name) {
        this.property_post_package_name = property_post_package_name;
    }

    public String getNo_of_propery() {
        return no_of_propery;
    }

    public void setNo_of_propery(String no_of_propery) {
        this.no_of_propery = no_of_propery;
    }

    public String getProperty_post_package_price() {
        return property_post_package_price;
    }

    public void setProperty_post_package_price(String property_post_package_price) {
        this.property_post_package_price = property_post_package_price;
    }

    public String getProperty_post_package_discount_price() {
        return property_post_package_discount_price;
    }

    public void setProperty_post_package_discount_price(String property_post_package_discount_price) {
        this.property_post_package_discount_price = property_post_package_discount_price;
    }

    public String getPackage_duration() {
        return package_duration;
    }

    public void setPackage_duration(String package_duration) {
        this.package_duration = package_duration;
    }

    public String getTxtsdate() {
        return txtsdate;
    }

    public void setTxtsdate(String txtsdate) {
        this.txtsdate = txtsdate;
    }

    public String getTxtedate() {
        return txtedate;
    }

    public void setTxtedate(String txtedate) {
        this.txtedate = txtedate;
    }

    public String getActive_post_package() {
        return active_post_package;
    }

    public void setActive_post_package(String active_post_package) {
        this.active_post_package = active_post_package;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String gpasswordet() {
        return password;
    }

    public void spasswordet(String password) {
        this.password = password;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}