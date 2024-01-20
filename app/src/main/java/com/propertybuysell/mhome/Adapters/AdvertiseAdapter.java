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
import com.propertybuysell.mhome.Activities.PropertydetailActivity;
import com.propertybuysell.mhome.ModelClasses.GetAdverti_Inner;
import com.propertybuysell.mhome.ModelClasses.GetAdvocate_Inner;
import com.propertybuysell.mhome.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


public class AdvertiseAdapter extends RecyclerView.Adapter<AdvertiseAdapter.ViewHolder> {
    ViewHolder viewHolder;
    Context mContext;
    ArrayList<GetAdverti_Inner> getAdverti_inners;


    public AdvertiseAdapter(Context context, List<GetAdverti_Inner> getAdverti_inners) {
        this.mContext = context;
        this.getAdverti_inners = (ArrayList<GetAdverti_Inner>) getAdverti_inners;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_advrtice_item, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            Glide.with(mContext)
                    .load(getAdverti_inners.get(position).getBannerImage())
                    .placeholder(R.drawable.no_image)
                    .into(holder.im_adimage);

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdverti_inners.get(position).getPropertyId().equalsIgnoreCase("0")){

                    }else{
                        Intent i=new Intent(mContext, PropertydetailActivity.class);
                        i.putExtra("p_id",getAdverti_inners.get(position).getPropertyId());
                        mContext.startActivity(i);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return getAdverti_inners.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        RoundedImageView im_adimage;

        ViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            im_adimage = itemView.findViewById(R.id.im_adimage);

        }

    }
}