package com.example.otpregisterloginhome.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.otpregisterloginhome.ModelClasses.UpdateDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UpdateModel;
import com.example.otpregisterloginhome.NetworkUtils.NetworkStateReceiver;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    RetrofitService retrofitService;
    SavePref savePref;
    LinearLayout loaderlayout;
    EditText phonenumberet;
    TextView continuebtn;

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
        setContentView(R.layout.activity_forgot_password);


        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        loaderlayout = findViewById(R.id.loaderlayout);
        hideLoader();

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(ForgotPasswordActivity.this);


        phonenumberet = findViewById(R.id.phonenumberet);


        continuebtn = findViewById(R.id.continuebtn);


        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);


                if (TextUtils.isEmpty(phonenumberet.getText().toString())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

                } else if (Character.isDigit(phonenumberet.getText().toString().trim().charAt(0)) && SplashActivity.isValidNumber(phonenumberet.getText().toString().trim())) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();

                } else {
                    callForgotPassword();

                }


            }
        });


    }

    private void callForgotPassword() {


        showLoader();


        retrofitService.changePassOTP(
                (phonenumberet.getText().toString())).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(ForgotPasswordActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {


                        Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPassOTPActivity.class);
                        intent.putExtra("phoneno", phonenumberet.getText().toString());
                        startActivity(intent);
                        finish();

                    } else {
                    }

                    hideLoader();
                } catch (Exception e) {
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