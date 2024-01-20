package com.propertybuysell.mhome.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.Constant;


import java.util.List;

public class ImagesViewPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<String> news;

    public ImagesViewPagerAdapter(final Context context, List<String> news) {
        this.news = news;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_imageview, container, false);

        final ImageView imageviewdp;
        imageviewdp = itemView.findViewById(R.id.imageviewdp);

       // if (TextUtils.isEmpty(news.get(position).getE_adharcard1())) {

        //} else {
            RequestOptions requestOptions = new RequestOptions();
            try {
                Glide.with(this.mContext)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(Constant.imageurl+news.get(position))
                        .into(imageviewdp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            container.addView(itemView);
     //   }


        return itemView;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}