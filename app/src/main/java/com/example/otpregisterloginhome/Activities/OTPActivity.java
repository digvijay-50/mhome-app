package com.example.otpregisterloginhome.Activities;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class OTPActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    RetrofitService retrofitService;
    SavePref savePref;
    LinearLayout loaderlayout;
    TextView confirmotpbtn;
    TextView phonetv;
    String phoneno;
    EditText et1, et2, et3, et4;
    TextView resendotptv;
    TextView timertv;
    CountDownTimer mCountDownTimer;
    int i = 0;
    LinearLayout l_rec;
    String m_androidId;

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
        setContentView(R.layout.activity_otp);
        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        savePref = new SavePref(this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);
        m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        l_rec = findViewById(R.id.l_rec);

        phoneno = getIntent().getStringExtra("phoneno");
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        resendotptv = findViewById(R.id.resendotptv);
        timertv = findViewById(R.id.timertv);
        confirmotpbtn = findViewById(R.id.confirmotpbtn);
        phonetv = findViewById(R.id.phonetv);
        phonetv.setText("+91 " + phoneno);
        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));

        resendotptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                requestOTP();
            }
        });

        confirmotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (validation()) {
                    callVerifyOTPAPI();
                }
            }
        });
        startcounter();


    }

    public void startcounter() {
        i = 0;
        mCountDownTimer = new CountDownTimer(25000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                timertv.setText(25 - i + "");
                l_rec.setVisibility(View.GONE);
            }

            @Override
            public void onFinish() {
                timertv.setText("");
                l_rec.setVisibility(View.VISIBLE);
            }
        };
        mCountDownTimer.start();
    }

    private boolean validation() {
        if (TextUtils.isEmpty(et1.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et2.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et3.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(et4.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private void callVerifyOTPAPI() {
        showLoader();
        String s = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();
        retrofitService.postConfirmOTP(phoneno, s).enqueue(new Callback<VerifyOTPModel>() {
            @Override
            public void onResponse(Call<VerifyOTPModel> call, retrofit2.Response<VerifyOTPModel> response) {
                VerifyOTPDetailModel loginDetailModel = response.body().getJsonData().get(0);
                Toast.makeText(OTPActivity.this, "" + loginDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                if (loginDetailModel.getSuccess().equalsIgnoreCase("1")) {

                    if (loginDetailModel.getUser_name().equals("")) {
                        SavePref.setLoggedIn(true);
                        SavePref.setUserId(loginDetailModel.getUserId());
                        startActivity(new Intent(OTPActivity.this, UserDetailsActivity.class)
                                .putExtra("VerifyOTPDetailModel", loginDetailModel)
                                .putExtra("phoneno", phoneno)
                        );


                    } else {
                        SavePref.setLoggedIn(true);
                        SavePref.setUserId(loginDetailModel.getUserId());
                        startActivity(new Intent(OTPActivity.this,MainlocationhomeActivity.class));
                    }
                    finish();
                } else {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<VerifyOTPModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });

    }

    private void requestOTP() {
        showLoader();
        retrofitService.userMobileRegisteration(
                phoneno, "1234").enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, retrofit2.Response<UpdateModel> response) {
                final UpdateModel registerModel = response.body();
                UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                Toast.makeText(OTPActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                mCountDownTimer.cancel();


                startcounter();

                hideLoader();
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onBackPressed() {


    }

    public class GenericTextWatcher implements TextWatcher {
        private final View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {

            String text = editable.toString();
            switch (view.getId()) {

                case R.id.et1:
                    if (text.length() == 1)
                        et2.requestFocus();

                    break;
                case R.id.et2:
                    if (text.length() == 1)
                        et3.requestFocus();
                    else if (text.length() == 0)
                        et1.requestFocus();

                    break;
                case R.id.et3:
                    if (text.length() == 1)
                        et4.requestFocus();
                    else if (text.length() == 0)
                        et2.requestFocus();

                    break;
                case R.id.et4:
                    if (text.length() == 0)
                        et3.requestFocus();

                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }
    }

}