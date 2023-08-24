package com.example.otpregisterloginhome.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.otpregisterloginhome.Fragments.ProfileFragment;
import com.example.otpregisterloginhome.Fragments.SearchCommFragment;
import com.example.otpregisterloginhome.Fragments.SearchResidentialFragment;
import com.example.otpregisterloginhome.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

public class SearchpropertyActivity extends AppCompatActivity {

    SmartTabLayout viewpagertab;
    ViewPager viewpager;
    LinearLayout loaderlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpropery);
        try {
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
            headingtv.setText("Search List");
            ArrayList<String> arrayList=new ArrayList<>();
            arrayList.add("Residential");
            arrayList.add("Commercial");
            viewpager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(),arrayList));
            viewpagertab.setViewPager(viewpager);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                return new SearchResidentialFragment();

            }else {
                return new SearchCommFragment();

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
}