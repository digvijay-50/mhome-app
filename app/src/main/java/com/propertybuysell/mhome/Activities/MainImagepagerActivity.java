package com.propertybuysell.mhome.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.propertybuysell.mhome.Adapters.ImagesViewPagerAdapter;
import com.propertybuysell.mhome.R;

import java.util.ArrayList;
import java.util.List;


public class MainImagepagerActivity extends AppCompatActivity {
    private ImagesViewPagerAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    TextView headingtv;

    private List<String> arrayList = new ArrayList<>();
    ProgressDialog pDialog;
    int id;
    ImageView backic;
    int currentPage;


    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_imageview_activity);
        try {

            headingtv=findViewById(R.id.headingtv);
            backic=findViewById(R.id.backic);

            backic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            Bundle bundle = getIntent().getExtras();
            arrayList= (List<String>) getIntent().getSerializableExtra("arrayList");
            id = bundle.getInt("id");



            currentPage = id;
//            Log.i("instantiateItem", "instantiateItem12: "+ Constants.imageurl+arrayList.get(0));
            mCustomPagerAdapter = new ImagesViewPagerAdapter(this, arrayList);
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mCustomPagerAdapter);
            mViewPager.setCurrentItem(id);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                public void onPageScrollStateChanged(int state) {

                }

                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    int pos=position+1;
                    headingtv.setText(pos+" of "+arrayList.size());
                }

                public void onPageSelected(int position) {


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }




}
