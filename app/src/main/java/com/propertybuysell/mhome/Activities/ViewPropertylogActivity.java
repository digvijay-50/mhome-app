package com.propertybuysell.mhome.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.propertybuysell.mhome.ModelClasses.FavModel;
import com.propertybuysell.mhome.ModelClasses.GetProperty;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPropertylogActivity extends AppCompatActivity {

    RecyclerView re_property;
    LinearLayout loaderlayout;
    SavePref savePref;
    LinearLayout l_nodata;
    RetrofitService retrofitService;
    SwipeRefreshLayout refres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advocatelist);
        re_property = findViewById(R.id.re_property);
        loaderlayout = findViewById(R.id.loaderlayout);
        refres = findViewById(R.id.refres);
        l_nodata = findViewById(R.id.l_nodata);

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(ViewPropertylogActivity.this);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);

        headingtv.setText("Property Log");

        re_property.setLayoutManager(new LinearLayoutManager(ViewPropertylogActivity.this));
        refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_secproperty();
                refres.setRefreshing(false);
            }
        });

get_secproperty();

    }


    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }


    private void get_secproperty() {
        showLoader();


        retrofitService.get_user_view_property_log("2").enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {

                try {

                    re_property.setAdapter(new PropertyAdapter(ViewPropertylogActivity.this, response.body().getJsonData()));

                    //   re_property.setAdapter(new PropertyAdapter(PropertylistActivity.this, response.body().getJsonData()));


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
                holder.tv_name.setText(getProperty_inners.get(position).getPropertyTitle());
                if (getProperty_inners.get(position).getPropertyArea().equalsIgnoreCase(getProperty_inners.get(position).getPropertyCity())) {
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyCity() + " , " + getProperty_inners.get(position).getPropertyState());
                } else {
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyArea() + " , " + getProperty_inners.get(position).getPropertyCity() + " , " + getProperty_inners.get(position).getPropertyState());
                }

//                holder.tv_startfrom.setText("₹" + getProperty_inners.get(position).getProperty_final_price());

                int firstcheck = Integer.parseInt(getProperty_inners.get(position).getProperty_final_price());


                if (firstcheck >= 10000000) {
                    DecimalFormat df = new DecimalFormat("#.#######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                    String out = df.format(lakhs);

                    holder.tv_startfrom.setText("₹ " + out + " Cr");
                } else if (firstcheck >= 100000) {

                    DecimalFormat df = new DecimalFormat("#.######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                    String out = df.format(lakhs);
                    holder.tv_startfrom.setText("₹ " + out + " Lac");
                } else {
                    holder.tv_startfrom.setText("₹ " + firstcheck + "");
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

                        Intent i = new Intent(ViewPropertylogActivity.this, PropertydetailActivity.class);
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
            getProperty_inners = new ArrayList<>();
            notifyDataSetChanged();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name, tv_address, tv_area, tv_startfrom;
            LinearLayout card;
            RoundedImageView im_top;
            ImageView im_fav;

            ViewHolder(View itemView) {
                super(itemView);

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
                        Toast.makeText(ViewPropertylogActivity.this, "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
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


}