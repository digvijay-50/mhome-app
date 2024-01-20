package com.propertybuysell.mhome.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.propertybuysell.mhome.Adapters.NotificationAdapter;
import com.propertybuysell.mhome.ModelClasses.GetNotification;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView re_trainner;
    RetrofitService retrofitService;
    String v_id,service_id,checkstatus;
    LinearLayout loaderlayout,l_nodata;

    SavePref savePref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);
        re_trainner=findViewById(R.id.re_trainner);
        loaderlayout=findViewById(R.id.loaderlayout);
        l_nodata=findViewById(R.id.l_nodata);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("All Notification");
        re_trainner.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));

        savePref=new SavePref(NotificationActivity.this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);

        get_vendor_plan();
        
    }

    private void get_vendor_plan() {
        showLoader();
        retrofitService.get_notification(savePref.getUserId()).enqueue(new Callback<GetNotification>() {
            @Override
            public void onResponse(Call<GetNotification> call, final retrofit2.Response<GetNotification> response) {
                try {

                    if (response.body().getJsonData().size()==0){
                        l_nodata.setVisibility(View.VISIBLE);
                    }else{
                        l_nodata.setVisibility(View.GONE);
                    }
                        if (savePref.getnoticount().equalsIgnoreCase("0")){
                        savePref.setnoticount(response.body().getTotalCount());
                    }else if (!savePref.getnoticount().equalsIgnoreCase(response.body().getTotalCount())){
                        savePref.setnoticount(response.body().getTotalCount());
                    }
                    re_trainner.setAdapter(new NotificationAdapter(NotificationActivity.this,response.body().getJsonData()));
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<GetNotification> call, Throwable t) {
                t.printStackTrace();
                hideLoader();
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



    

}