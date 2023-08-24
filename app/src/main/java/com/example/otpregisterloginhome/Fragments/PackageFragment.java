package com.example.otpregisterloginhome.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.otpregisterloginhome.Activities.PostpackagedetailActivity;
import com.example.otpregisterloginhome.ModelClasses.GetPostPackage_Inner;
import com.example.otpregisterloginhome.ModelClasses.GetPropertyPostPackage;
import com.example.otpregisterloginhome.ModelClasses.UserProfileDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileModel;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PackageFragment extends Fragment {


    RetrofitService retrofitService;
    SavePref savePref;
    LinearLayout loaderlayout, ll_purchase;
    RecyclerView recycler;
    TextView tv_numberpost, tv_remaining, tv_sdate, tv_edate, tv_price, tv_pakname;


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
        View view = inflater.inflate(R.layout.activity_package, container, false);
        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        loaderlayout = view.findViewById(R.id.loaderlayout);
        recycler = view.findViewById(R.id.recycler);
        tv_numberpost = view.findViewById(R.id.tv_numberpost);
        tv_remaining = view.findViewById(R.id.tv_remaining);
        tv_sdate = view.findViewById(R.id.tv_sdate);
        tv_edate = view.findViewById(R.id.tv_edate);
        tv_price = view.findViewById(R.id.tv_price);
        tv_pakname = view.findViewById(R.id.tv_pakname);
        ll_purchase = view.findViewById(R.id.ll_purchase);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(getActivity());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                get_property_post_package();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        get_property_post_package();
        getUserProfile();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        get_property_post_package();
    }


    private void get_property_post_package() {
        showLoader();
        retrofitService.get_property_post_package(savePref.getUserId()).enqueue(new Callback<GetPropertyPostPackage>() {
            @Override
            public void onResponse(Call<GetPropertyPostPackage> call, Response<GetPropertyPostPackage> response) {
                try {
                    recycler.setAdapter(new PostpackageAdapter(getContext(), response.body().getJsonData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<GetPropertyPostPackage> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }


    public class PostpackageAdapter extends RecyclerView.Adapter<PostpackageAdapter.ViewHolder> {
        ViewHolder viewHolder;
        Context mContext;
        ArrayList<GetPostPackage_Inner> getPostPackage_inners;


        public PostpackageAdapter(Context context, List<GetPostPackage_Inner> getPostPackage_inners) {
            this.mContext = context;
            this.getPostPackage_inners = (ArrayList<GetPostPackage_Inner>) getPostPackage_inners;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_postpackage_item1, parent, false);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            try {
                holder.tv_pakname.setText(getPostPackage_inners.get(position).getPropertyPostPackageName());
                holder.tv_price.setText("₹" + getPostPackage_inners.get(position).getPropertyPostPackagePrice());
                holder.tv_numberpost.setText("Number of Post : " + getPostPackage_inners.get(position).getNoOfPropery());
                holder.tv_duration.setText("Package Duration : " + getPostPackage_inners.get(position).getPackageDuration() + " Days");


                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), PostpackagedetailActivity.class);
                        i.putExtra("getPostPackage_inner",getPostPackage_inners.get(position));
                        startActivity(i);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return getPostPackage_inners.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_pakname, tv_price, tv_numberpost, tv_duration;
            CardView card;

            ViewHolder(View itemView) {
                super(itemView);
                tv_pakname = itemView.findViewById(R.id.tv_pakname);
                tv_price = itemView.findViewById(R.id.tv_price);
                card = itemView.findViewById(R.id.card);
                tv_numberpost = itemView.findViewById(R.id.tv_numberpost);
                tv_duration = itemView.findViewById(R.id.tv_duration);
            }
        }
    }


    private void getUserProfile() {
        showLoader();

        retrofitService.userProfile(SavePref.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                try {
                    final UserProfileModel userModel = response.body();

                    UserProfileDetailModel userDetailModel = userModel.getJsonData().get(0);

                    //   Log.i("onResponse", "onResponse: "+userDetailModel.getNo_of_propery());

                    if (userDetailModel.getProperty_post_package_name().equalsIgnoreCase("")) {
                        ll_purchase.setVisibility(View.GONE);
                    } else {
                        ll_purchase.setVisibility(View.VISIBLE);
                    }
                    tv_pakname.setText(userDetailModel.getProperty_post_package_name());
                    tv_numberpost.setText("Number of Post : " + userDetailModel.getNo_of_propery());
                    tv_remaining.setText("Remaining Post : " + userDetailModel.getRemaining_post());
                    tv_sdate.setText("Start Date : " + userDetailModel.getTxtsdate());
                    tv_edate.setText("End Date : " + userDetailModel.getTxtedate());
                    tv_price.setText("Price : ₹" + userDetailModel.getProperty_post_package_discount_price());
                    hideLoader();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

}