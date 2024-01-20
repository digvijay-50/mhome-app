package com.propertybuysell.mhome.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.propertybuysell.mhome.Activities.MainImagepagerActivity;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.Constant;


import java.util.ArrayList;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    SavePref savePref;
    String check;
    public ArrayList<String> categoryDetailModels;
    private Context context;




    public ImageAdapter(Context context, ArrayList<String> categoryDetailModels) {
        this.context = context;

        this.categoryDetailModels = categoryDetailModels;
        savePref = new SavePref(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.image_item1, parent, false);
        ViewHolder viewHolder = new ViewHolder(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {





            Glide.with(context)
                    .load(Constant.imageurl + categoryDetailModels.get(position))
                    .placeholder(R.drawable.no_image)
                    .into(holder.categoryiv);


            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(context, MainImagepagerActivity.class);
                    i.putExtra("images",Constant.imageurl + categoryDetailModels.get(position));
                    i.putExtra("arrayList",categoryDetailModels);
                    i.putExtra("id",position);
                    context.startActivity(i);
                }
            });


        }
        catch (Exception e)
        {

        }


    }

    @Override
    public int getItemCount() {

       // Log.i("getItemCount", "getItemCount: "+categoryDetailModels.size());
            return categoryDetailModels.size();


    }





    protected class ViewHolder extends RecyclerView.ViewHolder {


        LinearLayout linearLayout;

        TextView categorytv;
        ImageView categoryiv;


        public ViewHolder(final View v) {
            super(v);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            categorytv = itemView.findViewById(R.id.categorytv);
            categoryiv = itemView.findViewById(R.id.categoryiv);

        }
    }


}