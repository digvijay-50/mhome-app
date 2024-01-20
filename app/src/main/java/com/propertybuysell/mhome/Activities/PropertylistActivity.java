package com.propertybuysell.mhome.Activities;

import static com.propertybuysell.mhome.Activities.SplashActivity.disablefor1sec;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Avaliablefor;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Floordetail;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Furnished;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.SearchlookingTo1;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Typesofproperty;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Typesofproperty1;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.endpricerange;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.endpricerangetxt;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.pricerange;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.property;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.configuration;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.property_facing;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.startpricerange;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.startpricerangetxt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.propertybuysell.mhome.Fragments.SearchResidentialFragment;
import com.propertybuysell.mhome.ModelClasses.FavModel;
import com.propertybuysell.mhome.ModelClasses.GetProperty;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.ModelClasses.MyItem;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.Constant;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.propertybuysell.mhome.placeapi.MapplaceActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class PropertylistActivity extends AppCompatActivity {

    RecyclerView re_property;
    LinearLayout loaderlayout, ll_short;
    SavePref savePref;
    RetrofitService retrofitService;
    EditText et_loc,et_loc1;

    SwipeRefreshLayout refres;

    LinearLayout l_nodata, ll_filter;
    int currentpage = 1;
    public boolean isLoading = false;
    public boolean isLastPage = false;
    public int TOTAL_PAGES = 14;
    PropertyAdapter propertyAdapter;
    LinearLayoutManager linearLayoutManager;
    String filter_type = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertylist);
        re_property = findViewById(R.id.re_property);
        loaderlayout = findViewById(R.id.loaderlayout);
        refres = findViewById(R.id.refres);
        ll_short = findViewById(R.id.ll_short);
        l_nodata = findViewById(R.id.l_nodata);
        ll_filter = findViewById(R.id.ll_filter);

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(PropertylistActivity.this);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Property List");
//        Log.i("onClick", "onClick: "+configuration+" "+Constant.property_facing+" "+Furnished);



        refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_property();
                refres.setRefreshing(false);
            }
        });
        linearLayoutManager = new LinearLayoutManager(PropertylistActivity.this);
        re_property.setLayoutManager(linearLayoutManager);
        propertyAdapter = new PropertyAdapter(PropertylistActivity.this);
        re_property.setAdapter(propertyAdapter);


        ll_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (property.equalsIgnoreCase("Commercial")) {
                    showBottomCommfilter(PropertylistActivity.this);
                } else {
                    showBottomresifilter(PropertylistActivity.this);
                }
