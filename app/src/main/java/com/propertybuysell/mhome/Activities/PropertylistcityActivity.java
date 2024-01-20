package com.propertybuysell.mhome.Activities;

import static com.propertybuysell.mhome.Activities.SplashActivity.disablefor1sec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.makeramen.roundedimageview.RoundedImageView;
import com.propertybuysell.mhome.ModelClasses.FavModel;
import com.propertybuysell.mhome.ModelClasses.GetProperty;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.Constant;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertylistcityActivity extends AppCompatActivity {

    RecyclerView re_property;
    LinearLayout loaderlayout;
    SavePref savePref;
    String property_configuration;
    RetrofitService retrofitService;
    EditText et_loc,et_loc1;

    SwipeRefreshLayout refres;

    LinearLayout l_nodata;
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
        setContentView(R.layout.activity_propertylisthome);
        re_property = findViewById(R.id.re_property);
        loaderlayout = findViewById(R.id.loaderlayout);
        refres = findViewById(R.id.refres);
        l_nodata = findViewById(R.id.l_nodata);

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(PropertylistcityActivity.this);

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


        property_configuration=getIntent().getStringExtra("property_configuration");

        refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_property();
                refres.setRefreshing(false);
            }
        });
        linearLayoutManager = new LinearLayoutManager(PropertylistcityActivity.this);
        re_property.setLayoutManager(linearLayoutManager);
        propertyAdapter = new PropertyAdapter(PropertylistcityActivity.this);
        re_property.setAdapter(propertyAdapter);

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
        retrofitService.get_home_property_city_filter(SavePref.getUserId(),property_configuration, currentpage).enqueue(new Callback<GetProperty>() {
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
        retrofitService.get_home_property_city_filter(SavePref.getUserId(),property_configuration, currentpage).enqueue(new Callback<GetProperty>() {
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
        ViewHolder viewHolder;
        Context mContext;
        ArrayList<GetProperty_Inner> getProperty_inners = new ArrayList<>();


        public PropertyAdapter(Context context, List<GetProperty_Inner> getProperty_inners) {
            this.mContext = context;
            this.getProperty_inners = (ArrayList<GetProperty_Inner>) getProperty_inners;
        }

        public PropertyAdapter(PropertylistcityActivity context) {
            this.mContext = context;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_property_item, parent, false);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

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
                        Intent i = new Intent(PropertylistcityActivity.this, PropertydetailActivity.class);
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
                        Toast.makeText(PropertylistcityActivity.this, "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
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
                getLocationFromAddress1(PropertylistcityActivity.this, data.getStringExtra("address"));
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