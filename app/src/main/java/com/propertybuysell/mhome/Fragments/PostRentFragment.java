package com.propertybuysell.mhome.Fragments;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import com.propertybuysell.mhome.Activities.EditPostActivity;
import com.propertybuysell.mhome.Activities.ViewcontactRentActivity;
import com.propertybuysell.mhome.ModelClasses.GetProperty;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostRentFragment extends Fragment {


    RetrofitService retrofitService;
    SavePref savePref;
    RecyclerView recycler;
    String checkvalue;
    PropertyAdapter adapter;
    LinearLayout loaderlayout,ll_home,ll_apartment,ll_office,ll_shop,l_nodata,ll_all;



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


        View view = inflater.inflate(R.layout.activity_rentshortlist, container, false);

        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

                get_property(checkvalue);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(getActivity());


        loaderlayout = view.findViewById(R.id.loaderlayout);
        recycler = view.findViewById(R.id.recycler);
        ll_home = view.findViewById(R.id.ll_home);
        ll_apartment = view.findViewById(R.id.ll_apartment);
        ll_office = view.findViewById(R.id.ll_office);
        ll_shop = view.findViewById(R.id.ll_shop);
        l_nodata = view.findViewById(R.id.l_nodata);
        ll_all = view.findViewById(R.id.ll_all);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter=new PropertyAdapter(getContext());
        recycler.setAdapter(adapter);

        ll_all.setBackground(getResources().getDrawable(R.drawable.unitbg));
        ll_home.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
        ll_apartment.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
        ll_office.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
        ll_shop.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_all.setBackground(getResources().getDrawable(R.drawable.unitbg));
                ll_home.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_apartment.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_office.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_shop.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

              checkvalue="All";
                get_property(checkvalue);
            }
        });
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_all.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_home.setBackground(getResources().getDrawable(R.drawable.unitbg));
                ll_apartment.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_office.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_shop.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

              checkvalue="Home";
                get_property(checkvalue);
            }
        });


        ll_apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_all.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

                ll_home.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_apartment.setBackground(getResources().getDrawable(R.drawable.unitbg));
                ll_office.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_shop.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

                checkvalue="Apartment";
                get_property(checkvalue);
            }
        });


        ll_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_all.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

                ll_home.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_apartment.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_office.setBackground(getResources().getDrawable(R.drawable.unitbg));
                ll_shop.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                checkvalue="Office";
                get_property(checkvalue);
            }
        });

        ll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_all.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

                ll_home.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_apartment.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_office.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
                ll_shop.setBackground(getResources().getDrawable(R.drawable.unitbg));
                checkvalue="Shop";
                get_property(checkvalue);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ll_all.setBackground(getResources().getDrawable(R.drawable.unitbg));

        ll_home.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
        ll_apartment.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
        ll_office.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));
        ll_shop.setBackground(getResources().getDrawable(R.drawable.unitwhitebg));

        checkvalue="All";
        get_property(checkvalue);
    }

    private void get_property(String property) {
        showLoader();

        retrofitService.get_user_rent_property(SavePref.getUserId()).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {

                try {
                  adapter.addAll(response.body().getJsonData(),property);
                    if (adapter.addAll(response.body().getJsonData(), property).size()==0){
                        l_nodata.setVisibility(View.VISIBLE);
                    }else {
                        l_nodata.setVisibility(View.GONE);
                    }

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
        ArrayList<GetProperty_Inner> getProperty_inners=new ArrayList<>();

        public PropertyAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public PropertyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_post_property, parent, false);
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
                holder.tv_ptype.setText("Property Type : "+getProperty_inners.get(position).getPropertyType());

                Glide.with(mContext)
                        .load(getProperty_inners.get(position).getProductImage())
                        .placeholder(R.drawable.no_image)
                        .into(holder.im_top);


                if (getProperty_inners.get(position).getProperty_sold().equalsIgnoreCase("0")){
                    holder.tv_sold.setVisibility(View.VISIBLE);
                    holder.imsold.setVisibility(View.GONE);
                }else{
                    holder.tv_sold.setVisibility(View.GONE);
                    holder.imsold.setVisibility(View.VISIBLE);
                }

                holder.tv_sold.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        property_sold(getProperty_inners.get(position).getPropertyId());
                    }
                });

                holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getContext(), EditPostActivity.class);
                        i.putExtra("property_id",getProperty_inners.get(position).getPropertyId());
                        startActivity(i);
                    }
                });
                holder.tv_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getContext(), ViewcontactRentActivity.class);
                        i.putExtra("GetProperty_Inner", (Serializable) getProperty_inners.get(position).getProperty_contact_detail());
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


        public void add(GetProperty_Inner r) {
            getProperty_inners.add(r);
            notifyDataSetChanged();

//        notifyItemInserted(orderModelArrayList.size() - 1);
        }

        public List<GetProperty_Inner> addAll(List<GetProperty_Inner> moveTransactionDetailss, String check) {

            getProperty_inners=new ArrayList<>();
            List<GetProperty_Inner> arr=new ArrayList<>();
            for (GetProperty_Inner result : moveTransactionDetailss) {
                if (check.equalsIgnoreCase("All")){
                    add(result);
                    arr.add(result);
                }else{
                    if (result.getPropertySubType().equalsIgnoreCase(check)){
                        add(result);
                        arr.add(result);
                    }
                }

//                else if (check.equalsIgnoreCase("7")){
//                    add(result);
//                    arr.add(result);
//                }

            }
            return arr;
        }



        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name, tv_address,tv_ptype,tv_edit,tv_view,tv_sold;
            LinearLayout card;
            RoundedImageView im_top;
            ImageView imsold;

            ViewHolder(View itemView) {
                super(itemView);

                tv_name = itemView.findViewById(R.id.tv_name);
                tv_address = itemView.findViewById(R.id.tv_address);
                card = itemView.findViewById(R.id.card);
                tv_ptype = itemView.findViewById(R.id.tv_ptype);
                im_top = itemView.findViewById(R.id.im_top);
                tv_edit = itemView.findViewById(R.id.tv_edit);
                tv_view = itemView.findViewById(R.id.tv_view);
                tv_sold = itemView.findViewById(R.id.tv_sold);
                imsold = itemView.findViewById(R.id.imsold);



            }

        }
    }


    private void property_sold(String property) {
        showLoader();

        retrofitService.property_sold(SavePref.getUserId(),property,"1").enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {

                try {
                    Toast.makeText(getContext(), ""+response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                    get_property(checkvalue);
//                    adapter.addAll(response.body().getJsonData(),property);
//                    if (adapter.addAll(response.body().getJsonData(), property).size()==0){
//                        l_nodata.setVisibility(View.VISIBLE);
//                    }else {
//                        l_nodata.setVisibility(View.GONE);
//                    }
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }
}