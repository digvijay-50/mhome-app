package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetProperty_Inner implements Serializable {

    @SerializedName("fav_id")
    @Expose
    private String favId;
    @SerializedName("property_id")
    @Expose
    private String propertyId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("user_category")
    @Expose
    private String userCategory;
    @SerializedName("address")
    @Expose
    private String address;
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
    @SerializedName("property_for")
    @Expose
    private String propertyFor;
    @SerializedName("property_type")
    @Expose
    private String propertyType;
    @SerializedName("property_sub_type")
    @Expose
    private String propertySubType;
    @SerializedName("property_state")
    @Expose
    private String propertyState;
    @SerializedName("property_city")
    @Expose
    private String propertyCity;

    @SerializedName("property_pdf")
    @Expose
    private String property_pdf;
    @SerializedName("property_area")
    @Expose
    private String propertyArea;
    @SerializedName("property_pincode")
    @Expose
    private String propertyPincode;
    @SerializedName("property_tehsil")
    @Expose
    private String propertyTehsil;
    @SerializedName("property_khasra_no")
    @Expose
    private String propertyKhasraNo;
    @SerializedName("property_district")
    @Expose
    private String propertyDistrict;
    @SerializedName("property_category")
    @Expose
    private String propertyCategory;
    @SerializedName("property_configuration")
    @Expose
    private String propertyConfiguration;
    @SerializedName("furnishing_detail")
    @Expose
    private String furnishingDetail;
    @SerializedName("floor_detail")
    @Expose
    private String floorDetail;
    @SerializedName("available_for")
    @Expose
    private String availableFor;

    @SerializedName("property_facing")
    @Expose
    private String property_facing;

    @SerializedName("area_detail")
    @Expose
    private String areaDetail;
    @SerializedName("area_unit_detail")
    @Expose
    private String areaUnitDetail;
    @SerializedName("price_detail")
    @Expose
    private String priceDetail;
    @SerializedName("price_unit_detail")
    @Expose
    private String priceUnitDetail;
    @SerializedName("property_title")
    @Expose
    private String propertyTitle;
    @SerializedName("property_description")
    @Expose
    private String propertyDescription;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_image_thumb")
    @Expose
    private String productImageThumb;
    @SerializedName("property_gallery")
    @Expose
    private List<String> propertyGallery = null;

    @SerializedName("property_contact_detail")
    @Expose
    private List<PropertyContactDetail> property_contact_detail = null;
    @SerializedName("property_status")
    @Expose
    private Integer propertyStatus;
    @SerializedName("fav_status")
    @Expose
    private String favStatus;

    @SerializedName("fav_user")
    @Expose
    private String fav_user;
    @SerializedName("property_view")
    @Expose
    private String property_view;
    @SerializedName("property_final_price")
    @Expose
    private String property_final_price;

    @SerializedName("property_sold")
    @Expose
    private String property_sold;


    public String getProperty_pdf() {
        return property_pdf;
    }

    public void setProperty_pdf(String property_pdf) {
        this.property_pdf = property_pdf;
    }

    public String getProperty_sold() {
        return property_sold;
    }

    public void setProperty_sold(String property_sold) {
        this.property_sold = property_sold;
    }

    public List<PropertyContactDetail> getProperty_contact_detail() {
        return property_contact_detail;
    }

    public void setProperty_contact_detail(List<PropertyContactDetail> property_contact_detail) {
        this.property_contact_detail = property_contact_detail;
    }

    public String getProperty_view() {
        return property_view;
    }

    public void setProperty_view(String property_view) {
        this.property_view = property_view;
    }

    public String getProperty_facing() {
        return property_facing;
    }

    public void setProperty_facing(String property_facing) {
        this.property_facing = property_facing;
    }

    public String getProperty_final_price() {
        return property_final_price;
    }

    public void setProperty_final_price(String property_final_price) {
        this.property_final_price = property_final_price;
    }

    public String getFav_user() {
        return fav_user;
    }

    public void setFav_user(String fav_user) {
        this.fav_user = fav_user;
    }

    public String getFavId() {
        return favId;
    }

    public void setFavId(String favId) {
        this.favId = favId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPropertyFor() {
        return propertyFor;
    }

    public void setPropertyFor(String propertyFor) {
        this.propertyFor = propertyFor;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertySubType() {
        return propertySubType;
    }

    public void setPropertySubType(String propertySubType) {
        this.propertySubType = propertySubType;
    }

    public String getPropertyState() {
        return propertyState;
    }

    public void setPropertyState(String propertyState) {
        this.propertyState = propertyState;
    }

    public String getPropertyCity() {
        return propertyCity;
    }

    public void setPropertyCity(String propertyCity) {
        this.propertyCity = propertyCity;
    }

    public String getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(String propertyArea) {
        this.propertyArea = propertyArea;
    }

    public String getPropertyPincode() {
        return propertyPincode;
    }

    public void setPropertyPincode(String propertyPincode) {
        this.propertyPincode = propertyPincode;
    }

    public String getPropertyTehsil() {
        return propertyTehsil;
    }

    public void setPropertyTehsil(String propertyTehsil) {
        this.propertyTehsil = propertyTehsil;
    }

    public String getPropertyKhasraNo() {
        return propertyKhasraNo;
    }

    public void setPropertyKhasraNo(String propertyKhasraNo) {
        this.propertyKhasraNo = propertyKhasraNo;
    }

    public String getPropertyDistrict() {
        return propertyDistrict;
    }

    public void setPropertyDistrict(String propertyDistrict) {
        this.propertyDistrict = propertyDistrict;
    }

    public String getPropertyCategory() {
        return propertyCategory;
    }

    public void setPropertyCategory(String propertyCategory) {
        this.propertyCategory = propertyCategory;
    }

    public String getPropertyConfiguration() {
        return propertyConfiguration;
    }

    public void setPropertyConfiguration(String propertyConfiguration) {
        this.propertyConfiguration = propertyConfiguration;
    }

    public String getFurnishingDetail() {
        return furnishingDetail;
    }

    public void setFurnishingDetail(String furnishingDetail) {
        this.furnishingDetail = furnishingDetail;
    }

    public String getFloorDetail() {
        return floorDetail;
    }

    public void setFloorDetail(String floorDetail) {
        this.floorDetail = floorDetail;
    }

    public String getAvailableFor() {
        return availableFor;
    }

    public void setAvailableFor(String availableFor) {
        this.availableFor = availableFor;
    }

    public String getAreaDetail() {
        return areaDetail;
    }

    public void setAreaDetail(String areaDetail) {
        this.areaDetail = areaDetail;
    }

    public String getAreaUnitDetail() {
        return areaUnitDetail;
    }

    public void setAreaUnitDetail(String areaUnitDetail) {
        this.areaUnitDetail = areaUnitDetail;
    }

    public String getPriceDetail() {
        return priceDetail;
    }

    public void setPriceDetail(String priceDetail) {
        this.priceDetail = priceDetail;
    }

    public String getPriceUnitDetail() {
        return priceUnitDetail;
    }

    public void setPriceUnitDetail(String priceUnitDetail) {
        this.priceUnitDetail = priceUnitDetail;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductImageThumb() {
        return productImageThumb;
    }

    public void setProductImageThumb(String productImageThumb) {
        this.productImageThumb = productImageThumb;
    }

    public List<String> getPropertyGallery() {
        return propertyGallery;
    }

    public void setPropertyGallery(List<String> propertyGallery) {
        this.propertyGallery = propertyGallery;
    }

    public Integer getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(Integer propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }
}
