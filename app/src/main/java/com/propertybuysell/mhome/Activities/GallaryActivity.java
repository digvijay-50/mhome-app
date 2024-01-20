package com.propertybuysell.mhome.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.propertybuysell.mhome.Adapters.ImageAdapter;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.R;

import java.util.ArrayList;

public class GallaryActivity extends AppCompatActivity {

    GetProperty_Inner property_inner;
    RecyclerView recycler_gallary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Gallery");

        property_inner= (GetProperty_Inner) getIntent().getSerializableExtra("gallary");
        recycler_gallary=findViewById(R.id.recycler_gallary);
        recycler_gallary.setLayoutManager(new LinearLayoutManager(GallaryActivity.this));

        recycler_gallary.setAdapter(new ImageAdapter(GallaryActivity.this, (ArrayList<String>) property_inner.getPropertyGallery()));

    }
}