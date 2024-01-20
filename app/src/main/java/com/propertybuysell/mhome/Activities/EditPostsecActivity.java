package com.propertybuysell.mhome.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.Constant;

public class EditPostsecActivity extends AppCompatActivity {

    TextView tv_proceed;
    SavePref savePref;
    GetProperty_Inner getProperty_inner;
    EditText et_state, et_city, et_area, et_pincode, et_tehsil, et_khasra, et_district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsec_post);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });

        savePref=new SavePref(EditPostsecActivity.this);
        tv_proceed = findViewById(R.id.tv_proceed);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_area = findViewById(R.id.et_area);
        et_pincode = findViewById(R.id.et_pincode);
        et_tehsil = findViewById(R.id.et_tehsil);
        et_khasra = findViewById(R.id.et_khasra);
        et_district = findViewById(R.id.et_district);


        TextView tv_top1=findViewById(R.id.tv_top1);
        tv_top1.setText("Edit Property Details");

        getProperty_inner= (GetProperty_Inner) getIntent().getSerializableExtra("getProperty_inner");
        et_state.setText(Constant.s_state);
        et_city.setText(Constant.s_city);
        et_area.setText(Constant.s_area);
        et_pincode.setText(getProperty_inner.getPropertyPincode());
        et_tehsil.setText(getProperty_inner.getPropertyTehsil());
        et_khasra.setText(getProperty_inner.getPropertyKhasraNo());
        et_district.setText(getProperty_inner.getPropertyDistrict());

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
                    Intent i = new Intent(EditPostsecActivity.this, EditPostthirdActivity.class);
                    i.putExtra("getProperty_inner",getProperty_inner);
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