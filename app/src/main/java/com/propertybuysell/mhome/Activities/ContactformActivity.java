package com.propertybuysell.mhome.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.propertybuysell.mhome.Adapters.CityAdapter;
import com.propertybuysell.mhome.Adapters.StateAdapter1;
import com.propertybuysell.mhome.ModelClasses.GetCity;
import com.propertybuysell.mhome.ModelClasses.GetCity_Inner;
import com.propertybuysell.mhome.ModelClasses.GetState;
import com.propertybuysell.mhome.ModelClasses.GetState_Inner;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactformActivity extends AppCompatActivity {

    EditText et_state,et_city,et_name,et_phone,et_pincode,et_descrip;
    RetrofitService retrofitService;
    TextView tv_proceed;
    SavePref savePref;
    String  stateid = "", cityid = "";
    LinearLayout loaderlayout;

    ArrayList<GetState_Inner> getState_inners = new ArrayList<>();
    ArrayList<GetCity_Inner> getCity_inners = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactform);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        ImageView backiv = findViewById(R.id.backic);
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });


        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Contact Form");

        savePref = new SavePref(this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);


        et_state=findViewById(R.id.et_state);
        et_city=findViewById(R.id.et_city);
        et_name=findViewById(R.id.et_name);
        et_phone=findViewById(R.id.et_phone);
        et_pincode=findViewById(R.id.et_pincode);
        tv_proceed=findViewById(R.id.tv_proceed);
        et_descrip=findViewById(R.id.et_descrip);


        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()){
                    add_advertisment_form();
                }
            }
        });
        et_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openstateDialog();
            }
        });
        et_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stateid.equalsIgnoreCase("")) {
                    Toast.makeText(ContactformActivity.this, "Please Enter State First", Toast.LENGTH_SHORT).show();
                } else {
                    opencityDialog();
                }

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


    private void openstateDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.state_dialog);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);

        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
        SearchView searchViewsearchView = (SearchView) dialog.findViewById(R.id.search);
        StateAdapter1 mAdapter = new StateAdapter1(this, getState_inners, new StateAdapter1.ContactsAdapterListener() {
            @Override
            public void onContactSelected(GetState_Inner contact) {
                //   savePref.setstate(contact.getState_name());
                et_state.setText(contact.getStateName());
                ///   country_id = contact.getCountryId();

                stateid = contact.getStateId();
                cityid = "";
//                et_city.setText("");
//                Stateid = contact.getSid();
//                cityid = "";
                dialog.dismiss();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        get_spinnerstate(mAdapter);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

//        searchView.getActionView();
        searchViewsearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchViewsearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchViewsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        dialog.show();
    }

    public void get_spinnerstate(StateAdapter1 mAdapter) {

        getState_inners.clear();
        retrofitService.get_state().enqueue(new Callback<GetState>() {
            @Override
            public void onResponse(Call<GetState> call, Response<GetState> response) {

            //    Log.i("onResponse", "onResponse: "+response.body().getJsonData().size());
              getState_inners.addAll(response.body().getJsonData());
                mAdapter.notifyDataSetChanged();




            }

            @Override
            public void onFailure(Call<GetState> call, Throwable t) {
            }
        });
    }

    private void opencityDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.state_dialog);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);

        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);
        SearchView searchViewsearchView = (SearchView) dialog.findViewById(R.id.search);
        CityAdapter mAdapter = new CityAdapter(this, getCity_inners, new CityAdapter.ContactsAdapterListener() {
            @Override
            public void onContactSelected(GetCity_Inner contact) {
                //   savePref.setstate(contact.getState_name());
                et_city.setText(contact.getCityName());
//                et_city.setText("");
//                Stateid = contact.getSid();
                cityid = contact.getCityId();
                dialog.dismiss();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        get_spinnercity(mAdapter);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

//        searchView.getActionView();
        searchViewsearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchViewsearchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchViewsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        dialog.show();
    }

    public void get_spinnercity(CityAdapter mAdapter) {
        getCity_inners.clear();
        retrofitService.get_city(stateid).enqueue(new Callback<GetCity>() {
            @Override
            public void onResponse(Call<GetCity> call, Response<GetCity> response) {
                getCity_inners.addAll(response.body().getJsonData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetCity> call, Throwable t) {
            }
        });
    }

    public void add_advertisment_form() {
        showLoader();
        getCity_inners.clear();
        retrofitService.add_advertisment_form(savePref.getUserId(),et_name.getText().toString(),et_phone.getText().toString(),et_state.getText().toString(),et_city.getText().toString(),et_pincode.getText().toString(),et_descrip.getText().toString()).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {

                if (response.body().getJsonData().get(0).getSuccess().equalsIgnoreCase("1")){
                    finish();
                }else{
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                hideLoader();
            }
        });
    }


    private boolean validation() {

        if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid User Name", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Phone", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(et_state.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter state", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(et_city.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter city", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(et_pincode.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter pincode", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }


    }



}