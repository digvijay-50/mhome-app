package com.propertybuysell.mhome.OtherUtils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
//import com.instamojo.android.Instamojo;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;
import com.propertybuysell.mhome.Activities.SplashActivity;

import org.json.JSONException;


public class AppController extends Application {


    public String type;

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private SharedPreferences sharedpreferences;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        Instamojo.getInstance().initialize(this, Instamojo.Environment.PRODUCTION);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this);
        OneSignal.setAppId("57e009d0-0f7b-40cc-9dbd-65ba62153066");
        OneSignal.setNotificationOpenedHandler(
                new OneSignal.OSNotificationOpenedHandler() {
                    @Override
                    public void notificationOpened(OSNotificationOpenedResult result) {
                        try {
                            type= String.valueOf(result.getNotification().getAdditionalData().get("type"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ///  Log.i("notificationOpened", "notificationOpened: ");
                        Intent i=new Intent(mInstance, SplashActivity.class);
                        i.putExtra("type",type);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    }
                });
        MultiDex.install(this);


    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


}