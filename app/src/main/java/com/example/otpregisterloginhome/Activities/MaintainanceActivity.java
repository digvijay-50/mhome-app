package com.example.otpregisterloginhome.Activities;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.otpregisterloginhome.NetworkUtils.NetworkStateReceiver;
import com.example.otpregisterloginhome.R;

public class MaintainanceActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    LinearLayout nointernet;
    TextView tv_des;
    NetworkStateReceiver networkStateReceiver;

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
        setContentView(R.layout.activity_maintainance);

        nointernet = findViewById(R.id.nointernet);
        tv_des = findViewById(R.id.tv_des);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        tv_des.setText(SplashActivity.settingsDetailModel.getApp_maintenance_description());

    }


}
