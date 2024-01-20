package com.propertybuysell.mhome.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.propertybuysell.mhome.Fragments.PostRentFragment;
import com.propertybuysell.mhome.Fragments.PostsellFragment;
import com.propertybuysell.mhome.Fragments.SearchCommFragment;
import com.propertybuysell.mhome.Fragments.SearchResidentialFragment;
import com.propertybuysell.mhome.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class PostedpropertyActivity extends AppCompatActivity {

    SmartTabLayout viewpagertab;
    ViewPager viewpager;
    LinearLayout loaderlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        viewpagertab =findViewById(R.id.viewpagertab);
        viewpager =findViewById(R.id.viewpager);
        loaderlayout =findViewById(R.id.loaderlayout);

        ImageView backiv = findViewById(R.id.backic);
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });


        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Your Property");
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Rent");
        arrayList.add("Sell");

        ViewpagerAdapter viewPagerAdapter=new ViewpagerAdapter(getSupportFragmentManager(),arrayList);
        viewpager.setAdapter(viewPagerAdapter);
     //   viewpager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(),arrayList));
        viewpagertab.setViewPager(viewpager);

        TextView view1 = (TextView) viewpagertab.getTabAt(0);
        view1.setTextColor(getResources().getColor(R.color.darkgray));
        viewpagertab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = viewPagerAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    TextView view = (TextView) viewpagertab.getTabAt(i);
                    view.setTextColor(getResources().getColor(R.color.white));
                }
                TextView view = (TextView) viewpagertab.getTabAt(position);
                view.setTextColor(getResources().getColor(R.color.darkgray));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }



    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }


    public class ViewpagerAdapter extends FragmentPagerAdapter{

        ArrayList<String> arrayList;
        public ViewpagerAdapter(@NonNull FragmentManager fm, ArrayList<String> arrayList) {
            super(fm);
            this.arrayList=arrayList;
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            if (position==0){
                return new PostRentFragment();

            }else {
                return new PostsellFragment();

            }
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(PostedpropertyActivity.this,NewHomeActivity.class);
        startActivity(i);
    }
}