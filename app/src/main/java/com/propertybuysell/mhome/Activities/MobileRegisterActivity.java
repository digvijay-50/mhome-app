package com.propertybuysell.mhome.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.propertybuysell.mhome.ModelClasses.UpdateDetailModel;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.NetworkUtils.NetworkStateReceiver;
import com.propertybuysell.mhome.Permission.PermissionHandler;
import com.propertybuysell.mhome.Permission.Permissions;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MobileRegisterActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
        }
    };
    int PERMISSION_ID = 44;
    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    EditText phonenumberet;
    TextView confirmbtn;
    TextView t_signin;
    String m_androidId;
    SavePref savePref;
    LinearLayout loaderlayout;
    RetrofitService retrofitService;
    TextView privacypolicytv, termsofservicetv;

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID && grantResults.length > 0 && grantResults[0] == 0) {
            getLastLocation();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private void getLastLocation() {
        if (!checkPermissions()) {
            requestPermissionsLocation();
        } else if (isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                        return;
                    }

                    if (savePref.getLocationLattHome().equalsIgnoreCase("")) {
                        savePref.setLocationLattHome(location.getLatitude() + "");
                        savePref.setLocationLonggHome(location.getLongitude() + "");
                        //   Toast.makeText(LoginNumberActivity.this, "hii", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show();
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    private void requestPermissionsLocation() {
        Permissions.check(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, getString(R.string.msg_rationale), new Permissions.Options().setRationaleDialogTitle(getString(R.string.txt_info)).setSettingsDialogTitle(getString(R.string.txt_warning)), new PermissionHandler() {
            @Override
            public void onGranted() {
                getLastLocation();
            }
            @Override
            public void onDenied(Context context, ArrayList<String> arrayList) {
                getLastLocation();
            }
        });
    }



    @Override
    public void networkAvailable() {
        nointernet.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        nointernet.setVisibility(View.VISIBLE);
    }

    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        getLastLocation();
        phonenumberet = findViewById(R.id.phonenumberet);


        privacypolicytv = findViewById(R.id.privacypolicytv);
        termsofservicetv = findViewById(R.id.termsofservicetv);

        termsofservicetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MobileRegisterActivity.this, SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_terms_condition());
                intent.putExtra("header", "Terms of Service");
                startActivity(intent);

            }
        });
        privacypolicytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MobileRegisterActivity.this, SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_privacy_policy());
                intent.putExtra("header", "Privacy Policy");
                startActivity(intent);
            }
        });


        confirmbtn = findViewById(R.id.confirmbtn);
        t_signin = findViewById(R.id.t_signin);
        loaderlayout = findViewById(R.id.loaderlayout);
        savePref = new SavePref(this);

        savePref.setpage(0);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        t_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                finish();
            }
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                if (TextUtils.isEmpty(phonenumberet.getText().toString())) {
                    Toast.makeText(MobileRegisterActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

                } else if (Character.isDigit(phonenumberet.getText().toString().trim().charAt(0))
                        && SplashActivity.isValidNumber(phonenumberet.getText().toString().trim())) {
                    Toast.makeText(MobileRegisterActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

                } else {
                    requestOTP();
                }
            }
        });


    }

    private void requestOTP() {
        showLoader();
        retrofitService.userMobileRegisteration(
                phonenumberet.getText().toString(), "1234").enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, retrofit2.Response<UpdateModel> response) {
                final UpdateModel registerModel = response.body();
                UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                Toast.makeText(MobileRegisterActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {


                    Intent intent = new Intent(MobileRegisterActivity.this, OTPActivity.class);
                    intent.putExtra("phoneno", phonenumberet.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    hideLoader();
                }

            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

}