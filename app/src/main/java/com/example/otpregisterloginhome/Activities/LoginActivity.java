/*
package com.example.otpregisterloginhome.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.ModelClasses.LoginDetailModel;
import com.example.otpregisterloginhome.ModelClasses.LoginModel;
import com.example.otpregisterloginhome.NetworkUtils.NetworkStateReceiver;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;

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

}*/
