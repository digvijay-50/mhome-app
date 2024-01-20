package com.propertybuysell.mhome.ModelClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsDetailModel {


    @SerializedName("app_name")
    @Expose
    private String app_name;


    @SerializedName("app_logo")
    @Expose
    private String app_logo;


    @SerializedName("app_version")
    @Expose
    private String app_version;


    @SerializedName("app_author")
    @Expose
    private String app_author;


    @SerializedName("app_contact")
    @Expose
    private String app_contact;


    @SerializedName("app_email")
    @Expose
    private String app_email;


    @SerializedName("app_website")
    @Expose
    private String app_website;

    @SerializedName("map_api_key")
    @Expose
    private String map_api_key;


    @SerializedName("app_description")
    @Expose
    private String app_description;


    @SerializedName("app_maintenance_status")
    @Expose
    private String maintenance;

    @SerializedName("app_about_us")
    @Expose
    private String app_about_us;
    @SerializedName("app_update_description")
    @Expose
    private String app_update_desc;
    @SerializedName("app_update_cancel_button")
    @Expose
    private String app_cancel_status;
    @SerializedName("app_redirect_url")
    @Expose
    private String app_redirect_url;
    @SerializedName("app_developed_by")
    @Expose
    private String app_developed_by;
    @SerializedName("app_privacy_policy")
    @Expose
    private String app_privacy_policy;
    @SerializedName("app_terms_condition")
    @Expose
    private String app_terms_condition;
    @SerializedName("app_cancellation_refund")
    @Expose
    private String app_cancellation_refund;
    @SerializedName("app_contact_us")
    @Expose
    private String app_contact_us;
    @SerializedName("app_maintenance_description")
    @Expose
    private String app_maintenance_description;

    @SerializedName("app_facebook")
    @Expose
    private String app_facebook;
    @SerializedName("app_youtube")
    @Expose
    private String app_youtube;

    @SerializedName("app_twitter")
    @Expose
    private String app_twitter;

    @SerializedName("app_instagram")
    @Expose
    private String app_instagram;


    public String getApp_facebook() {
        return app_facebook;
    }

    public void setApp_facebook(String app_facebook) {
        this.app_facebook = app_facebook;
    }

    public String getApp_youtube() {
        return app_youtube;
    }

    public void setApp_youtube(String app_youtube) {
        this.app_youtube = app_youtube;
    }

    public String getApp_twitter() {
        return app_twitter;
    }

    public void setApp_twitter(String app_twitter) {
        this.app_twitter = app_twitter;
    }

    public String getApp_instagram() {
        return app_instagram;
    }

    public void setApp_instagram(String app_instagram) {
        this.app_instagram = app_instagram;
    }

    public String getApp_maintenance_description() {
        return app_maintenance_description;
    }

    public void setApp_maintenance_description(String app_maintenance_description) {
        this.app_maintenance_description = app_maintenance_description;
    }


    public String getMap_api_key() {
        return map_api_key;
    }

    public void setMap_api_key(String map_api_key) {
        this.map_api_key = map_api_key;
    }

    public String getApp_about_us() {
        return app_about_us;
    }

    public void setApp_about_us(String app_about_us) {
        this.app_about_us = app_about_us;
    }

    public String getApp_contact_us() {
        return app_contact_us;
    }

    public void setApp_contact_us(String app_contact_us) {
        this.app_contact_us = app_contact_us;
    }

    public String getApp_update_desc() {
        return app_update_desc;
    }

    public void setApp_update_desc(String app_update_desc) {
        this.app_update_desc = app_update_desc;
    }

    public String getApp_cancel_status() {
        return app_cancel_status;
    }

    public void setApp_cancel_status(String app_cancel_status) {
        this.app_cancel_status = app_cancel_status;
    }

    public String getApp_redirect_url() {
        return app_redirect_url;
    }

    public void setApp_redirect_url(String app_redirect_url) {
        this.app_redirect_url = app_redirect_url;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_author() {
        return app_author;
    }

    public void setApp_author(String app_author) {
        this.app_author = app_author;
    }

    public String getApp_contact() {
        return app_contact;
    }

    public void setApp_contact(String app_contact) {
        this.app_contact = app_contact;
    }

    public String getApp_email() {
        return app_email;
    }

    public void setApp_email(String app_email) {
        this.app_email = app_email;
    }

    public String getApp_website() {
        return app_website;
    }

    public void setApp_website(String app_website) {
        this.app_website = app_website;
    }

    public String getApp_description() {
        return app_description;
    }

    public void setApp_description(String app_description) {
        this.app_description = app_description;
    }

    public String getApp_developed_by() {
        return app_developed_by;
    }

    public void setApp_developed_by(String app_developed_by) {
        this.app_developed_by = app_developed_by;
    }

    public String getApp_privacy_policy() {
        return app_privacy_policy;
    }

    public void setApp_privacy_policy(String app_privacy_policy) {
        this.app_privacy_policy = app_privacy_policy;
    }

    public String getApp_terms_condition() {
        return app_terms_condition;
    }

    public void setApp_terms_condition(String app_terms_condition) {
        this.app_terms_condition = app_terms_condition;
    }

    public String getApp_cancellation_refund() {
        return app_cancellation_refund;
    }

    public void setApp_cancellation_refund(String app_cancellation_refund) {
        this.app_cancellation_refund = app_cancellation_refund;
    }
}
