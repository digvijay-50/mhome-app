package com.propertybuysell.mhome.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.propertybuysell.mhome.Activities.AdvocatedetailActivity;
import com.propertybuysell.mhome.Activities.AdvocatelistActivity;
import com.propertybuysell.mhome.Activities.UserDetailsActivity;
import com.propertybuysell.mhome.ModelClasses.GetAdvocate_Inner;
import com.propertybuysell.mhome.ModelClasses.GetPostPackage_Inner;
import com.propertybuysell.mhome.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


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
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_advocate_item, parent, false);
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


                    Intent i=new Intent(mContext, AdvocatedetailActivity.class);
                    i.putExtra("getAdvocate_inners",getAdvocate_inners.get(position));
                    mContext.startActivity(i);

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