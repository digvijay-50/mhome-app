package com.example.otpregisterloginhome.Activities;

import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Avaliablefor;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Furnished;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Typesofproperty;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.configuration;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.property;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class AddPostActivity extends AppCompatActivity {

    TextView tv_proceed,tv_rent,tv_cell,tv_home,tv_apartment,tv_plot,tv_land,tv_residential,tv_comm,tv_office,tv_shop;
    EditText mSearchBoxView;
    SavePref savePref;
    RelativeLayout r_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });

        tv_rent=findViewById(R.id.tv_rent);
        tv_cell=findViewById(R.id.tv_cell);
        tv_proceed=findViewById(R.id.tv_proceed);
        tv_home=findViewById(R.id.tv_home);
        tv_apartment=findViewById(R.id.tv_apartment);
        tv_plot=findViewById(R.id.tv_plot);
        tv_land=findViewById(R.id.tv_land);
        tv_residential=findViewById(R.id.tv_residential);
        tv_comm=findViewById(R.id.tv_comm);
        tv_office=findViewById(R.id.tv_office);
        tv_shop=findViewById(R.id.tv_shop);
        r_location=findViewById(R.id.r_location);
        mSearchBoxView=findViewById(R.id.mSearchBoxView);

        SearchlookingTo="Rent";
        property="Residential";
        Typesofproperty="Home";

        savePref=new SavePref(AddPostActivity.this);
        tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        tv_rent.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        tv_cell.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

        tv_land.setVisibility(View.GONE);
        tv_plot.setVisibility(View.GONE);
        tv_office.setVisibility(View.GONE);
        tv_shop.setVisibility(View.GONE);

        
        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mSearchBoxView.getText().toString())){
                    Toast.makeText(AddPostActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i=new Intent(AddPostActivity.this,AddPostsecActivity.class);
                    startActivity(i);
                }
            }
        });

        r_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddPostActivity.this,MainSelectlocationActivity.class);
                i.putExtra("address",savePref.getAddress());
                startActivityForResult(i,1004);
            }
        });

        mSearchBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddPostActivity.this,MainSelectlocationActivity.class);
                i.putExtra("address",savePref.getAddress());
                startActivityForResult(i,1004);
            }
        });

        tv_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_land.setVisibility(View.GONE);
                tv_plot.setVisibility(View.GONE);
                tv_office.setVisibility(View.GONE);
                tv_shop.setVisibility(View.GONE);
                tv_home.setVisibility(View.VISIBLE);
                tv_apartment.setVisibility(View.VISIBLE);
                SearchlookingTo="Rent";
                Avaliablefor="";
                Furnished="";
                configuration="";
                Typesofproperty="Home";
                property="Residential";
                tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_rent.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_cell.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));



            }
        });


        tv_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_land.setVisibility(View.VISIBLE);
                tv_plot.setVisibility(View.VISIBLE);
                tv_office.setVisibility(View.GONE);
                tv_shop.setVisibility(View.GONE);
                tv_home.setVisibility(View.VISIBLE);
                tv_apartment.setVisibility(View.VISIBLE);
                SearchlookingTo="Sell";
                Avaliablefor="";
                Furnished="";
                configuration="";
                Typesofproperty="Home";
                property="Residential";
                tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_rent.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_cell.setBackground(getResources().getDrawable(R.drawable.btnbgselect));


            


            }
        });

        tv_residential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(SearchlookingTo.equalsIgnoreCase("sell")){
                    tv_land.setVisibility(View.VISIBLE);
                    tv_plot.setVisibility(View.VISIBLE);
                }else{
                    tv_land.setVisibility(View.GONE);
                    tv_plot.setVisibility(View.GONE);
                }


                tv_office.setVisibility(View.GONE);
                tv_shop.setVisibility(View.GONE);

                tv_home.setVisibility(View.VISIBLE);
                tv_apartment.setVisibility(View.VISIBLE);
                Avaliablefor="";
                Furnished="";
                configuration="";
                Typesofproperty="Home";
                property="Residential";
                tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));


            }
        });

        tv_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_office.setVisibility(View.VISIBLE);
                tv_shop.setVisibility(View.VISIBLE);
                tv_home.setVisibility(View.GONE);
                tv_apartment.setVisibility(View.GONE);

                Avaliablefor="";
                Furnished="";
                configuration="";
                Typesofproperty="Office";
                property="Commercial";
                if(SearchlookingTo.equalsIgnoreCase("Rent")){
                    tv_land.setVisibility(View.GONE);
                    tv_plot.setVisibility(View.GONE);
                }else{
                    tv_land.setVisibility(View.VISIBLE);
                    tv_plot.setVisibility(View.VISIBLE);
                }
                tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgselect));

                tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
             

            }
        });
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ll_flor.setVisibility(View.GONE);
                Avaliablefor="";
                Furnished="";
                configuration="";
                Typesofproperty="Home";
                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });

        tv_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ll_flor.setVisibility(View.GONE);
                Avaliablefor="";
                Furnished="";
                configuration="";
                Typesofproperty="Office";
                tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });

        tv_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ll_flor.setVisibility(View.GONE);
                Avaliablefor="";
                Furnished="";
                configuration="";
                Typesofproperty="Shop";
                tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });

        tv_apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typesofproperty="Apartment";
                Avaliablefor="";
                Furnished="";
                configuration="";
                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));


            }
        });


        tv_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typesofproperty="Plot";
                Avaliablefor="";
                Furnished="";
                configuration="";
                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));



            }
        });


        tv_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typesofproperty="Land";
                Avaliablefor="";
                Furnished="";
                configuration="";
                tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            }
        });
        
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1004){

            try {
                mSearchBoxView.setText(data.getStringExtra("address"));
                getLocationFromAddress1(AddPostActivity.this, data.getStringExtra("address"));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public LatLng getLocationFromAddress1(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

            if (address.get(0).getSubLocality() == null) {
                savePref.setArea(address.get(0).getLocality());
                savePref.setstate(address.get(0).getAdminArea());
                savePref.setcity(address.get(0).getLocality());
                savePref.setAddress(address.get(0).getAddressLine(0));

            } else {

                savePref.setArea(address.get(0).getSubLocality());
                savePref.setstate(address.get(0).getAdminArea());
                savePref.setcity(address.get(0).getLocality());
                savePref.setAddress(address.get(0).getAddressLine(0));

            }


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}