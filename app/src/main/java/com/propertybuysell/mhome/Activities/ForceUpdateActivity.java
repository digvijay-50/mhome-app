package com.propertybuysell.mhome.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.propertybuysell.mhome.NetworkUtils.NetworkStateReceiver;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;

public class ForceUpdateActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    TextView texttv;

    TextView cancelbtn;

    TextView linkBtn;

    SavePref savePref;

    LinearLayout nointernet;
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
        setContentView(R.layout.activity_force_update_new);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


        savePref = new SavePref(ForceUpdateActivity.this);


        linkBtn = findViewById(R.id.linkBtn);
        texttv = findViewById(R.id.texttv);
        texttv.setText(SavePref.getSettingsDetailModel().getApp_update_desc());

        linkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                Intent irate = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="
                        + ForceUpdateActivity.this.getPackageName()));
                Intent new_intent = Intent.createChooser(irate, "Update App");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(new_intent);

            }
        });


        cancelbtn = findViewById(R.id.cancelbtn);
        cancelbtn.setVisibility(View.GONE);
        if (SavePref.getSettingsDetailModel().getApp_cancel_status().equals("1")) {
            cancelbtn.setVisibility(View.VISIBLE);
        } else {
            cancelbtn.setVisibility(View.GONE);
        }

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                startActivity(new Intent(ForceUpdateActivity.this, NewHomeActivity.class));
                finish();


            }
        });


    }


}
