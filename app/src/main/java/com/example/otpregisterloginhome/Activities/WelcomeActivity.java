package com.example.otpregisterloginhome.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.otpregisterloginhome.Adapters.WelcomeSliderAdapter;
import com.example.otpregisterloginhome.ModelClasses.WelcomeModel;
import com.example.otpregisterloginhome.NetworkUtils.NetworkStateReceiver;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    ViewPager viewpagervp;
    CircleIndicator indicator;
    ArrayList<WelcomeModel> welcomeModels = new ArrayList<>();
    TextView skiptv;
    TextView nextbtn;
    SavePref savePref;

    @Override
    public void networkAvailable() {
        nointernet.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        nointernet.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        savePref = new SavePref(WelcomeActivity.this);

        nextbtn = findViewById(R.id.nextbtn);

        indicator = findViewById(R.id.indicator);
        skiptv = findViewById(R.id.skiptv);

        viewpagervp = findViewById(R.id.viewpagervp);


        welcomeModels.add(new WelcomeModel(R.drawable.w1,
                "Welcome to Mhome",
                "Find the ideal place according to your needs and expectations."));


        welcomeModels.add(new WelcomeModel(R.drawable.w2,
                "Buy & Sell House",
                "Buy & Sell your expected house from phone with Mhome."));


        welcomeModels.add(new WelcomeModel(R.drawable.w3,
                "Get the Keys of your Dream Home",
                "Start searching and quickly find the home you were looking for."));


        viewpagervp.setAdapter(new WelcomeSliderAdapter(WelcomeActivity.this, welcomeModels));
        indicator.setViewPager(viewpagervp);


        viewpagervp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    nextbtn.setText("Next");
                    skiptv.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    nextbtn.setText("Next");
                    skiptv.setVisibility(View.VISIBLE);
                } else {
                    nextbtn.setText("GET STARTED");
                    skiptv.setVisibility(View.GONE);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                if (viewpagervp.getCurrentItem() == 0) {
                    viewpagervp.setCurrentItem(1);
                } else if (viewpagervp.getCurrentItem() == 1) {
                    viewpagervp.setCurrentItem(2);

                } else {
                    SplashActivity.disablefor1sec(v);
                    SavePref.setFirstTime(false);
                    startActivity(new Intent(WelcomeActivity.this, MobileRegisterActivity.class));
                    finish();

                }
            }
        });


        skiptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);


                SavePref.setFirstTime(false);

                startActivity(new Intent(WelcomeActivity.this, MobileRegisterActivity.class));
                finish();


            }
        });


    }

    @Override
    public void onBackPressed() {

        final Dialog dialog = new Dialog(WelcomeActivity.this);
        dialog.setContentView(R.layout.dialog_exit_app);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView nobtn = dialog.findViewById(R.id.nobtn);
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                dialog.dismiss();
            }
        });

        final TextView yesbtn = dialog.findViewById(R.id.yesbtn);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                finishAffinity();

                dialog.dismiss();
            }
        });


        dialog.show();


    }
}