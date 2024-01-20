package com.propertybuysell.mhome.Activities;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.propertybuysell.mhome.ModelClasses.UpdateDetailModel;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.NetworkUtils.NetworkStateReceiver;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassOTPActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    LinearLayout nointernet;
    TextView resendotptv;
    NetworkStateReceiver networkStateReceiver;
    RetrofitService retrofitService;
    SavePref savePref;
    LinearLayout l_rec;
    LinearLayout loaderlayout;
    TextView confirmotpbtn;
    TextView phonetv;
    String phoneno;
    EditText et1, et2, et3, et4;


    TextView timertv;
    CountDownTimer mCountDownTimer;
    int i = 0;


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
        setContentView(R.layout.activity_otp_forgot);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        l_rec = findViewById(R.id.l_rec);
        savePref = new SavePref(this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);
        m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
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

        resendotptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                callForgotPassword();
            }
        });

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
                Toast.makeText(ForgotPassOTPActivity.this, "" + loginDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                if (loginDetailModel.getSuccess().equalsIgnoreCase("1")) {

                    startActivity(new Intent(ForgotPassOTPActivity.this, NewPasswordActivity.class)
                            .putExtra("VerifyOTPDetailModel", loginDetailModel)
                            .putExtra("phoneno", phoneno));

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
            // TODO Auto-generated method stub
            String text = editable.toString();

            if (view.getId() == R.id.et1) {
                if (text.length() == 1)
                    et2.requestFocus();
            }else if (view.getId() == R.id.et2) {
                if (text.length() == 1)
                    et3.requestFocus();
                else if (text.length() == 0)
                    et1.requestFocus();
            }else if (view.getId() == R.id.et3) {
                if (text.length() == 1)
                    et4.requestFocus();
                else if (text.length() == 0)
                    et2.requestFocus();
            }else if (view.getId() == R.id.et4) {
                if (text.length() == 0)
                    et3.requestFocus();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    private void callForgotPassword() {


        showLoader();


        retrofitService.changePassOTP(
                phoneno).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(ForgotPassOTPActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    /// if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {


                    mCountDownTimer.cancel();
                    startcounter();
//                    } else {
//                    }

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