//                Intent i=new Intent(PropertylistActivity.this,SearchpropertyActivity.class);
//                startActivity(i);
            }
        });
        re_property.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentpage += 1;
                get_secproperty();

            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        ll_short.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog1(PropertylistActivity.this);
            }
        });
        get_property();

    }


    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }


    private void get_property() {


//        Log.i("get_property", "get_property: "+startpricerange+" "+endpricerange);
        propertyAdapter.clearall();
        showLoader();
        currentpage = 1;
//

//
//        Log.i("get_property", "get_property: "+SavePref.getUserId()+ " short_by="+ filter_type+ " property_type="+ property+ " property_for="+ SearchlookingTo
//                + " property_sub_type="+Typesofproperty+ " property_state="+ savePref.getstate()+ " property_city="+
//                        savePref.getcity()+ " property_area="+
//                savePref.getArea()+ " property_configuration="+ configuration+ " furnishing_detail="+ Furnished+ " floor_detail="
//                + Floordetail+ " available_for="+ Avaliablefor+ " price_detail="+pricerange+ " price_value_one="
//                +startpricerange+ " price_value_two="+endpricerange+ " property_facing="+property_facing+ " Page="+currentpage);

        //   Log.i("get_property", "get_property: "+SavePref.getUserId()+"   "+ filter_type+"   " +property+"   "+SearchlookingTo+"    "+Typesofproperty);
        retrofitService.get_property(SavePref.getUserId(), "", filter_type, property, SearchlookingTo, Typesofproperty, savePref.getstate(), savePref.getcity(),
                savePref.getArea(), configuration, Furnished, Floordetail, Avaliablefor, pricerange,startpricerange,endpricerange,property_facing, currentpage).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {

                try {
                    if (response.body().getJsonData().size() == 0) {
                        l_nodata.setVisibility(View.VISIBLE);
                    } else {
                        l_nodata.setVisibility(View.GONE);
                    }
                    //   re_property.setAdapter(new PropertyAdapter(PropertylistActivity.this, response.body().getJsonData()));

                    propertyAdapter.addAll(response.body().getJsonData());

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


    private void get_secproperty() {
        showLoader();


        retrofitService.get_property(SavePref.getUserId(), "", filter_type, property, SearchlookingTo, Typesofproperty, savePref.getstate(), savePref.getcity(),
                savePref.getArea(), configuration, Furnished, Floordetail, Avaliablefor, pricerange,startpricerange,endpricerange,property_facing, currentpage).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {

                try {
                    //   re_property.setAdapter(new PropertyAdapter(PropertylistActivity.this, response.body().getJsonData()));
                    isLoading = false;
                    propertyAdapter.addAll(response.body().getJsonData());

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


    public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
        PropertyAdapter.ViewHolder viewHolder;
        Context mContext;
        ArrayList<GetProperty_Inner> getProperty_inners = new ArrayList<>();


        public PropertyAdapter(Context context, List<GetProperty_Inner> getProperty_inners) {
            this.mContext = context;
            this.getProperty_inners = (ArrayList<GetProperty_Inner>) getProperty_inners;
        }

        public PropertyAdapter(PropertylistActivity context) {
            this.mContext = context;

        }


        @Override
        public PropertyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_property_item, parent, false);
            viewHolder = new PropertyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull PropertyAdapter.ViewHolder holder, final int position) {

            try {
                if (getProperty_inners.get(position).getProperty_sold().equalsIgnoreCase("0")){

                    holder.im_sold.setVisibility(View.GONE);
                }else{
//                    Toast.makeText(mContext, "hii", Toast.LENGTH_SHORT).show();
                    holder.im_sold.setVisibility(View.VISIBLE);
                }

                holder.tv_name.setText(getProperty_inners.get(position).getPropertyTitle());
                if (getProperty_inners.get(position).getPropertyArea().equalsIgnoreCase(getProperty_inners.get(position).getPropertyCity())) {
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyCity() + " , " + getProperty_inners.get(position).getPropertyState());
                } else {
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyArea() + " , " + getProperty_inners.get(position).getPropertyCity() + " , " + getProperty_inners.get(position).getPropertyState());
                }

//                Log.i("onBindViewHolder", "onBindViewHolder: "+getProperty_inners.get(position).getProperty_sold());



//                holder.tv_startfrom.setText("₹" + getProperty_inners.get(position).getProperty_final_price());

                int firstcheck= Integer.parseInt(getProperty_inners.get(position).getProperty_final_price());

                if (firstcheck >= 10000000) {
                    DecimalFormat df = new DecimalFormat("#.#######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                    String out = df.format(lakhs);
                    holder.tv_startfrom.setText("₹ "+out+" Cr");
                } else if (firstcheck >= 100000) {
                    DecimalFormat df = new DecimalFormat("#.######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                    String out = df.format(lakhs);
                    holder.tv_startfrom.setText("₹ "+out+" Lac");
                }else  {
                    holder.tv_startfrom.setText("₹ "+firstcheck+"");
                }
                holder.tv_area.setText(getProperty_inners.get(position).getAreaDetail() + " " + getProperty_inners.get(position).getAreaUnitDetail());

                if (getProperty_inners.get(position).getFav_user().equalsIgnoreCase("1")) {
                    Glide.with(mContext)
                            .load(mContext.getResources().getDrawable(R.drawable.favourite))
                            .into(holder.im_fav);
                } else {
                    Glide.with(mContext)
                            .load(mContext.getResources().getDrawable(R.drawable.unfavourite))
                            .into(holder.im_fav);
                }
                Glide.with(mContext)
                        .load(getProperty_inners.get(position).getProductImage())
                        .placeholder(R.drawable.no_image)
                        .into(holder.im_top);


                holder.im_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_fav(getProperty_inners.get(position).getPropertyId(), getProperty_inners.get(position), holder.im_fav);
                    }
                });
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(PropertylistActivity.this, PropertydetailActivity.class);
                        i.putExtra("p_id", getProperty_inners.get(position).getPropertyId());
                        startActivity(i);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return getProperty_inners.size();
        }

        public void addAll(List<GetProperty_Inner> getProperty_inners) {
            for (GetProperty_Inner getProperty_inner : getProperty_inners) {
                add(getProperty_inner);
            }
        }

        private void add(GetProperty_Inner getProperty_inner) {
            this.getProperty_inners.add(getProperty_inner);
            notifyDataSetChanged();
        }

        public void clearall() {
            getProperty_inners=new ArrayList<>();
            notifyDataSetChanged();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name, tv_address, tv_area, tv_startfrom;
            LinearLayout card;
            RoundedImageView im_top;
            ImageView im_fav;
            RoundedImageView im_sold;

            ViewHolder(View itemView) {
                super(itemView);
                im_sold = itemView.findViewById(R.id.im_sold);
                tv_name = itemView.findViewById(R.id.tv_name);
                tv_address = itemView.findViewById(R.id.tv_address);
                card = itemView.findViewById(R.id.card);
                im_top = itemView.findViewById(R.id.im_top);
                im_fav = itemView.findViewById(R.id.im_fav);
                tv_area = itemView.findViewById(R.id.tv_area);
                tv_startfrom = itemView.findViewById(R.id.tv_startfrom);

            }

        }


        private void add_fav(String propertyId, GetProperty_Inner getProperty_inner, ImageView im_fav) {
            showLoader();
            retrofitService.add_fav(SavePref.getUserId(), propertyId).enqueue(new Callback<FavModel>() {
                @Override
                public void onResponse(Call<FavModel> call, Response<FavModel> response) {

                    try {
                        Toast.makeText(PropertylistActivity.this, "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        if (response.body().getJsonData().get(0).getSuccess().equalsIgnoreCase("1")) {

                            if (response.body().getJsonData().get(0).getFid().equalsIgnoreCase("0")) {
                                getProperty_inner.setFav_user("0");
                            } else {
                                getProperty_inner.setFav_user("1");
                            }
                            //   notifyDataSetChanged();
                            if (getProperty_inner.getFav_user().equalsIgnoreCase("1")) {
                                Glide.with(mContext)
                                        .load(mContext.getResources().getDrawable(R.drawable.favourite))
                                        .into(im_fav);
                            } else {
                                Glide.with(mContext)
                                        .load(mContext.getResources().getDrawable(R.drawable.unfavourite))
                                        .into(im_fav);
                            }
                            //get_property();
                        } else {

                        }
                        hideLoader();
                    } catch (Exception e) {
                        hideLoader();
                    }
                }

                @Override
                public void onFailure(Call<FavModel> call, Throwable t) {
                    hideLoader();
                    t.printStackTrace();

                }
            });
        }

    }

    public void showBottomresifilter(final Context context) {

        TextView tv_add, tv_rent, tv_cell, tv_home, tv_apartment, tv_plot, tv_land, tv_search;
        EditText et_fname;
        LinearLayout ll_add, ll_bottomconf,ll_avaliable,ll_facing;
        RangeSlider continuousRangeSlider;
        TextView tv_firstrange, tv_secrange;
        RelativeLayout r_location;
        ArrayList<String> selectedApps = new ArrayList<>();
        LinearLayout ll_flor;
        TextView tv_1rk, tv_1bhk, tv_2bhk, tv_3bhk, tv_4bhk, tv_5bhk, tv_other, tv_unfur, tv_semifur, tv_fur, tv_family, tv_bechlor, tv_avother,tv_north,tv_south,tv_east,tv_west;;

        final BottomSheetDialog view = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        view.setCanceledOnTouchOutside(false);
        view.setCancelable(false);
        view.setContentView(R.layout.fragment_searchresidental);
        continuousRangeSlider = view.findViewById(R.id.continuousRangeSlider);
        RecyclerView recycler_conf=view.findViewById(R.id.recycler_conf);
        RecyclerView re_furnishing=view.findViewById(R.id.re_furnishing);
        RecyclerView re_facing=view.findViewById(R.id.re_facing);

        recycler_conf.setLayoutManager(new GridLayoutManager(PropertylistActivity.this,4));
        re_furnishing.setLayoutManager(new GridLayoutManager(PropertylistActivity.this,2));
        re_facing.setLayoutManager(new GridLayoutManager(PropertylistActivity.this,2));
        ArrayList<MyItem> confiarraylist=new ArrayList<>();

        confiarraylist.add(new MyItem("1 RK","Configuration"));
        confiarraylist.add(new MyItem("1 BHK","Configuration"));
        confiarraylist.add(new MyItem("2 BHK","Configuration"));
        confiarraylist.add(new MyItem("3 BHK","Configuration"));
        confiarraylist.add(new MyItem("4 BHK","Configuration"));
        confiarraylist.add(new MyItem("5 BHK","Configuration"));
        confiarraylist.add(new MyItem("Other","Configuration"));
        recycler_conf.setAdapter(new ConfigAdapter(PropertylistActivity.this,confiarraylist,"Configuration"));

        ArrayList<MyItem> furnishinglist=new ArrayList<>();

        furnishinglist.add(new MyItem("Unfurnished","furnishing"));
        furnishinglist.add(new MyItem("Semi Furnished","furnishing"));
        furnishinglist.add(new MyItem("Furnished","furnishing"));
        re_furnishing.setAdapter(new ConfigAdapter(PropertylistActivity.this,furnishinglist,"furnishing"));

        ArrayList<MyItem> facinglist=new ArrayList<>();

        facinglist.add(new MyItem("North","facing"));
        facinglist.add(new MyItem("South","facing"));
        facinglist.add(new MyItem("East","facing"));
        facinglist.add(new MyItem("West","facing"));
        re_facing.setAdapter(new ConfigAdapter(PropertylistActivity.this,facinglist,"facing"));


        et_loc = view.findViewById(R.id.et_loc);
        ll_avaliable = view.findViewById(R.id.ll_avaliable);
        tv_search = view.findViewById(R.id.tv_search);
        et_fname = view.findViewById(R.id.et_fname);
        r_location = view.findViewById(R.id.r_location);
        tv_add = view.findViewById(R.id.tv_add);
        ll_add = view.findViewById(R.id.ll_add);
        tv_rent = view.findViewById(R.id.tv_rent);
        ll_flor = view.findViewById(R.id.ll_flor);
        ll_facing = view.findViewById(R.id.ll_facing);
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
        et_loc.setText(Constant.location);

        tv_north = view.findViewById(R.id.tv_north);
        tv_south = view.findViewById(R.id.tv_south);
        tv_east = view.findViewById(R.id.tv_east);
        tv_west = view.findViewById(R.id.tv_west);

        if (Constant.property_facing.equalsIgnoreCase("North")) {
//            Constant.property_facing="North";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Constant.property_facing.equalsIgnoreCase("East")) {
//            Constant.property_facing="East";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Constant.property_facing.equalsIgnoreCase("West")) {
//            Constant.property_facing="West";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        }else if (Constant.property_facing.equalsIgnoreCase("South")) {
//            Constant.property_facing="South";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        }

        if (Furnished.equalsIgnoreCase("Unfurnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Furnished.equalsIgnoreCase("Semi Furnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Furnished.equalsIgnoreCase("Furnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
        }

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



        if (configuration.equalsIgnoreCase("1 RK")) {
            tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (configuration.equalsIgnoreCase("1 Bhk")) {
            tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (configuration.equalsIgnoreCase("2 Bhk")) {
            tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (configuration.equalsIgnoreCase("3 Bhk")) {
            tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (configuration.equalsIgnoreCase("4 Bhk")) {
            tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (configuration.equalsIgnoreCase("5 Bhk")) {
            tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (configuration.equalsIgnoreCase("Other")) {
            tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
        }


        if (Avaliablefor.equalsIgnoreCase("Family")) {
            tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Avaliablefor.equalsIgnoreCase("Bachelor")) {
            tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Avaliablefor.equalsIgnoreCase("Other")) {
            tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
        }


        if (Furnished.equalsIgnoreCase("Unfurnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Furnished.equalsIgnoreCase("Semi Furnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Furnished.equalsIgnoreCase("Furnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
        }


        if (SearchlookingTo.equalsIgnoreCase("Rent")) {
            ll_avaliable.setVisibility(View.VISIBLE);
            continuousRangeSlider.setStepSize(Float.parseFloat("5000.0"));
            continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
            continuousRangeSlider.setValueTo(Float.parseFloat("1000000.0"));
            continuousRangeSlider.setValues(Float.parseFloat(startpricerange), Float.parseFloat(endpricerange));
            tv_firstrange.setText(startpricerangetxt + "");
            tv_secrange.setText(endpricerangetxt+"");
            ll_bottomconf.setVisibility(View.VISIBLE);
            ll_facing.setVisibility(View.VISIBLE);
            tv_land.setVisibility(View.GONE);
            tv_plot.setVisibility(View.GONE);
            tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            if (Typesofproperty.equalsIgnoreCase("Home")) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_facing.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.GONE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.VISIBLE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            }
        } else {

            ll_avaliable.setVisibility(View.GONE);
            ll_facing.setVisibility(View.VISIBLE);
            continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
            continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
            continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
            continuousRangeSlider.setValues(Float.parseFloat(startpricerange), Float.parseFloat(endpricerange));
            tv_firstrange.setText(startpricerangetxt + "");
            tv_secrange.setText(endpricerangetxt+"");
            ll_bottomconf.setVisibility(View.VISIBLE);
            tv_land.setVisibility(View.VISIBLE);
            tv_plot.setVisibility(View.VISIBLE);
            tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            if (Typesofproperty.equalsIgnoreCase("Home")) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.GONE);
                ll_facing.setVisibility(View.VISIBLE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else if (Typesofproperty.equalsIgnoreCase("Apartment")) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_facing.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.VISIBLE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else if (Typesofproperty.equalsIgnoreCase("Plot")) {
                ll_bottomconf.setVisibility(View.GONE);
                ll_facing.setVisibility(View.GONE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else if (Typesofproperty.equalsIgnoreCase("Land")) {
                ll_bottomconf.setVisibility(View.GONE);
                ll_facing.setVisibility(View.GONE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            }
        }

        continuousRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

//                Log.i("onStartTrackingTouch", "onStartTrackingTouch: "+slider.getValues());
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
//                Log.i("onStartTrackingTouch", "onStopTrackingTouch: " + slider.getValues());
                String currentString = slider.getValues().toString().replace("[", "").replace("]", "");
                StringTokenizer tokens = new StringTokenizer(currentString, ",");
                String first = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();

                float firstNumber = Float.parseFloat(first);
                float secnumber = Float.parseFloat(second);


                int firstcheck = Integer.parseInt(String.format("%.0f", firstNumber));

                if (firstcheck >= 10000000) {
                    DecimalFormat df = new DecimalFormat("#.#######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                    String out = df.format(lakhs);

                    tv_firstrange.setText("₹ " + out + " Cr");
                } else if (firstcheck >= 100000) {
//                    firstcheck = (firstcheck / 100000);
//
//                    tv_firstrange.setText("₹ "+firstcheck+" Lac");

                    DecimalFormat df = new DecimalFormat("#.######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                    String out = df.format(lakhs);

                    tv_firstrange.setText("₹ " + out + " Lac");
                } else if (firstcheck >= 10000) {
                    //     firstcheck = (firstcheck / 10000);
                    tv_firstrange.setText("₹ " + firstcheck + "");
                } else {
                    tv_firstrange.setText("₹ " + firstcheck + "");
                }

                int seccheck = Integer.parseInt(String.format("%.0f", secnumber));
                if (seccheck >= 10000000) {
                    DecimalFormat df = new DecimalFormat("#.#######");
                    double lakhs = Double.parseDouble(String.valueOf(seccheck)) / 10000000;
                    String out = df.format(lakhs);

                    tv_secrange.setText("₹ " + out + " Cr");
                } else if (seccheck >= 100000) {

                    DecimalFormat df = new DecimalFormat("#.######");
                    double lakhs = Double.parseDouble(String.valueOf(seccheck)) / 100000;
                    String out = df.format(lakhs);

                    tv_secrange.setText("₹ " + out + " Lac");
//                    seccheck = (seccheck / 100000);
//
//                    tv_secrange.setText("₹ "+seccheck+" Lac");
                } else if (seccheck >= 10000) {
//                    seccheck = (seccheck / 10000);
                    tv_secrange.setText("₹ " + seccheck + "");
                } else {
//                    seccheck = (seccheck / 10000);
                    tv_secrange.setText("₹ " + seccheck + "");
                }

//                tv_secrange.setText(String.format("%.0f", firstNumber)+"");

            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  if (validation()){
                property = "Residential";
                Floordetail = et_fname.getText().toString();
                startpricerangetxt=tv_firstrange.getText().toString();
                endpricerangetxt=tv_secrange.getText().toString();

                view.dismiss();
                configuration="";
                Constant.property_facing="";
                Furnished="";
                String currentString=continuousRangeSlider.getValues().toString().replace("[","").replace("]","");
                StringTokenizer tokens = new StringTokenizer(currentString, ",");
                String first = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();

                float firstNumber = Float.parseFloat(first);
                float secnumber = Float.parseFloat(second);

                startpricerange=String.format("%.0f", firstNumber);
                endpricerange=String.format("%.0f", secnumber);

                HashMap<String, List<String>> groupedData = new HashMap<>();

                // Iterate through the original HashMap and split the data by value
                for (Map.Entry<String, String> entry : Constant.allchekhashmap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    // Check if the value already exists in the groupedData HashMap
                    if (groupedData.containsKey(value)) {
                        // If it exists, add the key to the existing list
                        groupedData.get(value).add(key);
                    } else {
                        // If it doesn't exist, create a new list and add the key to it
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
                        Constant.property_facing=keys.toString().replace("[","").replace("]","").replace(", ",",");
                    }else if (value.equalsIgnoreCase("furnishing")){
                        Furnished=keys.toString().replace("[","").replace("]","").replace(", ",",");;
                    }

//                    System.out.println("Value: " + value);
//                    System.out.println("Keys: " + keys);
                }


                get_property();
//                    Intent i=new Intent(PropertylistActivity.this, PropertylistActivity.class);
//                    startActivity(i);

                //  }

            }
        });

        tv_1rk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "1 RK";
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });
        tv_1bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "1 BHK";
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });
        tv_2bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "2 BHK";
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });
        tv_3bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "3 BHK";
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });
        tv_4bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "4 BHK";
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });
        tv_5bhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "5 BHK";
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });
        tv_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuration = "Other";
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));

            }
        });

        tv_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Avaliablefor = "Family";
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });
        tv_bechlor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Avaliablefor = "Bachelor";
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_avother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Avaliablefor = "Other";
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            }
        });
        tv_unfur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Unfurnished";
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });
        tv_semifur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Semi Furnished";
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });
        tv_fur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Furnished";
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            }
        });

        tv_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuousRangeSlider.setStepSize(Float.parseFloat("5000.0"));
                continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                continuousRangeSlider.setValueTo(Float.parseFloat("1000000.0"));
                continuousRangeSlider.setValues(0.0f, 1000000.0f);
                tv_firstrange.setText("₹ " + 0 + "");
                tv_secrange.setText("₹ 10 Lac");

                ll_bottomconf.setVisibility(View.VISIBLE);
                tv_land.setVisibility(View.GONE);
                tv_plot.setVisibility(View.GONE);
                SearchlookingTo = "Rent";
                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                Typesofproperty = "Home";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
                continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
                continuousRangeSlider.setValues(0.0f, 5000000.0f);

                tv_firstrange.setText("₹ " + 0 + "");
                tv_secrange.setText("₹ 50 Lac");

                ll_bottomconf.setVisibility(View.VISIBLE);
                tv_land.setVisibility(View.VISIBLE);
                tv_plot.setVisibility(View.VISIBLE);
                SearchlookingTo = "Buy";
                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                Typesofproperty = "Home";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));

                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });
        tv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.GONE);

                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                Typesofproperty = "Home";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));


                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        tv_apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.VISIBLE);
                Typesofproperty = "Apartment";
                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });


        tv_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Typesofproperty = "Plot";
                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                ll_bottomconf.setVisibility(View.GONE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });


        tv_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ll_bottomconf.setVisibility(View.GONE);
                Typesofproperty = "Land";
                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));

                tv_1rk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_1bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_2bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_3bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_4bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_5bhk.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_other.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_family.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_bechlor.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_avother.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });

        if (selectedApps.size() == 0) {
            ll_add.setVisibility(View.GONE);
        } else {
            ll_add.setVisibility(View.VISIBLE);
        }
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PropertylistActivity.this, MapplaceActivity.class);
                i.putExtra("abs", selectedApps);
                i.putExtra("address", savePref.getAddress());
                startActivityForResult(i, 1004);
            }
        });

        r_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PropertylistActivity.this, MainSelectlocationActivity.class);

                i.putExtra("abs", selectedApps);
                i.putExtra("address", savePref.getAddress());
                startActivityForResult(i, 1004);
            }
        });
        et_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PropertylistActivity.this, MainSelectlocationActivity.class);
                i.putExtra("abs", selectedApps);
                i.putExtra("address", savePref.getAddress());
                startActivityForResult(i, 1004);
            }
        });

