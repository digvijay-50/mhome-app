package com.example.otpregisterloginhome.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otpregisterloginhome.ModelClasses.GetPostPackage_Inner;
import com.example.otpregisterloginhome.ModelClasses.UpdateDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UpdateModel;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostpackagedetailActivity extends AppCompatActivity {


    TextView tv_pakname, tv_price, tv_numberpost, tv_duration,tv_des,tv_purchase;
    CardView card;
    SavePref savePref;
    RetrofitService retrofitService;
    LinearLayout loaderlayout;
    GetPostPackage_Inner getPostPackage_inner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpackagedetail);

        ImageView backiv = findViewById(R.id.backic);
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });


        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Property Post Package");
        savePref=new SavePref(PostpackagedetailActivity.this);
        getPostPackage_inner= (GetPostPackage_Inner) getIntent().getSerializableExtra("getPostPackage_inner");
        tv_pakname = findViewById(R.id.tv_pakname);
        tv_price = findViewById(R.id.tv_price);
        card = findViewById(R.id.card);
        tv_numberpost = findViewById(R.id.tv_numberpost);
        tv_duration = findViewById(R.id.tv_duration);
        tv_des = findViewById(R.id.tv_des);
        tv_purchase = findViewById(R.id.tv_purchase);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);
        tv_pakname.setText(getPostPackage_inner.getPropertyPostPackageName());
        tv_price.setText("₹" + getPostPackage_inner.getPropertyPostPackagePrice());
        tv_numberpost.setText("Number of Post : " + getPostPackage_inner.getNoOfPropery());
        tv_duration.setText("Package Duration : " + getPostPackage_inner.getPackageDuration() + " Days");

        tv_des.setText(Html.fromHtml(getPostPackage_inner.getPropertyPostPackageDescription()));

        tv_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_postpackagepurchase();

            }
        });
    }


    private void add_postpackagepurchase() {

        showLoader();


        retrofitService.add_postpackagepurchase(
               savePref.getUserId(),
                getPostPackage_inner.getPropertyPostPackageId(),
                getPostPackage_inner.getPropertyPostPackageDiscountPrice(),
                "0",
               "0",
                "0"

        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(PostpackagedetailActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {

                        Intent i=new Intent(PostpackagedetailActivity.this,NewHomeActivity.class);
                        startActivity(i);
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



    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }


}