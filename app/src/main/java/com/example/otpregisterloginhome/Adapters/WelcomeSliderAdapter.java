package com.example.otpregisterloginhome.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.ModelClasses.WelcomeModel;
import com.example.otpregisterloginhome.R;

import java.util.List;


public class WelcomeSliderAdapter extends PagerAdapter {
    Context context;
    List<WelcomeModel> imageModelArrayList;


    public WelcomeSliderAdapter(Context context, List<WelcomeModel> imageModelArrayList) {
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

        View itemView = layoutInflater.inflate(R.layout.pager_welcome_slider, container, false);

        TextView messagetv = itemView.findViewById(R.id.messagetv);
        TextView titletv = itemView.findViewById(R.id.titletv);
        ImageView imageiv = itemView.findViewById(R.id.imageiv);


        messagetv.setText(imageModelArrayList.get(position).getMessage());
        titletv.setText(imageModelArrayList.get(position).getTitle());
        Glide.with(context)
                .load(imageModelArrayList.get(position).getImage())
                .into(imageiv);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        try {

            container.removeView((LinearLayout) object);

        } catch (Exception e) {

        }

    }
}