//        TextView savebtn = bottomSheetDialog.findViewById(R.id.saveBtn);
//        TextView t_clear = bottomSheetDialog.findViewById(R.id.t_clear);


//        savebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomSheetDialog.dismiss();
//            }
//        });


        view.show();
    }


    public void showBottomCommfilter(final Context context) {
        LinearLayout ll_bottomconf,ll_facing;
        TextView tv_add, tv_rent, tv_cell, tv_home, tv_apartment, tv_plot, tv_land, tv_search,tv_north,tv_south,tv_east,tv_west;

        LinearLayout ll_add, ll_furni;
        RangeSlider continuousRangeSlider;
        RelativeLayout r_location;
        ArrayList<String> selectedApps = new ArrayList<>();
        TextView tv_firstrange, tv_secrange;

        LinearLayout ll_flor;
        TextView tv_unfur, tv_semifur, tv_fur;

        EditText et_fname;
        final BottomSheetDialog view = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        view.setCanceledOnTouchOutside(false);
        view.setCancelable(false);
        view.setContentView(R.layout.fragment_searchcomm);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        RecyclerView re_furnishing=view.findViewById(R.id.re_furnishing);
        RecyclerView re_facing=view.findViewById(R.id.re_facing);

        re_furnishing.setLayoutManager(new GridLayoutManager(PropertylistActivity.this,2));
        re_facing.setLayoutManager(new GridLayoutManager(PropertylistActivity.this,2));
        ArrayList<MyItem> confiarraylist=new ArrayList<>();



        ArrayList<MyItem> furnishinglist=new ArrayList<>();

        furnishinglist.add(new MyItem("Unfurnished","furnishing"));
        furnishinglist.add(new MyItem("Semi Furnished","furnishing"));
        furnishinglist.add(new MyItem("Furnished","furnishing"));
        re_furnishing.setAdapter(new ConfigAdapter(PropertylistActivity.this,furnishinglist,"furnishing"));

        ArrayList<MyItem> facinglist=new ArrayList<>();

        facinglist.add(new MyItem("North","facing"));
        facinglist.add(new MyItem("South","facing"));
        facinglist.add(new MyItem("East","facing"));
        facinglist.add(new MyItem("West","facing"));
        re_facing.setAdapter(new ConfigAdapter(PropertylistActivity.this,facinglist,"facing"));

        continuousRangeSlider = view.findViewById(R.id.continuousRangeSlider);
        r_location = view.findViewById(R.id.r_location);
        tv_search = view.findViewById(R.id.tv_search);
        tv_add = view.findViewById(R.id.tv_add);
        ll_furni = view.findViewById(R.id.ll_furni);
        ll_add = view.findViewById(R.id.ll_add);
        et_loc1 = view.findViewById(R.id.et_loc);
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
        ll_facing = view.findViewById(R.id.ll_facing);

        tv_north = view.findViewById(R.id.tv_north);
        tv_south = view.findViewById(R.id.tv_south);
        tv_east = view.findViewById(R.id.tv_east);
        tv_west = view.findViewById(R.id.tv_west);

        et_fname.setText(Floordetail);
        et_loc1.setText(Constant.location);

        if (Constant.property_facing.equalsIgnoreCase("North")) {
//            Constant.property_facing="North";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Constant.property_facing.equalsIgnoreCase("East")) {
//            Constant.property_facing="East";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Constant.property_facing.equalsIgnoreCase("West")) {
//            Constant.property_facing="West";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
        }else if (Constant.property_facing.equalsIgnoreCase("South")) {
//            Constant.property_facing="South";
            tv_north.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_east.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_west.setBackground(getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_south.setBackground(getResources().getDrawable(R.drawable.btnbgselect));
        }

        if (Furnished.equalsIgnoreCase("Unfurnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Furnished.equalsIgnoreCase("Semi Furnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
        } else if (Furnished.equalsIgnoreCase("Furnished")) {
            tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
        }


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


        if (SearchlookingTo.equalsIgnoreCase("Rent")) {
            continuousRangeSlider.setStepSize(Float.parseFloat("5000.0"));
            continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
            continuousRangeSlider.setValueTo(Float.parseFloat("1000000.0"));
            continuousRangeSlider.setValues(0.0f, 1000000.0f);
            tv_firstrange.setText("₹ " + 0 + "");
            tv_secrange.setText("₹ 10 Lac");
            ll_bottomconf.setVisibility(View.VISIBLE);
            tv_land.setVisibility(View.GONE);
            tv_plot.setVisibility(View.GONE);
            ll_facing.setVisibility(View.VISIBLE);
            tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            if (Typesofproperty.equalsIgnoreCase("Office")) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.GONE);
                ll_facing.setVisibility(View.VISIBLE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else {
                ll_facing.setVisibility(View.VISIBLE);
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.VISIBLE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            }
        } else {



            continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
            continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
            continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
            continuousRangeSlider.setValues(0.0f, 5000000.0f);

            tv_firstrange.setText("₹ " + 0 + "");
            tv_secrange.setText("₹ 50 Lac");

            ll_bottomconf.setVisibility(View.VISIBLE);
            tv_land.setVisibility(View.VISIBLE);
            tv_plot.setVisibility(View.VISIBLE);
            tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            if (Typesofproperty.equalsIgnoreCase("Office")) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.GONE);


                ll_facing.setVisibility(View.VISIBLE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else if (Typesofproperty.equalsIgnoreCase("Shop")) {
                ll_bottomconf.setVisibility(View.VISIBLE);
                ll_flor.setVisibility(View.VISIBLE);
                ll_facing.setVisibility(View.VISIBLE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else if (Typesofproperty.equalsIgnoreCase("Plot")) {
                ll_bottomconf.setVisibility(View.GONE);
                ll_facing.setVisibility(View.GONE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            } else if (Typesofproperty.equalsIgnoreCase("Land")) {
                ll_bottomconf.setVisibility(View.GONE);
                ll_facing.setVisibility(View.GONE);
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            }
        }





//        continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
//        continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
//        continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
//        continuousRangeSlider.setValues(0.0f, 5000000.0f);

//        tv_firstrange.setText("₹ " + 0 + "");
//        tv_secrange.setText("₹ 50 Lac");

        continuousRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
//                Log.i("onStartTrackingTouch", "onStartTrackingTouch: "+slider.getValues());
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                String currentString = slider.getValues().toString().replace("[", "").replace("]", "");
                StringTokenizer tokens = new StringTokenizer(currentString, ",");
                String first = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();
                float firstNumber = Float.parseFloat(first);
                float secnumber = Float.parseFloat(second);
                int firstcheck = Integer.parseInt(String.format("%.0f", firstNumber));
                if (firstcheck >= 10000000) {
                    DecimalFormat df = new DecimalFormat("#.#######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                    String out = df.format(lakhs);
                    tv_firstrange.setText("₹ " + out + " Cr");
                } else if (firstcheck >= 100000) {
//                    firstcheck = (firstcheck / 100000);
//                    tv_firstrange.setText("₹ "+firstcheck+" Lac");
                    DecimalFormat df = new DecimalFormat("#.######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                    String out = df.format(lakhs);
                    tv_firstrange.setText("₹ " + out + " Lac");
                } else if (firstcheck >= 10000) {
                    //     firstcheck = (firstcheck / 10000);
                    tv_firstrange.setText("₹ " + firstcheck + "");
                } else {
                    tv_firstrange.setText("₹ " + firstcheck + "");
                }

                int seccheck = Integer.parseInt(String.format("%.0f", secnumber));
                if (seccheck >= 10000000) {
                    DecimalFormat df = new DecimalFormat("#.#######");
                    double lakhs = Double.parseDouble(String.valueOf(seccheck)) / 10000000;
                    String out = df.format(lakhs);

                    tv_secrange.setText("₹ " + out + " Cr");
                } else if (seccheck >= 100000) {

                    DecimalFormat df = new DecimalFormat("#.######");
                    double lakhs = Double.parseDouble(String.valueOf(seccheck)) / 100000;
                    String out = df.format(lakhs);

                    tv_secrange.setText("₹ " + out + " Lac");
//                    seccheck = (seccheck / 100000);
//                    tv_secrange.setText("₹ "+seccheck+" Lac");
                } else if (seccheck >= 10000) {
//                    seccheck = (seccheck / 10000);
                    tv_secrange.setText("₹ " + seccheck + "");
                } else {
//                    seccheck = (seccheck / 10000);
                    tv_secrange.setText("₹ " + seccheck + "");
                }
//                tv_secrange.setText(String.format("%.0f", firstNumber)+"");
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.dismiss();
                String currentString=continuousRangeSlider.getValues().toString().replace("[","").replace("]","");
                StringTokenizer tokens = new StringTokenizer(currentString, ",");
                String first = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();

                float firstNumber = Float.parseFloat(first);
                float secnumber = Float.parseFloat(second);
                configuration="";
                Constant.property_facing="";
                Furnished="";
                startpricerange=String.format("%.0f", firstNumber);
                endpricerange=String.format("%.0f", secnumber);

                HashMap<String, List<String>> groupedData = new HashMap<>();

                // Iterate through the original HashMap and split the data by value
                for (Map.Entry<String, String> entry : Constant.allchekhashmap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    // Check if the value already exists in the groupedData HashMap
                    if (groupedData.containsKey(value)) {
                        // If it exists, add the key to the existing list
                        groupedData.get(value).add(key);
                    } else {
                        // If it doesn't exist, create a new list and add the key to it
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
                        Constant.property_facing=keys.toString().replace("[","").replace("]","").replace(", ",",");
                    }else if (value.equalsIgnoreCase("furnishing")){
                        Furnished=keys.toString().replace("[","").replace("]","").replace(", ",",");;
                    }

//                    System.out.println("Value: " + value);
//                    System.out.println("Keys: " + keys);
                }

                get_property();
            }
        });
        tv_unfur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Unfurnished";
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });
        tv_semifur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Semi Furnished";
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
            }
        });
        tv_fur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Furnished = "Furnished";
                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
            }
        });

        tv_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                continuousRangeSlider.setStepSize(Float.parseFloat("5000.0"));
                continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                continuousRangeSlider.setValueTo(Float.parseFloat("1000000.0"));
                continuousRangeSlider.setValues(0.0f, 1000000.0f);
                tv_firstrange.setText("₹ " + 0 + "");
                tv_secrange.setText("₹ 10 Lac");


                ll_bottomconf.setVisibility(View.VISIBLE);
                tv_land.setVisibility(View.GONE);
                tv_plot.setVisibility(View.GONE);
                SearchlookingTo = "Rent";
                SearchlookingTo1 = "Rent";
                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                Typesofproperty = "Office";
                Typesofproperty1 = "Office";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });

        tv_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuousRangeSlider.setStepSize(Float.parseFloat("500000.0"));
                continuousRangeSlider.setValueFrom(Float.parseFloat("0.0"));
                continuousRangeSlider.setValueTo(Float.parseFloat("40000000.0"));
                continuousRangeSlider.setValues(0.0f, 5000000.0f);

                tv_firstrange.setText("₹ " + 0 + "");
                tv_secrange.setText("₹ 50 Lac");


                ll_bottomconf.setVisibility(View.VISIBLE);
                tv_land.setVisibility(View.VISIBLE);
                tv_plot.setVisibility(View.VISIBLE);
                SearchlookingTo = "Buy";
                SearchlookingTo1 = "Buy";
                Avaliablefor = "";
                Furnished = "";
                configuration = "";
                Typesofproperty = "Office";
                Typesofproperty1 = "Office";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_rent.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_cell.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));


                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

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
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Typesofproperty="Office";
                    Typesofproperty1="Office";
                    tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                    tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));



                    tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                }else{
                    ll_flor.setVisibility(View.VISIBLE);
                    ll_furni.setVisibility(View.GONE);
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    Typesofproperty="Office";
                    Typesofproperty1="Office";
                    tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                    tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));



                    tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                }



            }
        });

        tv_apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SearchlookingTo.equalsIgnoreCase("Rent")){
                    ll_bottomconf.setVisibility(View.VISIBLE);
                    ll_flor.setVisibility(View.VISIBLE);
                    Typesofproperty="Shop";
                    Typesofproperty1="Shop";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                    tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));


                    tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                }else{
                    ll_furni.setVisibility(View.GONE);
                    ll_flor.setVisibility(View.VISIBLE);
                    Typesofproperty="Shop";
                    Typesofproperty1="Shop";
                    Avaliablefor="";
                    Furnished="";
                    configuration="";
                    tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                    tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));


                    tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                    tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                }


            }
        });


        tv_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_furni.setVisibility(View.GONE);
                ll_flor.setVisibility(View.GONE);
