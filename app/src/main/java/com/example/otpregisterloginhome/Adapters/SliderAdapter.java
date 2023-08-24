package com.example.otpregisterloginhome.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.Activities.PropertydetailActivity;
import com.example.otpregisterloginhome.ModelClasses.BannerDetailModel;
import com.example.otpregisterloginhome.ModelClasses.GetAdverti_Inner;
import com.example.otpregisterloginhome.R;

import java.util.List;


public class SliderAdapter extends PagerAdapter {
    Context context;
    List<GetAdverti_Inner> imageModelArrayList;


    public SliderAdapter(Context context, List<GetAdverti_Inner> imageModelArrayList) {
        try {
            this.context = context;
            this.imageModelArrayList = imageModelArrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {


        return imageModelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {


        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = layoutInflater.inflate(R.layout.pager_banner_slider, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageiv);
        Glide.with(context)
                .load(imageModelArrayList.get(position).getBannerImage())
                .placeholder(R.drawable.no_image)
                .into(imageView);


        CardView card = itemView.findViewById(R.id.card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageModelArrayList.get(position).getPropertyId().equalsIgnoreCase("0")){
                    try {
                        String videoUrl = imageModelArrayList.get(position).getBanner_link();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(videoUrl));
                        context.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Intent i=new Intent(context, PropertydetailActivity.class);
                    i.putExtra("p_id",imageModelArrayList.get(position).getPropertyId());
                    context.startActivity(i);
                }
            }
        });

      ///  Log.e("EFcdcdfddv", imageModelArrayList.get(position).getbImage());
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        try {

            container.removeView((CardView) object);

        } catch (Exception e) {

        }

    }
}