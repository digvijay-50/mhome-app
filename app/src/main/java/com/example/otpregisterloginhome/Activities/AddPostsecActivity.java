package com.example.otpregisterloginhome.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.Constant;

public class AddPostsecActivity extends AppCompatActivity {

    TextView tv_proceed;
    SavePref savePref;
    EditText et_state, et_city, et_area, et_pincode, et_tehsil, et_khasra, et_district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsec_post);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });

        savePref=new SavePref(AddPostsecActivity.this);
        tv_proceed = findViewById(R.id.tv_proceed);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_area = findViewById(R.id.et_area);
        et_pincode = findViewById(R.id.et_pincode);
        et_tehsil = findViewById(R.id.et_tehsil);
        et_khasra = findViewById(R.id.et_khasra);
        et_district = findViewById(R.id.et_district);


        et_state.setText(savePref.getstate());
        et_city.setText(savePref.getcity());
        et_area.setText(savePref.getArea());
        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    Constant.s_state = et_state.getText().toString();
                    Constant.s_city = et_city.getText().toString();
                    Constant.s_area = et_area.getText().toString();
                    Constant.s_pincode = et_pincode.getText().toString();
                    Constant.s_tehsil = et_tehsil.getText().toString();
                    Constant.s_khasra = et_khasra.getText().toString();
                    Constant.s_district = et_district.getText().toString();
                    Intent i = new Intent(AddPostsecActivity.this, AddPostthirdActivity.class);
                    startActivity(i);
                }
            }
        });
    }


    private boolean validation() {

        if (TextUtils.isEmpty(et_state.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter State", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(et_city.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(et_area.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Area", Toast.LENGTH_SHORT).show();

            return false;
        }  else if (TextUtils.isEmpty(et_pincode.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }


    }

}