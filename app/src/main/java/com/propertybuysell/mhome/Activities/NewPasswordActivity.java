package com.propertybuysell.mhome.Activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

public class NewPasswordActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    TextView changepasswordbtn;
    TextView phonenumberet;

    EditText etnewpassword;
    ImageView showPassword;
    EditText etconfirmnewpassword;
    ImageView showPassword2;
    String phoneno;
    VerifyOTPDetailModel verifyConfirmOTPDetailModel;
    SavePref savePref;
    LinearLayout loaderlayout;
    RetrofitService retrofitService;
    private int passwordNotVisible = 1;
    private int passwordNotVisible2 = 1;

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
        setContentView(R.layout.activity_new_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        verifyConfirmOTPDetailModel = (VerifyOTPDetailModel) getIntent().getSerializableExtra("VerifyOTPDetailModel");


        phoneno = getIntent().getStringExtra("phoneno");
        phonenumberet = findViewById(R.id.phonenumberet);

        savePref = new SavePref(this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);

        etconfirmnewpassword = findViewById(R.id.etconfirmnewpassword);
        showPassword2 = findViewById(R.id.show_pass_btn2);


        etnewpassword = findViewById(R.id.etnewpassword);
        showPassword = findViewById(R.id.show_pass_btn);

        phonenumberet = findViewById(R.id.phonenumberet);


        changepasswordbtn = findViewById(R.id.changepasswordbtn);


        phonenumberet.setText(phoneno);

        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (passwordNotVisible == 1) {
                    etnewpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etnewpassword.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    Glide.with(NewPasswordActivity.this)
                            .load(R.drawable.visibility_gone)
                            .into(showPassword);

                    passwordNotVisible = 0;
                } else {
                    Glide.with(NewPasswordActivity.this)
                            .load(R.drawable.visibility)
                            .into(showPassword);

                    etnewpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etnewpassword.setTypeface(SplashActivity.nunitosans_semiboldTypeface);
                    passwordNotVisible = 1;
                }


                etnewpassword.setSelection(etnewpassword.length());

            }
        });


        showPassword2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (passwordNotVisible2 == 1) {
                    etconfirmnewpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etconfirmnewpassword.setTypeface(SplashActivity.nunitosans_semiboldTypeface);

                    Glide.with(NewPasswordActivity.this)
                            .load(R.drawable.visibility_gone)
                            .into(showPassword2);

                    passwordNotVisible2 = 0;
                } else {
                    Glide.with(NewPasswordActivity.this)
                            .load(R.drawable.visibility)
                            .into(showPassword2);

                    etconfirmnewpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etnewpassword.setTypeface(SplashActivity.nunitosans_semiboldTypeface);
                    passwordNotVisible2 = 1;
                }


                etconfirmnewpassword.setSelection(etconfirmnewpassword.length());

            }
        });


        changepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                if (validation()) {

                    profileupdatewithoutimage();

                }
            }
        });

    }


    private boolean validation() {

        if (TextUtils.isEmpty(etnewpassword.getText().toString().trim()) ||
                etnewpassword.getText().toString().length() < 6) {
            Toast.makeText(this, "Please Enter Valid New Password", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(etconfirmnewpassword.getText().toString().trim()) ||
                etconfirmnewpassword.getText().toString().length() < 6) {
            Toast.makeText(this, "Please Enter Valid Confirm password", Toast.LENGTH_SHORT).show();

            return false;

        } else if (!(etnewpassword.getText().toString().trim().equalsIgnoreCase(etconfirmnewpassword.getText().toString().trim()))) {
            Toast.makeText(this, "Please Enter Valid Confirm password", Toast.LENGTH_SHORT).show();

            return false;

        } else {
            return true;
        }


    }


    private void profileupdatewithoutimage() {

        showLoader();


        retrofitService.updatePassword(
                (verifyConfirmOTPDetailModel.getPhone()),
                etnewpassword.getText().toString()
        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(NewPasswordActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onBackPressed() {


    }
}