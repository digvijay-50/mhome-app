package com.propertybuysell.mhome.Activities;

import static com.propertybuysell.mhome.RetrofitUtils.Constant.Avaliablefor;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Furnished;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Typesofproperty;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.configuration;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.property;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.propertybuysell.mhome.ModelClasses.GetProperty;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.Constant;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostActivity extends AppCompatActivity {

    TextView tv_proceed,tv_rent,tv_cell,tv_home,tv_apartment,tv_plot,tv_land,tv_residential,tv_comm,tv_office,tv_shop;
    EditText mSearchBoxView;
    SavePref savePref;
    LinearLayout loaderlayout;
    RelativeLayout r_location;
    RetrofitService retrofitService;
    String property_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });

        property_id=getIntent().getStringExtra("property_id");
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
        loaderlayout=findViewById(R.id.loaderlayout);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);

        TextView tv_top1=findViewById(R.id.tv_top1);
        tv_top1.setText("Edit Basic Details");

        get_property();
        savePref=new SavePref(EditPostActivity.this);

//        SearchlookingTo="Rent";
//        property="Residential";
//        Typesofproperty="Home";
//        tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
//        tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
//        tv_rent.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
//        tv_cell.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
//        tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
//        tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
//
//        tv_land.setVisibility(View.GONE);
//        tv_plot.setVisibility(View.GONE);
//        tv_office.setVisibility(View.GONE);
//        tv_shop.setVisibility(View.GONE);

        


        r_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EditPostActivity.this,MainSelectlocationActivity.class);
                i.putExtra("address",savePref.getAddress());
                startActivityForResult(i,1004);
            }
        });

        mSearchBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EditPostActivity.this,MainSelectlocationActivity.class);
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


                if(SearchlookingTo.equalsIgnoreCase("Sell")){
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
                getLocationFromAddress1(EditPostActivity.this, data.getStringExtra("address"));
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
                Constant.s_state = address.get(0).getAdminArea();
                Constant.s_city =address.get(0).getLocality();
                Constant.s_area = address.get(0).getLocality();
            } else {

                savePref.setArea(address.get(0).getSubLocality());
                savePref.setstate(address.get(0).getAdminArea());
                savePref.setcity(address.get(0).getLocality());
                savePref.setAddress(address.get(0).getAddressLine(0));
                Constant.s_state = address.get(0).getAdminArea();
                Constant.s_city = address.get(0).getLocality();
                Constant.s_area = address.get(0).getSubLocality();
            }


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private void get_property() {
        showLoader();

        retrofitService.get_single_property(SavePref.getUserId(), property_id).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {
                try {
                    setdata(response.body().getJsonData().get(0));
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<GetProperty> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

    private void setdata(GetProperty_Inner getProperty_inner) {


        Constant.s_state = getProperty_inner.getPropertyState();
        Constant.s_city = getProperty_inner.getPropertyCity();
        Constant.s_area =getProperty_inner.getPropertyArea();
        Constant.s_pincode = getProperty_inner.getPropertyPincode();
        Constant.s_tehsil = getProperty_inner.getPropertyTehsil();
        Constant.s_khasra = getProperty_inner.getPropertyKhasraNo();
        Constant.s_district = getProperty_inner.getPropertyDistrict();

        if (getProperty_inner.getPropertyFor().equalsIgnoreCase("Rent")){
            SearchlookingTo="Rent";
            tv_rent.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_cell.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_home.setVisibility(View.VISIBLE);
            tv_apartment.setVisibility(View.VISIBLE);
            tv_land.setVisibility(View.GONE);
            tv_plot.setVisibility(View.GONE);
            tv_office.setVisibility(View.GONE);
            tv_shop.setVisibility(View.GONE);
        }else{
            SearchlookingTo="Sell";
            tv_cell.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_rent.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_home.setVisibility(View.VISIBLE);
            tv_apartment.setVisibility(View.VISIBLE);
            tv_land.setVisibility(View.VISIBLE);
            tv_plot.setVisibility(View.VISIBLE);
            tv_office.setVisibility(View.GONE);
            tv_shop.setVisibility(View.GONE);
        }

        if (getProperty_inner.getPropertyType().equalsIgnoreCase("Residential")){
            property="Residential";
            tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

            if(SearchlookingTo.equalsIgnoreCase("Rent")){
                tv_home.setVisibility(View.VISIBLE);
                tv_apartment.setVisibility(View.VISIBLE);
                tv_land.setVisibility(View.GONE);
                tv_plot.setVisibility(View.GONE);
                tv_office.setVisibility(View.GONE);
                tv_shop.setVisibility(View.GONE);
            }else{
                tv_home.setVisibility(View.VISIBLE);
                tv_apartment.setVisibility(View.VISIBLE);
                tv_land.setVisibility(View.VISIBLE);
                tv_plot.setVisibility(View.VISIBLE);
                tv_office.setVisibility(View.GONE);
                tv_shop.setVisibility(View.GONE);
            }



        }else{
            property="Commercial";
            tv_comm.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_residential.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

            if(SearchlookingTo.equalsIgnoreCase("Rent")){
                tv_home.setVisibility(View.GONE);
                tv_apartment.setVisibility(View.GONE);
                tv_land.setVisibility(View.GONE);
                tv_plot.setVisibility(View.GONE);
                tv_office.setVisibility(View.VISIBLE);
                tv_shop.setVisibility(View.VISIBLE);
            }else{
                tv_home.setVisibility(View.GONE);
                tv_apartment.setVisibility(View.GONE);
                tv_land.setVisibility(View.VISIBLE);
                tv_plot.setVisibility(View.VISIBLE);
                tv_office.setVisibility(View.VISIBLE);
                tv_shop.setVisibility(View.VISIBLE);
            }
        }


        if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Home")){
            Typesofproperty=getProperty_inner.getPropertySubType();
            tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        }else if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Apartment")){
            Typesofproperty=getProperty_inner.getPropertySubType();
            tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

        }else if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Plot")){
            Typesofproperty=getProperty_inner.getPropertySubType();
            tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

        }else if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Land")){
            Typesofproperty=getProperty_inner.getPropertySubType();
            tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

        }else if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Office")){
            Typesofproperty=getProperty_inner.getPropertySubType();
            tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

        }else if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Shop")){
            Typesofproperty=getProperty_inner.getPropertySubType();
            tv_home.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_apartment.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_plot.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_land.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_office.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_shop.setBackground(getResources().getDrawable(R.drawable.btnbgselect));

        }


        if(getProperty_inner.getPropertyArea().equalsIgnoreCase(getProperty_inner.getPropertyCity())){
            mSearchBoxView.setText(getProperty_inner.getPropertyCity()+" , "+getProperty_inner.getPropertyState());

        }else{
            mSearchBoxView.setText(getProperty_inner.getPropertyArea()+" , "+getProperty_inner.getPropertyCity()+" , "+getProperty_inner.getPropertyState());

        }


        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mSearchBoxView.getText().toString())){
                    Toast.makeText(EditPostActivity.this, "Please Enter City", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i=new Intent(EditPostActivity.this,EditPostsecActivity.class);
                    i.putExtra("getProperty_inner",getProperty_inner);
                    startActivity(i);
                }
            }
        });

//        tv_rent.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
//        tv_cell.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));



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