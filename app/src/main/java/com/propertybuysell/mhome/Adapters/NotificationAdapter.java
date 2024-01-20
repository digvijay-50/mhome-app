package com.propertybuysell.mhome.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.propertybuysell.mhome.ModelClasses.GetNotification_Inner;
import com.propertybuysell.mhome.R;

import java.util.ArrayList;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    ViewHolder viewHolder;
    Context mContext;
    ArrayList<GetNotification_Inner> notificationModelArrayList;


    public NotificationAdapter(Context context, List<GetNotification_Inner> notificationModelArrayList) {
        this.mContext = context;
        this.notificationModelArrayList = (ArrayList<GetNotification_Inner>) notificationModelArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_notification_item, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        try {
            holder.tv_message.setText(notificationModelArrayList.get(position).getMsg());
            holder.tv_date.setText(notificationModelArrayList.get(position).getDate());


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
        return notificationModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_message, tv_date;
        CardView card;

        ViewHolder(View itemView) {
            super(itemView);

            tv_message = itemView.findViewById(R.id.tv_message);
            tv_date = itemView.findViewById(R.id.tv_date);
            card = itemView.findViewById(R.id.card);

        }

    }
}