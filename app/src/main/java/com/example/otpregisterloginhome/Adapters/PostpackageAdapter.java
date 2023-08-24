package com.example.otpregisterloginhome.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpregisterloginhome.ModelClasses.GetPostPackage_Inner;
import com.example.otpregisterloginhome.ModelClasses.GetPostPackage_Inner;
import com.example.otpregisterloginhome.R;

import java.util.ArrayList;
import java.util.List;


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
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_postpackage_item, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        try {
            holder.tv_pakname.setText(getPostPackage_inners.get(position).getPropertyPostPackageName());
            holder.tv_price.setText("Price : ₹"+getPostPackage_inners.get(position).getPropertyPostPackagePrice());
            holder.tv_numberpost.setText("Number of Post : "+getPostPackage_inners.get(position).getNoOfPropery());
            holder.tv_duration.setText("Package Duration : "+getPostPackage_inners.get(position).getPackageDuration()+" Days");


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
        return getPostPackage_inners.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pakname, tv_price,tv_numberpost,tv_duration;
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