//                ll_bottomconf.setVisibility(View.GONE);
//                ll_flor.setVisibility(View.VISIBLE);
                Typesofproperty="Plot";
                Typesofproperty1="Plot";
                Avaliablefor="";
                Furnished="";
                configuration="";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });


        tv_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_furni.setVisibility(View.GONE);
                ll_flor.setVisibility(View.GONE);
                Typesofproperty="Land";
                Typesofproperty1="Land";
                Avaliablefor="";
                Furnished="";
                configuration="";
                tv_home.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_apartment.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_plot.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_land.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgselect));


                tv_unfur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_semifur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));
                tv_fur.setBackground(PropertylistActivity.this.getResources().getDrawable(R.drawable.btnbgwhitestock));

            }
        });

        if (selectedApps.size() == 0) {
            ll_add.setVisibility(View.GONE);
        } else {
            ll_add.setVisibility(View.VISIBLE);
        }
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PropertylistActivity.this, MainSelectlocationActivity.class);
                i.putExtra("abs", selectedApps);
                i.putExtra("address", savePref.getAddress());
                startActivityForResult(i, 1004);
            }
        });

        r_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PropertylistActivity.this, MainSelectlocationActivity.class);
                i.putExtra("abs", selectedApps);
                i.putExtra("address", savePref.getAddress());
                startActivityForResult(i, 1004);
            }
        });
        et_loc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PropertylistActivity.this, MainSelectlocationActivity.class);
                i.putExtra("abs", selectedApps);
                i.putExtra("address", savePref.getAddress());
                startActivityForResult(i, 1004);
            }
        });

        view.show();
    }

    public void showBottomSheetDialog1(final Context context) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.CustomBottomSheetDialog);
        bottomSheetDialog.setContentView(R.layout.activity_bottomfilter2);


        final RadioGroup radioGroup_filter = bottomSheetDialog.findViewById(R.id.radioGroup_filter);

        TextView savebtn = bottomSheetDialog.findViewById(R.id.saveBtn);
        TextView t_clear = bottomSheetDialog.findViewById(R.id.t_clear);


        if (filter_type.equalsIgnoreCase("1")) {
            ((RadioButton) radioGroup_filter.getChildAt(0)).setChecked(true);
        } else if (filter_type.equalsIgnoreCase("2")) {
            ((RadioButton) radioGroup_filter.getChildAt(1)).setChecked(true);
        } else if (filter_type.equalsIgnoreCase("3")) {
            ((RadioButton) radioGroup_filter.getChildAt(2)).setChecked(true);
        } else if (filter_type.equalsIgnoreCase("4")) {
            ((RadioButton) radioGroup_filter.getChildAt(3)).setChecked(true);
        } else if (filter_type.equalsIgnoreCase("5")) {
            ((RadioButton) radioGroup_filter.getChildAt(4)).setChecked(true);
        }


        t_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disablefor1sec(v);
                radioGroup_filter.clearCheck();
