package com.example.otpregisterloginhome.Fragments;

import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Avaliablefor;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Floordetail;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Furnished;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.SearchlookingTo1;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Typesofproperty;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Typesofproperty1;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.property;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.configuration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpregisterloginhome.Activities.MainSelectlocationActivity;
import com.example.otpregisterloginhome.Activities.PropertylistActivity;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.Constant;
import com.example.otpregisterloginhome.placeapi.MapplaceActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.slider.RangeSlider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class SearchCommFragment extends Fragment {


    SavePref savePref;
    LinearLayout loaderlayout,ll_bottomconf,ll_facing;
    TextView tv_add,tv_rent,tv_cell,tv_home,tv_apartment,tv_plot,tv_land,tv_search,tv_north,tv_south,tv_east,tv_west;
    RecyclerView recycler;
    LinearLayout ll_add,ll_furni;
    RangeSlider continuousRangeSlider;
    RelativeLayout r_location;
    ArrayList<String>selectedApps=new ArrayList<>();
    TextView tv_firstrange,tv_secrange;

    LinearLayout ll_flor;
    TextView tv_unfur,tv_semifur,tv_fur;

    EditText et_fname,et_loc;



    public SearchCommFragment() {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchcomm, container, false);
        try {
            savePref = new SavePref(getActivity());
            loaderlayout = view.findViewById(R.id.loaderlayout);
            continuousRangeSlider = view.findViewById(R.id.continuousRangeSlider);
            r_location = view.findViewById(R.id.r_location);
            tv_search = view.findViewById(R.id.tv_search);
            ll_facing = view.findViewById(R.id.ll_facing);
            recycler = view.findViewById(R.id.recycler);
            tv_add = view.findViewById(R.id.tv_add);
            ll_furni = view.findViewById(R.id.ll_furni);
            ll_add = view.findViewById(R.id.ll_add);
            et_loc = view.findViewById(R.id.et_loc);
            tv_rent = view.findViewById(R.id.tv_rent);
            ll_flor = view.findViewById(R.id.ll_flor);
            et_fname = view.findViewById(R.id.et_fname);
            tv_apartment = view.findViewById(R.id.tv_apartment);
            tv_cell = view.findViewById(R.id.tv_cell);
            tv_plot = view.findViewById(R.id.tv_plot);
            tv_land = view.findViewById(R.id.tv_land);
            tv_home = view.findViewById(R.id.tv_home);
            ll_bottomconf = view.findViewById(R.id.ll_bottomconf);
            tv_firstrange = view.findViewById(R.id.tv_firstrange);
            tv_secrange = view.findViewById(R.id.tv_secrange);
            tv_unfur = view.findViewById(R.id.tv_unfur);
            tv_semifur = view.findViewById(R.id.tv_semifur);
            tv_fur = view.findViewById(R.id.tv_fur);

            tv_north = view.findViewById(R.id.tv_north);
            tv_south = view.findViewById(R.id.tv_south);
            tv_east = view.findViewById(R.id.tv_east);
            tv_west = view.findViewById(R.id.tv_west);
            recycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
            ll_flor.setVisibility(View.GONE);
            ll_facing.setVisibility(View.VISIBLE);
            SearchlookingTo="Rent";
            SearchlookingTo1="Rent";
            Typesofproperty="";
            Typesofproperty1="";


            tv_north.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.property_facing="North";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });

            tv_east.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.property_facing="East";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });

            tv_west.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.property_facing="West";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });

            tv_south.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.property_facing="South";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
                }
            });


            tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

            tv_rent.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
            tv_cell.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

            tv_land.setVisibility(View.GONE);
            tv_plot.setVisibility(View.GONE);

            continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
            continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
            continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
            continuousRangeSlider.setValues(0.0f,5000000.0f);

            tv_firstrange.setText("₹ 0");
            tv_secrange.setText("₹ 50 Lac");
            continuousRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                }

                @SuppressLint("RestrictedApi")
                @Override
                public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                    String currentString=slider.getValues().toString().replace("[","").replace("]","");
                    StringTokenizer tokens = new StringTokenizer(currentString, ",");
                    String first = tokens.nextToken();// this will contain "Fruit"
                    String second = tokens.nextToken();
                    float firstNumber = Float.parseFloat(first);
                    float secnumber = Float.parseFloat(second);
                    int firstcheck= Integer.parseInt(String.format("%.0f", firstNumber));
                    if (firstcheck >= 10000000) {
                        DecimalFormat df = new DecimalFormat("#.#######");
                        double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                        String out = df.format(lakhs);
                        tv_firstrange.setText("₹ "+out+" Cr");
                    } else if (firstcheck >= 100000) {

                        DecimalFormat df = new DecimalFormat("#.######");
                        double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                        String out = df.format(lakhs);
                        tv_firstrange.setText("₹ "+out+" Lac");
                    }else if (firstcheck >= 10000) {
                        tv_firstrange.setText("₹ "+firstcheck+"");
                    }else{
                        tv_firstrange.setText("₹ "+firstcheck+"");
                    }

                    int seccheck= Integer.parseInt(String.format("%.0f", secnumber));
                    if (seccheck >= 10000000) {
                        DecimalFormat df = new DecimalFormat("#.#######");
                        double lakhs = Double.parseDouble(String.valueOf(seccheck)) / 10000000;
                        String out = df.format(lakhs);
                        tv_secrange.setText("₹ "+out+" Cr");
                    } else if (seccheck >= 100000) {
                        DecimalFormat df = new DecimalFormat("#.######");
                        double lakhs = Double.parseDouble(String.valueOf(seccheck)) / 100000;
                        String out = df.format(lakhs);
                        tv_secrange.setText("₹ "+out+" Lac");
    //                    seccheck = (seccheck / 100000);
    //                    tv_secrange.setText("₹ "+seccheck+" Lac");
                    }else if (seccheck >= 10000) {
    //                    seccheck = (seccheck / 10000);
                        tv_secrange.setText("₹ "+seccheck+"");
                    }else {
    //                    seccheck = (seccheck / 10000);
                        tv_secrange.setText("₹ "+seccheck+"");
                    }
    //                tv_secrange.setText(String.format("%.0f", firstNumber)+"");
                }
            });

            tv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validation()){
                        Constant.location=et_loc.getText().toString();
                        property="Commercial";
                        Floordetail=et_fname.getText().toString();

                        Intent i=new Intent(getContext(), PropertylistActivity.class);
                        startActivity(i);
                    }

                }
            });
            tv_unfur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Furnished="Unfurnished";
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });
            tv_semifur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Furnished="Semi Furnished";
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });
            tv_fur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Furnished="Furnished";
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                }
            });

            tv_rent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
                    continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                    continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
                    continuousRangeSlider.setValues(0.0f,5000000.0f);

                    tv_firstrange.setText("₹ "+0+"");
                    tv_secrange.setText("₹ 50 Lac");

                    ll_bottomconf.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.VISIBLE);
    //                ll_flor.setVisibility(View.VISIBLE);
                    tv_land.setVisibility(View.GONE);
                    tv_plot.setVisibility(View.GONE);
                    SearchlookingTo="Rent";
                    SearchlookingTo1="Rent";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Typesofproperty="Office";
                    Typesofproperty1="Office";
                    Constant.property_facing="";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_rent.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_cell.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });

            tv_cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    continuousRangeSlider.setStepSize(Float.parseFloat("5000.0"));
                    continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                    continuousRangeSlider.setValueTo(Float.parseFloat("1000000.0"));
                    continuousRangeSlider.setValues(0.0f,1000000.0f);
                    tv_firstrange.setText("₹ "+0+"");
                    tv_secrange.setText("₹ 10 Lac");

                    ll_bottomconf.setVisibility(View.VISIBLE);
                    tv_land.setVisibility(View.VISIBLE);
                    tv_plot.setVisibility(View.VISIBLE);
                    SearchlookingTo="Sell";
                    SearchlookingTo1="Sell";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Typesofproperty="Office";
                    Typesofproperty1="Office";
                    Constant.property_facing="";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                    tv_rent.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_cell.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));


                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                    ll_furni.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.VISIBLE);
                }
            });
            tv_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(SearchlookingTo.equalsIgnoreCase("Rent")){
                        ll_flor.setVisibility(View.VISIBLE);
                        ll_bottomconf.setVisibility(View.VISIBLE);
                        ll_facing.setVisibility(View.VISIBLE);
                        Avaliablefor="";
                        Furnished="";
                        configuration="";
                        Constant.property_facing="";
                        Typesofproperty="Office";
                        Typesofproperty1="Office";
                        Constant.property_facing="";
                        tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

                        tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                        tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));



                        tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    }else{
                        ll_flor.setVisibility(View.VISIBLE);
                        ll_facing.setVisibility(View.VISIBLE);
                        ll_furni.setVisibility(View.GONE);
                        Avaliablefor="";
                        Furnished="";
                        configuration="";
                        Constant.property_facing="";
                        Typesofproperty="Office";
                        Typesofproperty1="Office";
                        Constant.property_facing="";
                        tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                        tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));



                        tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    }



                }
            });

            tv_apartment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(SearchlookingTo.equalsIgnoreCase("Rent")){
                        ll_bottomconf.setVisibility(View.VISIBLE);
                        ll_facing.setVisibility(View.VISIBLE);
                        ll_flor.setVisibility(View.VISIBLE);
                        Typesofproperty="Shop";
                        Typesofproperty1="Shop";
                        Avaliablefor="";
                        Furnished="";
                        configuration="";
                        Constant.property_facing="";
                        tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                        tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));


                        tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    }else{
                        ll_furni.setVisibility(View.GONE);
                        ll_flor.setVisibility(View.VISIBLE);
                        ll_facing.setVisibility(View.VISIBLE);
                        Typesofproperty="Shop";
                        Typesofproperty1="Shop";
                        Avaliablefor="";
                        Furnished="";
                        configuration="";
                        Constant.property_facing="";
                        tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                        tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));


                        tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                        tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    }


                }
            });


            tv_plot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_furni.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
    //                ll_bottomconf.setVisibility(View.GONE);
    //                ll_flor.setVisibility(View.VISIBLE);
                    Typesofproperty="Plot";
                    Typesofproperty1="Plot";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Constant.property_facing="";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });


            tv_land.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_furni.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
                    Typesofproperty="Land";
                    Typesofproperty1="Land";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Constant.property_facing="";
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));


                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });

            if (selectedApps.size()==0){
                ll_add.setVisibility(View.GONE);
            }else{
                ll_add.setVisibility(View.VISIBLE);
            }
            tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(), MainSelectlocationActivity.class);
                    i.putExtra("abs",selectedApps);
                    i.putExtra("address",savePref.getAddress());
                    startActivityForResult(i,1004);
                }
            });

            r_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(getContext(), MainSelectlocationActivity.class);
                    i.putExtra("abs",selectedApps);
                    i.putExtra("address",savePref.getAddress());
                    startActivityForResult(i,1004);
                }
            });
            et_loc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(getContext(), MainSelectlocationActivity.class);
                    i.putExtra("abs",selectedApps);
                    i.putExtra("address",savePref.getAddress());

                    startActivityForResult(i,1004);
                }
            });
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==1004){

                try {
                    et_loc.setText(data.getStringExtra("address"));
                    getLocationFromAddress1(getActivity(), data.getStringExtra("address"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void onResume() {
        super.onResume();
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
//            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getAddressLine(0));
//            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getAdminArea());
//            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getSubLocality());
//            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getCountryName());
//            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getLocale());
//            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getLocality());
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
//            Log.i("onClick", "getLocationFromAddress: "+location.getLatitude()+" "+location.getLongitude());


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


    private boolean validation() {

       if (SearchlookingTo1.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Please Select Looking to", Toast.LENGTH_SHORT).show();

            return false;
        } else if (Typesofproperty1.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Please Select Types Of Property", Toast.LENGTH_SHORT).show();

            return false;
        }else  if (TextUtils.isEmpty(et_loc.getText().toString().trim())) {
            Toast.makeText(getContext(), "Please Enter Location", Toast.LENGTH_SHORT).show();

            return false;
        }  else {
            return true;
        }


    }

}