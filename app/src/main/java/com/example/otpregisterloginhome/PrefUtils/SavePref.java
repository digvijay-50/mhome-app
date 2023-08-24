package com.example.otpregisterloginhome.PrefUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.otpregisterloginhome.ModelClasses.SettingsDetailModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class SavePref {
    public final static String PREFS_NAME = "SetMarks";
    private static Context context;

    public SavePref(Context context) {
        SavePref.context = context;
    }



    public static String getnoticount() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString("getnoticount", "0");
    }

    public static void setnoticount(String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("getnoticount", value);
        editor.apply();
    }



    public static Boolean getFirstTime() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean("FirstTime", true);
    }

    public static void setFirstTime(Boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("FirstTime", value);
        editor.apply();
    }


    public static Boolean getLoggedIn() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getBoolean("LoggedIn", false);
    }

    public static void setLoggedIn(Boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("LoggedIn", value);
        editor.apply();
    }


    public static String getUserId() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString("s_id", "0");
    }

    public static void setUserId(String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("s_id", value);
        editor.apply();
    }



    public static void setpage(int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("countt", value);
        editor.apply();
    }
    public static int getpage() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt("countt", 0);
    }



    public static String getUserEmail() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getString("UserEmail", "0");
    }

    public static void setUserEmail(String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("UserEmail", value);
        editor.apply();
    }


    public static SettingsDetailModel getSettingsDetailModel() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        Type type = new TypeToken<SettingsDetailModel>() {
        }.getType();

        SettingsDetailModel companyModel = gson.fromJson(prefs.getString("SettingsDetailModel", "0"), type);


        return companyModel;
    }

    public static void setSettingsDetailModel(SettingsDetailModel wongBakerModel) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String wongBakerModelstr = gson.toJson(wongBakerModel);


        editor.putString("SettingsDetailModel", wongBakerModelstr);
        editor.apply();
    }



    public static String getLocationLattHome() {
        return context.getSharedPreferences(PREFS_NAME, 0).getString("LocationLattHome", "");
    }

    public static void setLocationLattHome(String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        Log.e("Efdscfdc", "value" + value);
        editor.putString("LocationLattHome", value);
        editor.apply();
    }

    public static String getLocationLonggHome() {
        return context.getSharedPreferences(PREFS_NAME, 0).getString("LocationLonggHome", "");
    }

    public static void setLocationLonggHome(String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        Log.e("Efdscfdc", "valuelong" + value);
        editor.putString("LocationLonggHome", value);
        editor.apply();
    }


    public static String getcity(){
        return context.getSharedPreferences(PREFS_NAME,0).getString("getcity","");
    }

    public static void setcity(String value){
        SharedPreferences.Editor editor=context.getSharedPreferences(PREFS_NAME,0).edit();
        editor.putString("getcity",value);
        editor.apply();
    }

    public static String getstate(){
        return context.getSharedPreferences(PREFS_NAME,0).getString("getstate","");
    }

    public void setstate(String state){
        SharedPreferences.Editor editor=context.getSharedPreferences(PREFS_NAME,0).edit();
        editor.putString("getstate",state);
        editor.apply();
    }

    public static String getproperty(){
        return context.getSharedPreferences(PREFS_NAME,0).getString("getpro","");
    }

    public void setproperty(String state){
        SharedPreferences.Editor editor=context.getSharedPreferences(PREFS_NAME,0).edit();
        editor.putString("getpro",state);
        editor.apply();
    }

    public static String getArea(){
        return context.getSharedPreferences(PREFS_NAME,0).getString("getArea","");
    }

    public void setArea(String value){
        SharedPreferences.Editor editor=context.getSharedPreferences(PREFS_NAME,0).edit();
        editor.putString("getArea",value);
        editor.apply();
    }
    public static String getAddress(){
        return context.getSharedPreferences(PREFS_NAME,0).getString("getAddress","");
    }

    public void setAddress(String value){
        SharedPreferences.Editor editor=context.getSharedPreferences(PREFS_NAME,0).edit();
        editor.putString("getAddress",value);
        editor.apply();
    }
}


