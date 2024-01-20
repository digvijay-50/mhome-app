package com.propertybuysell.mhome.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.ModelClasses.PropertyContactDetail;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class ViewcontactRentActivity extends AppCompatActivity {

    RecyclerView re_property;
    LinearLayout loaderlayout;
    SavePref savePref;
    LinearLayout l_nodata;
    RetrofitService retrofitService;
    SwipeRefreshLayout refres;
    ArrayList<PropertyContactDetail> getProperty_inner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advocatelist);
        re_property = findViewById(R.id.re_property);
        loaderlayout = findViewById(R.id.loaderlayout);
        refres = findViewById(R.id.refres);
        l_nodata = findViewById(R.id.l_nodata);

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(ViewcontactRentActivity.this);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);

        getProperty_inner= (ArrayList<PropertyContactDetail>) getIntent().getSerializableExtra("GetProperty_Inner");
        headingtv.setText("Contact Details");


//        Log.i("onCreate", "onCreate: "+getProperty_inner.size());

        refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                re_property.setAdapter(new AdvocateAdapter(ViewcontactRentActivity.this,getProperty_inner));

                refres.setRefreshing(false);
            }
        });
        re_property.setLayoutManager(new LinearLayoutManager(ViewcontactRentActivity.this));
        re_property.setAdapter(new AdvocateAdapter(ViewcontactRentActivity.this,getProperty_inner));

        if (getProperty_inner.size()==0){
            l_nodata.setVisibility(View.VISIBLE);
        }else{
            l_nodata.setVisibility(View.GONE);
        }
    }


    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }




    public class AdvocateAdapter extends RecyclerView.Adapter<AdvocateAdapter.ViewHolder> {
        ViewHolder viewHolder;
        Context mContext;
        ArrayList<PropertyContactDetail> getAdvocate_inners;


        public AdvocateAdapter(Context context, ArrayList<PropertyContactDetail> getAdvocate_inners) {
            this.mContext = context;
            this.getAdvocate_inners = getAdvocate_inners;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_advocate_item1, parent, false);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            try {
                holder.tv_pakname.setText(getAdvocate_inners.get(position).getUserName());
                holder.tv_price.setVisibility(View.GONE);
                holder.tv_numberpost.setText("Mobile No. : "+getAdvocate_inners.get(position).getPhone());
                holder.tv_duration.setText("Date : "+getAdvocate_inners.get(position).getPropety_contact_date());
                Glide.with(mContext)
                        .load(getAdvocate_inners.get(position).getImageurl())
                        .placeholder(R.drawable.userimage)
                        .into(holder.im_adimage);

                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return getAdvocate_inners.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_pakname, tv_price,tv_numberpost,tv_duration;
            CardView card;

            RoundedImageView im_adimage;

            ViewHolder(View itemView) {
                super(itemView);

                tv_pakname = itemView.findViewById(R.id.tv_pakname);
                tv_price = itemView.findViewById(R.id.tv_price);
                card = itemView.findViewById(R.id.card);
                tv_numberpost = itemView.findViewById(R.id.tv_numberpost);
                tv_duration = itemView.findViewById(R.id.tv_duration);
                im_adimage = itemView.findViewById(R.id.im_adimage);

            }

        }
    }
    
}