package com.propertybuysell.mhome.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.propertybuysell.mhome.ModelClasses.SettingsDetailModel;
import com.propertybuysell.mhome.ModelClasses.SettingsModel;
import com.propertybuysell.mhome.NetworkUtils.NetworkStateReceiver;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;

public class SplashActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    public static SettingsDetailModel settingsDetailModel = new SettingsDetailModel();
    public static Typeface nunitosans_semiboldTypeface;
    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    SavePref savePref;
    RetrofitService retrofitService;

    public static void disablefor1sec(final View v) {
        try {
            v.setEnabled(false);
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        v.setEnabled(true);
                    } catch (Exception e) {

                    }
                }
            }, 700);
        } catch (Exception e) {

        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return !(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidNumber(CharSequence target) {
        return (!TextUtils.isEmpty(target) && target.toString().length() != 10);
    }


    @Override
    public void networkAvailable() {
        nointernet.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        nointernet.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(SplashActivity.this);
        savePref.setpage(0);
//        Log.e("EFdcwefdcedc", SavePref.getUserId());


//        startActivity(new Intent(SplashActivity.this, NewHomeActivity.class));
//        finish();


      getSettings();
        nunitosans_semiboldTypeface = Typeface.createFromAsset(SplashActivity.this.getAssets(),
                "nunitosans_semibold.ttf");

    }

    private void getSettings() {


        retrofitService.getSettings().enqueue(new Callback<SettingsModel>() {
            @Override
            public void onResponse(Call<SettingsModel> call, final retrofit2.Response<SettingsModel> response) {

                try {
                    final SettingsModel registerModel = response.body();

                    settingsDetailModel = registerModel.getRegisterDetailModels().get(0);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            String versionName = "1";
                            try {
                                versionName = getApplicationContext().getPackageManager()
                                        .getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }


                            Double appversiondouble = Double.parseDouble(settingsDetailModel.getApp_version());
                            Double versionnamedouble = Double.parseDouble(versionName);


                            SavePref.setSettingsDetailModel(settingsDetailModel);

                            Boolean aBoolean = settingsDetailModel.getApp_version().equals(versionName) ||
                                    appversiondouble < versionnamedouble;

                            if (settingsDetailModel.getMaintenance().equals("1")) {
                                Intent intent = new Intent(SplashActivity.this, MaintainanceActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                if (aBoolean) {

                                    if (SavePref.getFirstTime()) {
                                        startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                                        finish();
                                    } else {

                                        if (SavePref.getLoggedIn()) {
                                            startActivity(new Intent(SplashActivity.this, NewHomeActivity.class));
                                            finish();
                                        } else {
                                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                            finish();
                                        }

                                    }

                                } else {
                                    Intent intent = new Intent(SplashActivity.this, ForceUpdateActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }


                        }
                    }, 2000);


                } catch (Exception e) {

                }


            }

            @Override
            public void onFailure(Call<SettingsModel> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }


}