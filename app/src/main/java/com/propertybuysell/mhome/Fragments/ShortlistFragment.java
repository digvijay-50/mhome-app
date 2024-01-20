package com.propertybuysell.mhome.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.propertybuysell.mhome.Activities.ChangePasswordActivity;
import com.propertybuysell.mhome.Activities.EditUserProfileActivity;
import com.propertybuysell.mhome.Activities.PropertydetailActivity;
import com.propertybuysell.mhome.Activities.PropertylistActivity;
import com.propertybuysell.mhome.Activities.SettingDataActivity;
import com.propertybuysell.mhome.Activities.SplashActivity;
import com.propertybuysell.mhome.Adapters.PropertyAdapter;
import com.propertybuysell.mhome.ModelClasses.FavModel;
import com.propertybuysell.mhome.ModelClasses.GetProperty;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.ModelClasses.UpdateDetailModel;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.ModelClasses.UserProfileDetailModel;
import com.propertybuysell.mhome.ModelClasses.UserProfileModel;
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


public class ShortlistFragment extends Fragment {


    RetrofitService retrofitService;
    SavePref savePref;
    RecyclerView recycler;
    LinearLayout loaderlayout,l_nodata;



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


        View view = inflater.inflate(R.layout.activity_shortlist, container, false);

        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);


        loaderlayout = view.findViewById(R.id.loaderlayout);
        recycler = view.findViewById(R.id.recycler);
        l_nodata = view.findViewById(R.id.l_nodata);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

                get_fav();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(getActivity());


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        get_fav();
    }

    private void get_fav() {
        showLoader();

        retrofitService.get_fav(SavePref.getUserId()).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {

                try {

                    if (response.body().getJsonData().size()==0){
                        l_nodata.setVisibility(View.VISIBLE);
                    }else {
                        l_nodata.setVisibility(View.GONE);
                    }
                    recycler.setAdapter(new PropertyAdapter(getContext(),response.body().getJsonData()));



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




        @Override
        public PropertyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_property_item, parent, false);
            viewHolder = new PropertyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PropertyAdapter.ViewHolder holder, final int position) {

            try {
                holder.tv_name.setText(getProperty_inners.get(position).getPropertyTitle());
                if (getProperty_inners.get(position).getPropertyArea().equalsIgnoreCase(getProperty_inners.get(position).getPropertyCity())){
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyCity()+" , "+getProperty_inners.get(position).getPropertyState());
                }else{
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyArea()+" , "+getProperty_inners.get(position).getPropertyCity()+" , "+getProperty_inners.get(position).getPropertyState());
                }

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
                }                holder.tv_area.setText(getProperty_inners.get(position).getAreaDetail() + " " + getProperty_inners.get(position).getAreaUnitDetail());

//                if (getProperty_inners.get(position).getFav_user().equalsIgnoreCase("1")) {
                    Glide.with(mContext)
                            .load(mContext.getResources().getDrawable(R.drawable.favourite))
                            .into(holder.im_fav);
//                } else {
//                    Glide.with(mContext)
//                            .load(mContext.getResources().getDrawable(R.drawable.unfavourite))
//                            .into(holder.im_fav);
//                }
                Glide.with(mContext)
                        .load(getProperty_inners.get(position).getProductImage())
                        .placeholder(R.drawable.no_image)
                        .into(holder.im_top);


//                Log.i("onBindViewHolder", "onBindViewHolder: "+getProperty_inners.get(position).getProductImage());
                holder.im_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_fav(getProperty_inners.get(position).getPropertyId(), getProperty_inners, position);

                    }
                });
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(mContext, PropertydetailActivity.class);
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


        private void add_fav(String propertyId, ArrayList<GetProperty_Inner> getProperty_inner, int im_fav) {
            showLoader();
            retrofitService.add_fav(SavePref.getUserId(), propertyId).enqueue(new Callback<FavModel>() {
                @Override
                public void onResponse(Call<FavModel> call, Response<FavModel> response) {

                    try {
                        Toast.makeText(getContext(), "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                        if (response.body().getJsonData().get(0).getSuccess().equalsIgnoreCase("1")) {

//                            if (response.body().getJsonData().get(0).getFid().equalsIgnoreCase("0")) {
//                                getProperty_inner.setFav_user("0");
//                            } else {
//                                getProperty_inner.setFav_user("1");
//                            }

                            getProperty_inner.remove(im_fav);
                              notifyDataSetChanged();
//                            if (getProperty_inner.getFav_user().equalsIgnoreCase("1")) {
//                                Glide.with(mContext)
//                                        .load(mContext.getResources().getDrawable(R.drawable.favourite))
//                                        .into(im_fav);
//                            } else {
//                                Glide.with(mContext)
//                                        .load(mContext.getResources().getDrawable(R.drawable.unfavourite))
//                                        .into(im_fav);
//                            }
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