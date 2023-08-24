package com.example.otpregisterloginhome.Activities;


import android.content.IntentFilter;
import android.os.Bundle;
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

public class ChangePasswordActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    TextView updateBtn;
    EditText oldpasset, newpasset, confirmnewpasset;
    SavePref savePref;
    LinearLayout loaderlayout;
    RetrofitService retrofitService;


    ImageView show_pass_btn1;
    int passwordNotVisible1 = 1;


    ImageView show_pass_btn2;
    int passwordNotVisible2 = 1;


    ImageView show_pass_btn3;
    int passwordNotVisible3 = 1;

    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;

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
        setContentView(R.layout.activity_change_password);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Change Password");


        savePref = new SavePref(this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);

        oldpasset = findViewById(R.id.oldpasset);

        newpasset = findViewById(R.id.newpasset);
        confirmnewpasset = findViewById(R.id.confirmnewpasset);


        updateBtn = findViewById(R.id.updateBtn);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);


                if (validation()) {

                    profileupdatewithoutimage();


                }
            }
        });


        show_pass_btn1 = findViewById(R.id.show_pass_btn1);
        show_pass_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (passwordNotVisible1 == 1) {

                    oldpasset.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    oldpasset.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    Glide.with(ChangePasswordActivity.this)
                            .load(R.drawable.visibility_gone)
                            .into(show_pass_btn1);

                    passwordNotVisible1 = 0;
                } else {
                    Glide.with(ChangePasswordActivity.this)
                            .load(R.drawable.visibility)
                            .into(show_pass_btn1);

                    oldpasset.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    oldpasset.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    passwordNotVisible1 = 1;
                }


                oldpasset.setSelection(oldpasset.length());

            }
        });


        show_pass_btn2 = findViewById(R.id.show_pass_btn2);
        show_pass_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (passwordNotVisible2 == 1) {

                    newpasset.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    newpasset.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    Glide.with(ChangePasswordActivity.this)
                            .load(R.drawable.visibility_gone)
                            .into(show_pass_btn2);

                    passwordNotVisible2 = 0;
                } else {
                    Glide.with(ChangePasswordActivity.this)
                            .load(R.drawable.visibility)
                            .into(show_pass_btn2);

                    newpasset.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    newpasset.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    passwordNotVisible2 = 1;
                }


                newpasset.setSelection(newpasset.length());

            }
        });


        show_pass_btn3 = findViewById(R.id.show_pass_btn3);
        show_pass_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (passwordNotVisible3 == 1) {

                    confirmnewpasset.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmnewpasset.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    Glide.with(ChangePasswordActivity.this)
                            .load(R.drawable.visibility_gone)
                            .into(show_pass_btn3);

                    passwordNotVisible3 = 0;
                } else {
                    Glide.with(ChangePasswordActivity.this)
                            .load(R.drawable.visibility)
                            .into(show_pass_btn3);

                    confirmnewpasset.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmnewpasset.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    passwordNotVisible3 = 1;
                }


                confirmnewpasset.setSelection(confirmnewpasset.length());

            }
        });


    }


    private boolean validation() {

        if (TextUtils.isEmpty(oldpasset.getText().toString().trim()) ||
                oldpasset.getText().toString().length() < 6) {
            Toast.makeText(this, "Please Enter Valid Old Password", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(newpasset.getText().toString().trim()) ||
                newpasset.getText().toString().length() < 6) {
            Toast.makeText(this, "Please Enter Valid New Password", Toast.LENGTH_SHORT).show();

            return false;

        } else if (TextUtils.isEmpty(confirmnewpasset.getText().toString().trim()) ||
                confirmnewpasset.getText().toString().length() < 6) {
            Toast.makeText(this, "Please Enter Valid Confirm Password", Toast.LENGTH_SHORT).show();

            return false;

        } else if (!newpasset.getText().toString().trim().equals(confirmnewpasset.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Confirm Password", Toast.LENGTH_SHORT).show();

            return false;

        } else {
            return true;
        }


    }


    private void profileupdatewithoutimage() {

        showLoader();


        retrofitService.changePassword(
                (newpasset.getText().toString()),
                oldpasset.getText().toString(),
                SavePref.getUserId()

        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(ChangePasswordActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {


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