//              radioGroup_price.clearCheck();
                Toast.makeText(context, "Currently, There is no filter applied. ", Toast.LENGTH_SHORT).show();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_type = "0";

                RadioButton rb = (RadioButton) radioGroup_filter.findViewById(radioGroup_filter.getCheckedRadioButtonId());
//                RadioButton rb1 = (RadioButton) radioGroup_price.findViewById(radioGroup_price.getCheckedRadioButtonId());

                int selectedId = radioGroup_filter.getCheckedRadioButtonId();
//                if (selectedId == R.id.rb_low){
//                    veg_price="1";
//                }else  if (selectedId == R.id.rb_high){
//                    veg_price="2";
//                }else

                if (selectedId == R.id.rb_new) {
                    filter_type = "1";
                } else if (selectedId == R.id.rb_low) {
                    filter_type = "2";
                } else if (selectedId == R.id.rb_high) {
                    filter_type = "3";
                } else if (selectedId == R.id.rb_sqlow) {
                    filter_type = "4";
                } else if (selectedId == R.id.rb_sqhigh) {
                    filter_type = "5";
                }


//
//                if (veg_type.equalsIgnoreCase("0") &&veg_price.equalsIgnoreCase("0")){
//                    ll_filter.setBackground(getResources().getDrawable(R.drawable.btnbgnewcolor));
//                    im_filter.setColorFilter(getResources().getColor(R.color.colorPrimary));
//                    tv_filter.setTextColor(getResources().getColor(R.color.colorPrimary));
//                    im_cross.setVisibility(View.GONE);
//
//                }else{
//                    ll_filter.setBackground(getResources().getDrawable(R.drawable.btnbgfilter));
//                    im_filter.setColorFilter(getResources().getColor(R.color.white));
//                    tv_filter.setTextColor(getResources().getColor(R.color.white));
//                    im_cross.setVisibility(View.VISIBLE);
//
//
//                }
                //  sendBroadcast(new Intent("data"));


                bottomSheetDialog.dismiss();
                get_property();

            }
        });


        bottomSheetDialog.show();
    }

