package com.propertybuysell.mhome.Activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.propertybuysell.mhome.NetworkUtils.NetworkStateReceiver;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;

public class SettingDataActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    TextView texttv;
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
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_data);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


        ImageView backiv = findViewById(R.id.backic);
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });


        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText(getIntent().getStringExtra("header"));


        savePref = new SavePref(SettingDataActivity.this);


        texttv = findViewById(R.id.texttv);

        texttv.setText(Html.fromHtml(getIntent().getStringExtra("text")));


    }


}
