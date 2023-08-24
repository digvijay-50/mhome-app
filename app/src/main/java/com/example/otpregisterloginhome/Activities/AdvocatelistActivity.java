package com.example.otpregisterloginhome.Activities;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.Adapters.AdvocateAdapter;
import com.example.otpregisterloginhome.ModelClasses.FavModel;
import com.example.otpregisterloginhome.ModelClasses.GetAdvocate;
import com.example.otpregisterloginhome.ModelClasses.GetAdvocate_Inner;
import com.example.otpregisterloginhome.ModelClasses.GetProperty;
import com.example.otpregisterloginhome.ModelClasses.GetProperty_Inner;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvocatelistActivity extends AppCompatActivity {

    RecyclerView re_property;
    LinearLayout loaderlayout;
    SavePref savePref;
    RetrofitService retrofitService;
    SwipeRefreshLayout refres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advocatelist);
        re_property = findViewById(R.id.re_property);
        loaderlayout = findViewById(R.id.loaderlayout);
        refres = findViewById(R.id.refres);

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(AdvocatelistActivity.this);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Advocate List");


        refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_advocate();
                refres.setRefreshing(false);
            }
        });
        re_property.setLayoutManager(new LinearLayoutManager(AdvocatelistActivity.this));
        get_advocate();

    }


    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }

    private void get_advocate() {
        showLoader();
        retrofitService.get_advocate(savePref.getcity()).enqueue(new Callback<GetAdvocate>() {
            @Override
            public void onResponse(Call<GetAdvocate> call, Response<GetAdvocate> response) {
                try {
                    re_property.setAdapter(new AdvocateAdapter(AdvocatelistActivity.this,response.body().getJsonData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<GetAdvocate> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });
    }



    public class AdvocateAdapter extends RecyclerView.Adapter<AdvocateAdapter.ViewHolder> {
        ViewHolder viewHolder;
        Context mContext;
        ArrayList<GetAdvocate_Inner> getAdvocate_inners;


        public AdvocateAdapter(Context context, List<GetAdvocate_Inner> getAdvocate_inners) {
            this.mContext = context;
            this.getAdvocate_inners = (ArrayList<GetAdvocate_Inner>) getAdvocate_inners;
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
                holder.tv_pakname.setText(getAdvocate_inners.get(position).getAdvocateName());
                holder.tv_price.setText("Mobile No : "+getAdvocate_inners.get(position).getAdvocateMobile());
                holder.tv_numberpost.setText("Experience : "+getAdvocate_inners.get(position).getAdvocateExperience());
                holder.tv_duration.setText("Qualification : "+getAdvocate_inners.get(position).getAdvocateQualification());
                Glide.with(mContext)
                        .load(getAdvocate_inners.get(position).getAdvocateProfile())
                        .placeholder(R.drawable.userimage)
                        .into(holder.im_adimage);

                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i=new Intent(AdvocatelistActivity.this,AdvocatedetailActivity.class);
                        i.putExtra("getAdvocate_inners",getAdvocate_inners.get(position));
                        startActivity(i);
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