//    @Override
//    public void onBackPressed() {
//        Intent i=new Intent(PropertylistActivity.this, NewHomeActivity.class);
//        startActivity(i);
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1004) {

            try {
                et_loc.setText(data.getStringExtra("address"));
                Constant.location=data.getStringExtra("address");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                et_loc1.setText(data.getStringExtra("address"));
                Constant.location=data.getStringExtra("address");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                getLocationFromAddress1(PropertylistActivity.this, data.getStringExtra("address"));
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
            return new ConfigAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ConfigAdapter.ViewHolder holder, int position) {


            holder.tv_checktxt.setText(confiarraylist.get(position).getItemName());

            String keyToCheck = confiarraylist.get(position).getItemName();
//                    String keyToCheck = "value2";
            if (Constant.allchekhashmap.containsKey(keyToCheck)) {

//                        String value = hashMap.get(keyToCheck);
                holder.tv_checktxt.setBackground(context.getDrawable(R.drawable.btnbgselect));
//                Constant.allchekhashmap.remove(confiarraylist.get(position).getItemName());
//                        Toast.makeText(context, "Done "+value, Toast.LENGTH_SHORT).show();
            } else {
                holder.tv_checktxt.setBackground(context.getDrawable(R.drawable.btnbgwhitestock));
//                        Toast.makeText(context, "not Done ", Toast.LENGTH_SHORT).show();
//                        hashMap.put("key2", "value2");
//                Constant.allchekhashmap.put(confiarraylist.get(position).getItemName(), confiarraylist.get(position).getCategory());
            }


            holder.tv_checktxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String keyToCheck = confiarraylist.get(position).getItemName();
//                    String keyToCheck = "value2";
                    if (Constant.allchekhashmap.containsKey(keyToCheck)) {

//                        String value = hashMap.get(keyToCheck);
                        holder.tv_checktxt.setBackground(context.getDrawable(R.drawable.btnbgwhitestock));
                        Constant.allchekhashmap.remove(confiarraylist.get(position).getItemName());
//                        Toast.makeText(context, "Done "+value, Toast.LENGTH_SHORT).show();
                    } else {
                        holder.tv_checktxt.setBackground(context.getDrawable(R.drawable.btnbgselect));
//                        Toast.makeText(context, "not Done ", Toast.LENGTH_SHORT).show();
//                        hashMap.put("key2", "value2");
                        Constant.allchekhashmap.put(confiarraylist.get(position).getItemName(), confiarraylist.get(position).getCategory());
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