package com.example.otpregisterloginhome.placeapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otpregisterloginhome.Activities.SplashActivity;
import com.example.otpregisterloginhome.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapplaceActivity1 extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteAdapter adapter;
    TextView responseView,tv_done;
    PlacesClient placesClient;
    RecyclerView recycler;
    ArrayList<String>arrayList=new ArrayList<>();
//    ArrayList<String>arrayList1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        ImageView backiv = findViewById(R.id.backic);
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });


        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Search City");
//        arrayList1= (ArrayList<String>) getIntent().getSerializableExtra("abs");

        responseView = findViewById(R.id.response);
        tv_done = findViewById(R.id.tv_done);
        recycler = findViewById(R.id.recycler);

        recycler.setLayoutManager(new GridLayoutManager(MapplaceActivity1.this,2));

        String apiKey = getString(R.string.api_key);

//        Toast.makeText(this, ""+arrayList1.size(), Toast.LENGTH_SHORT).show();
//        if (arrayList1.size()==0){
//
//
//        }else{
//            arrayList.addAll(arrayList1);
//            recycler.setAdapter(new CityAdapter(MapplaceActivity1.this,arrayList));
//        }

        if (apiKey.isEmpty()) {
            responseView.setText(getString(R.string.error));
            return;
        }

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);
        initAutoCompleteTextView();

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putStringArrayListExtra("arraylist",arrayList);
                setResult(2,intent);
                finish();//finishing activity

//                for (int i = 0; i < arrayList.size(); i++) {
//                    Log.i("onClick", "onClick: "+arrayList.get(i));
//                }

            }
        });

    }

    private void initAutoCompleteTextView() {

        autoCompleteTextView = findViewById(R.id.auto);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(this, placesClient);
        autoCompleteTextView.setAdapter(adapter);
    }

    private final AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = adapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {
                   //         if (arrayList.size()==0){
                                arrayList.add(task.getPlace().getName());
//                            }else{
//                                for (int j = 0; j < arrayList.size(); j++) {
//                                    if (arrayList.get(i).equalsIgnoreCase(task.getPlace().getName())){
//                                    }else{
//                                        arrayList.add(task.getPlace().getName());
//
//                                    }
//
//                                }
//                            }



                      //      recycler.setAdapter(new CityAdapter(MapplaceActivity.this,arrayList));
                            Log.i("onClick", "onSuccess: "+arrayList.size());

                            getLocationFromAddress(MapplaceActivity1.this, task.getPlace().getAddress());

//                            Log.i("onClick", "onSuccess: "+task.getPlace().getAddress()+" \nName  "+task.getPlace().getName()+" \nAddressComponents "+
//                                    task.getPlace().getAddressComponents()+" \nLatLng "+task.getPlace().getLatLng()+" \nPlusCode  "+task.getPlace().getPlusCode());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            responseView.setText(e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
        ViewHolder viewHolder;
        Context mContext;
        ArrayList<String> notificationModelArrayList;


        public CityAdapter(Context context, ArrayList<String> notificationModelArrayList) {
            this.mContext = context;
            this.notificationModelArrayList = (ArrayList<String>) notificationModelArrayList;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_city, parent, false);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            try {
                holder.tv_city.setText(notificationModelArrayList.get(position));
                //  holder.tv_date.setText(notificationModelArrayList.get(position).getDate());


                holder.im_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notificationModelArrayList.remove(position);
//                        arrayList.remove(position);
                   //     notificationModelArrayList.remove(position);
                        notifyDataSetChanged();
//                    if (notificationModelArrayList.get(0).getNotificationOrderId().equalsIgnoreCase("0")){
//
//                    }else{
//                        Intent i = new Intent(mContext, OrderDetailActivity.class);
//                        i.putExtra("order_id", notificationModelArrayList.get(position).getNotificationOrderId());
//                        mContext.startActivity(i);
                        //   }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return notificationModelArrayList.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_city;
            CardView card;
            ImageView im_remove;

            ViewHolder(View itemView) {
                super(itemView);

                tv_city = itemView.findViewById(R.id.tv_city);

                card = itemView.findViewById(R.id.card);
                im_remove = itemView.findViewById(R.id.im_remove);

            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("arraylist",arrayList);
        setResult(2,intent);
        finish();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);

            responseView.setText("Address : "+address.get(0).getAddressLine(0)
                    + "\n State : " + address.get(0).getAdminArea()+
                    "\n Sub locality : " + address.get(0).getSubLocality()
                    + "\n Contry Name : " + address.get(0).getCountryName()
                    + "\n city : " + address.get(0).getLocality()
            );

            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getAddressLine(0));
            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getAdminArea());
            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getSubLocality());
            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getCountryName());
            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getLocale());
            Log.i("onClick", "getLocationFromAddress: "+address.get(0).getLocality());
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            Log.i("onClick", "getLocationFromAddress: "+location.getLatitude()+" "+location.getLongitude());


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


}
