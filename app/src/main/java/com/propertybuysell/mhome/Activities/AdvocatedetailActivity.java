package com.propertybuysell.mhome.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.propertybuysell.mhome.ModelClasses.GetAdvocate_Inner;
import com.propertybuysell.mhome.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class AdvocatedetailActivity extends AppCompatActivity {
    TextView tv_pakname, tv_price,tv_numberpost,tv_duration,tv_email,tv_location,tv_district,tv_state,tv_tehsil,tv_city;
    GetAdvocate_Inner getAdvocate_inner;
    RoundedImageView im_adimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advocatedetail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Advocate Detail");

        getAdvocate_inner= (GetAdvocate_Inner) getIntent().getSerializableExtra("getAdvocate_inners");



        tv_pakname = findViewById(R.id.tv_pakname);
        tv_price = findViewById(R.id.tv_price);
        tv_numberpost = findViewById(R.id.tv_numberpost);
        tv_duration = findViewById(R.id.tv_duration);
        im_adimage = findViewById(R.id.im_adimage);
        tv_email = findViewById(R.id.tv_email);
        tv_location = findViewById(R.id.tv_location);
        tv_district = findViewById(R.id.tv_district);
        tv_state = findViewById(R.id.tv_state);
        tv_tehsil = findViewById(R.id.tv_tehsil);
        tv_city = findViewById(R.id.tv_city);


        tv_pakname.setText(getAdvocate_inner.getAdvocateName());
        tv_price.setText("Mobile No : "+getAdvocate_inner.getAdvocateMobile());
        tv_numberpost.setText("Experience : "+getAdvocate_inner.getAdvocateExperience());
        tv_duration.setText("Qualification : "+getAdvocate_inner.getAdvocateQualification());
        tv_email.setText("Email ID : "+getAdvocate_inner.getAdvocateEmail());
        tv_location.setText("Location : "+getAdvocate_inner.getAdvocateAddress());
        tv_district.setText("District : "+getAdvocate_inner.getAdvocateDistrict());
        tv_state.setText("State : "+getAdvocate_inner.getAdvocateState());
        tv_tehsil.setText("Tehsil : "+getAdvocate_inner.getAdvocate_tehsil());
        tv_city.setText("City : "+getAdvocate_inner.getAdvocate_city());

        Glide.with(AdvocatedetailActivity.this)
                .load(getAdvocate_inner.getAdvocateProfile())
                .placeholder(R.drawable.userimage)
                .into(im_adimage);
        
    }
}