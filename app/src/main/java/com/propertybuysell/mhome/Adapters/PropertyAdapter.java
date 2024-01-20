package com.propertybuysell.mhome.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.propertybuysell.mhome.Activities.EditUserProfileActivity;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    ViewHolder viewHolder;
    Context mContext;
    ArrayList<GetProperty_Inner> getProperty_inners;


    public PropertyAdapter(Context context, List<GetProperty_Inner> getProperty_inners) {
        this.mContext = context;
        this.getProperty_inners = (ArrayList<GetProperty_Inner>) getProperty_inners;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_property_item, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

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
            }
           /// holder.tv_startfrom.setText("₹"+getProperty_inners.get(position).getPriceDetail()+" "+getProperty_inners.get(position).getPriceUnitDetail());
            holder.tv_area.setText(getProperty_inners.get(position).getAreaDetail()+" "+getProperty_inners.get(position).getAreaUnitDetail());

            Glide.with(mContext)
                    .load(getProperty_inners.get(position).getProductImage())
                    .placeholder(R.drawable.no_image)
                    .into(holder.im_top);

//            Glide.with(mContext)
//                    .load(mContext.getResources().getDrawable(R.drawable.favourite))
//                    .placeholder(R.drawable.no_image)
//                    .into(holder.tv_fav);

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
        return getProperty_inners.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_address,tv_area,tv_startfrom;
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
}