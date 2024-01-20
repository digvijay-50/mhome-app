package com.propertybuysell.mhome.Fragments;

import static com.propertybuysell.mhome.RetrofitUtils.Constant.Floordetail;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Typesofproperty;

import static com.propertybuysell.mhome.RetrofitUtils.Constant.endpricerangetxt;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.property;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.configuration;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Furnished;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Avaliablefor;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.startpricerangetxt;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.propertybuysell.mhome.Activities.AddPostthirdActivity;
import com.propertybuysell.mhome.Activities.MainSelectlocationActivity;
import com.propertybuysell.mhome.Activities.PropertylistActivity;
import com.propertybuysell.mhome.ModelClasses.MyItem;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.Constant;
import com.propertybuysell.mhome.placeapi.MapplaceActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.slider.RangeSlider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class SearchResidentialFragment extends Fragment {


    SavePref savePref;
    RecyclerView recycler_conf,re_furnishing,re_facing;
    HashMap<String, String> hashMap = new HashMap<>();
    LinearLayout loaderlayout,ll_avaliable,ll_facing;
    TextView tv_add,tv_rent,tv_cell,tv_home,tv_apartment,tv_plot,tv_land,tv_search,tv_north,tv_south,tv_east,tv_west;
    RecyclerView recycler;
    EditText et_fname,et_loc;
    LinearLayout ll_add,ll_bottomconf;
    RangeSlider continuousRangeSlider;
    TextView tv_firstrange,tv_secrange;
    RelativeLayout r_location;
    ArrayList<String>selectedApps=new ArrayList<>();

    LinearLayout ll_flor;
    TextView tv_1rk,tv_1bhk,tv_2bhk,tv_3bhk,tv_4bhk,tv_5bhk,tv_other,tv_unfur,tv_semifur,tv_fur,tv_family,tv_bechlor,tv_avother;




    public SearchResidentialFragment() {

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
        View view = inflater.inflate(R.layout.fragment_searchresidental, container, false);

        try {
            savePref = new SavePref(getActivity());
            loaderlayout = view.findViewById(R.id.loaderlayout);
            continuousRangeSlider = view.findViewById(R.id.continuousRangeSlider);

            et_loc = view.findViewById(R.id.et_loc);
            tv_search = view.findViewById(R.id.tv_search);
            et_fname = view.findViewById(R.id.et_fname);
            r_location = view.findViewById(R.id.r_location);
            recycler = view.findViewById(R.id.recycler);
            re_furnishing = view.findViewById(R.id.re_furnishing);
            re_facing = view.findViewById(R.id.re_facing);
            tv_add = view.findViewById(R.id.tv_add);
            recycler_conf = view.findViewById(R.id.recycler_conf);
            ll_add = view.findViewById(R.id.ll_add);
            tv_rent = view.findViewById(R.id.tv_rent);
            ll_flor = view.findViewById(R.id.ll_flor);
            ll_facing = view.findViewById(R.id.ll_facing);
            ll_avaliable = view.findViewById(R.id.ll_avaliable);
            tv_firstrange = view.findViewById(R.id.tv_firstrange);
            tv_secrange = view.findViewById(R.id.tv_secrange);
            ll_bottomconf = view.findViewById(R.id.ll_bottomconf);
            tv_apartment = view.findViewById(R.id.tv_apartment);
            tv_cell = view.findViewById(R.id.tv_cell);
            tv_plot = view.findViewById(R.id.tv_plot);
            tv_land = view.findViewById(R.id.tv_land);
            tv_home = view.findViewById(R.id.tv_home);
            tv_1rk = view.findViewById(R.id.tv_1rk);
            tv_1bhk = view.findViewById(R.id.tv_1bhk);
            tv_2bhk = view.findViewById(R.id.tv_2bhk);
            tv_3bhk = view.findViewById(R.id.tv_3bhk);
            tv_4bhk = view.findViewById(R.id.tv_4bhk);
            tv_5bhk = view.findViewById(R.id.tv_5bhk);
            tv_other = view.findViewById(R.id.tv_other);
            tv_unfur = view.findViewById(R.id.tv_unfur);
            tv_semifur = view.findViewById(R.id.tv_semifur);
            tv_fur = view.findViewById(R.id.tv_fur);
            tv_family = view.findViewById(R.id.tv_family);
            tv_bechlor = view.findViewById(R.id.tv_bechlor);
            tv_avother = view.findViewById(R.id.tv_avother);
            recycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

            tv_north = view.findViewById(R.id.tv_north);
            tv_south = view.findViewById(R.id.tv_south);
            tv_east = view.findViewById(R.id.tv_east);
            tv_west = view.findViewById(R.id.tv_west);

            recycler_conf.setLayoutManager(new GridLayoutManager(getContext(),4));
            re_furnishing.setLayoutManager(new GridLayoutManager(getContext(),2));
            re_facing.setLayoutManager(new GridLayoutManager(getContext(),2));


            ArrayList<MyItem> confiarraylist=new ArrayList<>();

            confiarraylist.add(new MyItem("1 RK","Configuration"));
            confiarraylist.add(new MyItem("1 BHK","Configuration"));
            confiarraylist.add(new MyItem("2 BHK","Configuration"));
            confiarraylist.add(new MyItem("3 BHK","Configuration"));
            confiarraylist.add(new MyItem("4 BHK","Configuration"));
            confiarraylist.add(new MyItem("5 BHK","Configuration"));
            confiarraylist.add(new MyItem("Other","Configuration"));
            recycler_conf.setAdapter(new ConfigAdapter(getContext(),confiarraylist,"Configuration"));

            ArrayList<MyItem> furnishinglist=new ArrayList<>();

            furnishinglist.add(new MyItem("Unfurnished","furnishing"));
            furnishinglist.add(new MyItem("Semi Furnished","furnishing"));
            furnishinglist.add(new MyItem("Furnished","furnishing"));
            re_furnishing.setAdapter(new ConfigAdapter(getContext(),furnishinglist,"furnishing"));

            ArrayList<MyItem> facinglist=new ArrayList<>();

            facinglist.add(new MyItem("North","facing"));
            facinglist.add(new MyItem("South","facing"));
            facinglist.add(new MyItem("East","facing"));
            facinglist.add(new MyItem("West","facing"));
            re_facing.setAdapter(new ConfigAdapter(getContext(),facinglist,"facing"));



            ll_flor.setVisibility(View.GONE);
            SearchlookingTo="Rent";
            Typesofproperty="";
            tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

            tv_rent.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
            tv_cell.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

            tv_land.setVisibility(View.GONE);
            tv_plot.setVisibility(View.GONE);


            continuousRangeSlider.setStepSize(Float.parseFloat("5000.0"));
            continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
            continuousRangeSlider.setValueTo(Float.parseFloat("1000000.0"));
            continuousRangeSlider.setValues(0.0f,1000000.0f);
            tv_firstrange.setText("₹ "+0+"");
            tv_secrange.setText("₹ 10 Lac");


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


            continuousRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onStartTrackingTouch(@NonNull RangeSlider slider) {

    //                Log.i("onStartTrackingTouch", "onStartTrackingTouch: "+slider.getValues());
                }

                @SuppressLint("RestrictedApi")
                @Override
                public void onStopTrackingTouch(@NonNull RangeSlider slider) {
//                    Log.i("onStartTrackingTouch", "onStopTrackingTouch: "+slider.getValues());
                    String currentString=slider.getValues().toString().replace("[","").replace("]","");
                    StringTokenizer tokens = new StringTokenizer(currentString, ",");
                    String first = tokens.nextToken();// this will contain "Fruit"
                    String second = tokens.nextToken();

                    float firstNumber = Float.parseFloat(first);
                    float secnumber = Float.parseFloat(second);



                    int firstcheck= Integer.parseInt(String.format("%.0f", firstNumber));
                     Constant.startpricerange=String.format("%.0f", firstNumber);
                    Constant.endpricerange=String.format("%.0f", secnumber);

                    if (firstcheck >= 10000000) {
                        DecimalFormat df = new DecimalFormat("#.#######");
                        double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                        String out = df.format(lakhs);

                        tv_firstrange.setText("₹ "+out+" Cr");
                    } else if (firstcheck >= 100000) {
    //                    firstcheck = (firstcheck / 100000);
    //
    //                    tv_firstrange.setText("₹ "+firstcheck+" Lac");

                        DecimalFormat df = new DecimalFormat("#.######");
                        double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                        String out = df.format(lakhs);

                        tv_firstrange.setText("₹ "+out+" Lac");
                    }else if (firstcheck >= 10000) {
                   //     firstcheck = (firstcheck / 10000);
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
    //
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
                        configuration="";
                        Constant.property_facing="";
                        Furnished="";

                        property="Residential";
                        Floordetail=et_fname.getText().toString();
                        Constant.location=et_loc.getText().toString();
                        Constant.allchekhashmap = new HashMap<>();
                        Constant.allchekhashmap=hashMap;
                        startpricerangetxt=tv_firstrange.getText().toString();
                        endpricerangetxt=tv_secrange.getText().toString();
                        String currentString=continuousRangeSlider.getValues().toString().replace("[","").replace("]","");
                        StringTokenizer tokens = new StringTokenizer(currentString, ",");
                        String first = tokens.nextToken();// this will contain "Fruit"
                        String second = tokens.nextToken();

                        float firstNumber = Float.parseFloat(first);
                        float secnumber = Float.parseFloat(second);

                        Constant.startpricerange=String.format("%.0f", firstNumber);
                        Constant.endpricerange=String.format("%.0f", secnumber);

                        HashMap<String, List<String>> groupedData = new HashMap<>();
                        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            if (groupedData.containsKey(value)) {
                                groupedData.get(value).add(key);
                            } else {
                                List<String> keyList = new ArrayList<>();
                                keyList.add(key);
                                groupedData.put(value, keyList);
                            }
                        }

                        // Now you have a HashMap (groupedData) where keys are values, and values are lists of keys associated with each value
                        // You can access the data by value as needed
                        for (Map.Entry<String, List<String>> entry : groupedData.entrySet()) {
                            String value = entry.getKey();
                            List<String> keys = entry.getValue();
                            if (value.equalsIgnoreCase("Configuration")){
                                configuration=keys.toString().replace("[","").replace("]","").replace(", ",",");
                            }else if (value.equalsIgnoreCase("facing")){
                                Constant.property_facing=keys.toString().replace("[","").replace("]","").replace(", ",",");;
                            }else if (value.equalsIgnoreCase("furnishing")){
                                Furnished=keys.toString().replace("[","").replace("]","").replace(", ",",");;
                            }

//                    System.out.println("Value: " + value);
//                    System.out.println("Keys: " + keys);
                        }

                        Intent i=new Intent(getContext(), PropertylistActivity.class);
                        startActivity(i);

                    }

                }
            });

            tv_1rk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuration="1 RK";
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });
            tv_1bhk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuration="1 BHK";
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });
            tv_2bhk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuration="2 BHK";
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });
            tv_3bhk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuration="3 BHK";
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });
            tv_4bhk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuration="4 BHK";
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });
            tv_5bhk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuration="5 BHK";
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });
            tv_other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configuration="Other";
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));

                }
            });

            tv_family.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Avaliablefor="Family";
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });
            tv_bechlor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Avaliablefor="Bachelor";
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });

            tv_avother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Avaliablefor="Other";
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
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
                    ll_avaliable.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);

                    continuousRangeSlider.setStepSize(Float.parseFloat("5000.0"));
                    continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                    continuousRangeSlider.setValueTo(Float.parseFloat("1000000.0"));
                    continuousRangeSlider.setValues(0.0f,1000000.0f);
                    tv_firstrange.setText("₹ "+0+"");
                    tv_secrange.setText("₹ 10 Lac");



                    ll_bottomconf.setVisibility(View.VISIBLE);
                    tv_land.setVisibility(View.GONE);
                    tv_plot.setVisibility(View.GONE);
                    SearchlookingTo="Rent";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Typesofproperty="Home";
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
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });

            tv_cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_avaliable.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.VISIBLE);
                    continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
                    continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                    continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
                    continuousRangeSlider.setValues(0.0f,5000000.0f);

                    tv_firstrange.setText("₹ "+0+"");
                    tv_secrange.setText("₹ 50 Lac");
                    ll_bottomconf.setVisibility(View.VISIBLE);
                    tv_land.setVisibility(View.VISIBLE);
                    tv_plot.setVisibility(View.VISIBLE);
                    SearchlookingTo="Buy";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Typesofproperty="Home";
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

                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                }
            });
            tv_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_bottomconf.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.GONE);

                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Constant.property_facing="";
                    Typesofproperty="Home";
                    tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });

            tv_apartment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll_bottomconf.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_facing.setVisibility(View.VISIBLE);
                    Typesofproperty="Apartment";
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

                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });


            tv_plot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Typesofproperty="Plot";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Constant.property_facing="";
                    ll_bottomconf.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
                    tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));

                    tv_home.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_apartment.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgselect));
                    tv_land.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));

                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                }
            });


            tv_land.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ll_bottomconf.setVisibility(View.GONE);
                    ll_facing.setVisibility(View.GONE);
                    Typesofproperty="Land";
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

                    tv_1rk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_1bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_2bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_3bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_4bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_5bhk.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_other.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_unfur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_family.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_bechlor.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_avother.setBackground(getContext().getResources().getDrawable(R.drawable.btnbgwhitestock));
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
                    Intent i=new Intent(getContext(), MapplaceActivity.class);
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


    private boolean validation() {

        if (SearchlookingTo.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Please Select Looking to", Toast.LENGTH_SHORT).show();

            return false;
        } else if (Typesofproperty.equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Please Select Types Of Property", Toast.LENGTH_SHORT).show();

            return false;
        }else  if (TextUtils.isEmpty(et_loc.getText().toString().trim())) {
            Toast.makeText(getContext(), "Please Enter Location", Toast.LENGTH_SHORT).show();

            return false;
        }  else {
            return true;
        }


    }

    private class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ViewHolder>{
        Context context;
        ArrayList<MyItem> confiarraylist;
        String category;


        public ConfigAdapter(Context context, ArrayList<MyItem> confiarraylist, String category) {
            this.confiarraylist=confiarraylist;

            this.context=context;
            this.category=category;

        }

        @NonNull
        @Override
        public ConfigAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(context).inflate(R.layout.adapter_checklayot,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ConfigAdapter.ViewHolder holder, int position) {


            holder.tv_checktxt.setText(confiarraylist.get(position).getItemName());



            holder.tv_checktxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String keyToCheck = confiarraylist.get(position).getItemName();
//                    String keyToCheck = "value2";
                    if (hashMap.containsKey(keyToCheck)) {

//                        String value = hashMap.get(keyToCheck);
                        holder.tv_checktxt.setBackground(context.getDrawable(R.drawable.btnbgwhitestock));
                        hashMap.remove(confiarraylist.get(position).getItemName());
//                        Toast.makeText(context, "Done "+value, Toast.LENGTH_SHORT).show();
                    } else {
                        holder.tv_checktxt.setBackground(context.getDrawable(R.drawable.btnbgselect));
//                        Toast.makeText(context, "not Done ", Toast.LENGTH_SHORT).show();
//                        hashMap.put("key2", "value2");
                        hashMap.put(confiarraylist.get(position).getItemName(), confiarraylist.get(position).getCategory());
                    }
                }
            });
        }

        @Override
        public long getItemId(int position) {
            // Return a unique ID for each item based on its position or data
            return position;
        }


        @Override
        public int getItemCount() {
            return confiarraylist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_checktxt;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_checktxt=itemView.findViewById(R.id.tv_checktxt);
            }
        }
    }

}