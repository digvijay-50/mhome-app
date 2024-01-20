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
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.propertybuysell.mhome.ModelClasses.LoginDetailModel;
import com.propertybuysell.mhome.ModelClasses.LoginModel;
import com.propertybuysell.mhome.NetworkUtils.NetworkStateReceiver;
import com.propertybuysell.mhome.Permission.PermissionHandler;
import com.propertybuysell.mhome.Permission.Permissions;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    ImageView showPassword;
    TextView signuptv;
    EditText etemailphone, etpassword;
    TextView loginBtn;
    String m_androidId;
    RetrofitService retrofitService;
    SavePref savePref;
    LinearLayout loaderlayout;
    TextView forgotpasswordtv;
    private int passwordNotVisible = 1;

    FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
        }
    };
    int PERMISSION_ID = 44;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        getLastLocation();

        loaderlayout = findViewById(R.id.loaderlayout);
        hideLoader();

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(LoginActivity.this);

        savePref.setpage(0);
        m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        signuptv = findViewById(R.id.signuptv);
        etemailphone = findViewById(R.id.etemailphone);

        forgotpasswordtv = findViewById(R.id.forgotpasswordtv);
        forgotpasswordtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);

            }
        });
        etpassword = findViewById(R.id.etpassword);
        loginBtn = findViewById(R.id.loginBtn);

        showPassword = findViewById(R.id.show_pass_btn);
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (passwordNotVisible == 1) {

                    etpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etpassword.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    Glide.with(LoginActivity.this)
                            .load(R.drawable.visibility_gone)
                            .into(showPassword);

                    passwordNotVisible = 0;
                } else {
                    Glide.with(LoginActivity.this)
                            .load(R.drawable.visibility)
                            .into(showPassword);

                    etpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etpassword.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    passwordNotVisible = 1;
                }


                etpassword.setSelection(etpassword.length());

            }
        });

        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(LoginActivity.this, MobileRegisterActivity.class);
                startActivity(i);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                if (validation()) {
                    postLoginCall();
                }

            }
        });


    }

    private void postLoginCall() {
        showLoader();

        retrofitService.userLogin(etemailphone.getText().toString(), etpassword.getText().toString()).
                enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        try {
                            LoginDetailModel loginDetailModel = response.body().getJsonData().get(0);
                            Toast.makeText(LoginActivity.this, "" + loginDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                            if (loginDetailModel.getSuccess().equalsIgnoreCase("1")) {
                                SavePref.setLoggedIn(true);
                                SavePref.setUserId(loginDetailModel.getUserId());
                                SavePref.setUserEmail(loginDetailModel.getEmail());
                                SavePref.setUserphone(loginDetailModel.getPhone());
                                Intent intent = new Intent(LoginActivity.this, MainlocationhomeActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            } else {
                                hideLoader();
                            }
                            Toast.makeText(LoginActivity.this, "" + loginDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            hideLoader();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        hideLoader();
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private boolean validation() {

        if (TextUtils.isEmpty(etemailphone.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Email Id or Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Character.isDigit(etemailphone.getText().toString().trim().charAt(0)) && SplashActivity.isValidNumber(etemailphone.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Email Id or Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Character.isDigit(etemailphone.getText().toString().trim().charAt(0)) && SplashActivity.isValidEmail(etemailphone.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Email Id or Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etpassword.getText().toString().trim()) ||
                etpassword.getText().toString().length() < 6) {
            Toast.makeText(this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }

}
