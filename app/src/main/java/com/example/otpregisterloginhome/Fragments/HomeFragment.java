package com.example.otpregisterloginhome.Fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.Activities.AddPostActivity;
import com.example.otpregisterloginhome.Activities.AdvocatelistActivity;
import com.example.otpregisterloginhome.Activities.ContactformActivity;

import com.example.otpregisterloginhome.Activities.MainSelectlocationActivity;
import com.example.otpregisterloginhome.Activities.MainlocationhomeActivity;
import com.example.otpregisterloginhome.Activities.MobileRegisterActivity;
import com.example.otpregisterloginhome.Activities.NewHomeActivity;
import com.example.otpregisterloginhome.Activities.PostedpropertyActivity;
import com.example.otpregisterloginhome.Activities.SearchpropertyActivity;
import com.example.otpregisterloginhome.Adapters.AdvertiseAdapter;
import com.example.otpregisterloginhome.Adapters.AdvocateAdapter;
import com.example.otpregisterloginhome.Adapters.PostpackageAdapter;
import com.example.otpregisterloginhome.Adapters.PropertyAdapter;
import com.example.otpregisterloginhome.Adapters.SliderAdapter;
import com.example.otpregisterloginhome.ModelClasses.BannerModel;
import com.example.otpregisterloginhome.ModelClasses.GetAdvertisementbanner;
import com.example.otpregisterloginhome.ModelClasses.GetAdvocate;
import com.example.otpregisterloginhome.ModelClasses.GetPropertyPostPackage;
import com.example.otpregisterloginhome.ModelClasses.ImageaddModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileModel;
import com.example.otpregisterloginhome.OtherUtils.AutoSwitcherViewPager;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;
import com.example.otpregisterloginhome.placeapi.MapplaceActivity;
import com.example.otpregisterloginhome.placeapi.MapplaceActivity1;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    SavePref savePref;
    LinearLayout loaderlayout;
    RelativeLayout re_post,r_location;

    LinearLayout rl_loc;
    RetrofitService retrofitService;
    RecyclerView re_package,re_advocate;
    ImageView im_contact;

    LinearLayout im_p_property;
    AutoSwitcherViewPager autoSwitcherViewPager;
    CircleIndicator indicator;

    AutoSwitcherViewPager autoSwitcherViewPager1;
    CircleIndicator indicator1;
    ImageView im_search;
    EditText mSearchBoxView;
    TextView tv_post,tv_alladvocate,tvlocation,tv_numberproperty,tv_toppost;

    public HomeFragment() {

    }

    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        autoSwitcherViewPager = view.findViewById(R.id.autoSwitcherViewPager);
        indicator = view.findViewById(R.id.indicator);
        autoSwitcherViewPager1 = view.findViewById(R.id.autoSwitcherViewPager1);
        indicator1 = view.findViewById(R.id.indicator1);

        savePref = new SavePref(getActivity());
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = view.findViewById(R.id.loaderlayout);
        im_search = view.findViewById(R.id.im_search);
        tv_post = view.findViewById(R.id.tv_post);

        re_package = view.findViewById(R.id.re_package);
        re_advocate = view.findViewById(R.id.re_advocate);

        re_post = view.findViewById(R.id.re_post);
        im_contact = view.findViewById(R.id.im_contact);
        tv_numberproperty = view.findViewById(R.id.tv_numberproperty);
        tv_alladvocate = view.findViewById(R.id.tv_alladvocate);
        im_p_property = view.findViewById(R.id.im_p_property);
        tv_toppost = view.findViewById(R.id.tv_toppost);
        tvlocation = view.findViewById(R.id.tvlocation);
        rl_loc = view.findViewById(R.id.rl_loc);
        mSearchBoxView = view.findViewById(R.id.mSearchBoxView);
        r_location = view.findViewById(R.id.r_location);
        re_package.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        re_advocate.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));


        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);



        String text = "<font color=#000000>Want to</font> <font color=#cc0029> Sell/Rent</font></font> <font color=#000000> Your Property?</font>";
        tv_toppost.setText(Html.fromHtml(text));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getBanners();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });




        im_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), SearchpropertyActivity.class);
                startActivity(i);
            }
        });

        rl_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), MainSelectlocationActivity.class);

                Log.i("onActivityResult", "onClick: "+tvlocation.getText().toString());
                i.putExtra("address",tvlocation.getText().toString());
                startActivity(i);

            }
        });
        r_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), SearchpropertyActivity.class);
                startActivity(i);

            }
        });

        mSearchBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), SearchpropertyActivity.class);
                startActivity(i);

            }
        });


        Log.i("onCreateView", "onCreateView: "+savePref.getproperty());
        getUserProfile();
        im_p_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), PostedpropertyActivity.class);
                startActivity(i);
            }
        });
        tv_alladvocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), AdvocatelistActivity.class);
                startActivity(i);
            }
        });
        im_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), ContactformActivity.class);
                startActivity(i);
            }
        });

        re_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), AddPostActivity.class);
                startActivity(i);
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), AddPostActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        tvlocation.setText(savePref.getAddress());
        getLocationFromAddress(getContext(), savePref.getAddress());
        getBanners();
        get_property_post_package();
        get_advocate();
        get_advertisementbanner();
    }

    private void getBanners() {
        showLoader();


        retrofitService.get_advertisementbanner(savePref.getUserId(),"1",savePref.getcity()).enqueue(new Callback<GetAdvertisementbanner>() {
            @Override
            public void onResponse(Call<GetAdvertisementbanner> call, final retrofit2.Response<GetAdvertisementbanner> response) {
                try {
                    autoSwitcherViewPager.setAdapter(new SliderAdapter(getActivity(), response.body().getJsonData()));
                    indicator.setViewPager(autoSwitcherViewPager);
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }


            }

            @Override
            public void onFailure(Call<GetAdvertisementbanner> call, Throwable t) {
                t.printStackTrace();
                hideLoader();
            }
        });
    }


    private void get_property_post_package() {
        showLoader();

        retrofitService.get_property_post_package(savePref.getUserId()).enqueue(new Callback<GetPropertyPostPackage>() {
            @Override
            public void onResponse(Call<GetPropertyPostPackage> call, Response<GetPropertyPostPackage> response) {
                try {
                    re_package.setAdapter(new PostpackageAdapter(getContext(),response.body().getJsonData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideLoader();

            }

            @Override
            public void onFailure(Call<GetPropertyPostPackage> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

    private void get_advocate() {
        showLoader();
        retrofitService.get_advocate(savePref.getcity()).enqueue(new Callback<GetAdvocate>() {
            @Override
            public void onResponse(Call<GetAdvocate> call, Response<GetAdvocate> response) {
                try {
                    re_advocate.setAdapter(new AdvocateAdapter(getContext(),response.body().getJsonData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<GetAdvocate> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });
    }

    private void get_advertisementbanner() {
        showLoader();
        retrofitService.get_advertisementbanner(savePref.getUserId(),"2",savePref.getcity()).enqueue(new Callback<GetAdvertisementbanner>() {
            @Override
            public void onResponse(Call<GetAdvertisementbanner> call, Response<GetAdvertisementbanner> response) {
                try {
                    autoSwitcherViewPager1.setAdapter(new SliderAdapter(getActivity(), response.body().getJsonData()));
                    indicator1.setViewPager(autoSwitcherViewPager1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<GetAdvertisementbanner> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });
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
//            Log.i("onClick", "getLocationFromAddress: " + address.get(0).getAddressLine(0));
//            Log.i("onClick", "getLocationFromAddress: " + address.get(0).getAdminArea());
//            Log.i("onClick", "getLocationFromAddress: " + address.get(0).getSubLocality());
//            Log.i("onClick", "getLocationFromAddress: " + address.get(0).getCountryName());
//            Log.i("onClick", "getLocationFromAddress: " + address.get(0).getLocale());
//            Log.i("onClick", "getLocationFromAddress: " + address.get(0).getLocality());
            if (address.get(0).getSubLocality() == null) {
                savePref.setArea(address.get(0).getLocality());
                savePref.setstate(address.get(0).getAdminArea());
                savePref.setcity(address.get(0).getLocality());
                savePref.setAddress(address.get(0).getAddressLine(0));

            } else {

                savePref.setArea(address.get(0).getSubLocality());
                savePref.setstate(address.get(0).getAdminArea());
                savePref.setcity(address.get(0).getLocality());
                savePref.setAddress(address.get(0).getAddressLine(0));

            }
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
//            Log.i("onClick", "getLocationFromAddress: " + location.getLatitude() + " " + location.getLongitude());


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private void getUserProfile() {
        showLoader();

        retrofitService.userProfile(SavePref.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {


                final UserProfileModel userModel = response.body();

                UserProfileDetailModel userDetailModel = userModel.getJsonData().get(0);

                if (userDetailModel.getSuccess().equals("1")) {
                    tv_numberproperty.setText(userDetailModel.getTotal_post_property());


                    } else {


                    }


                hideLoader();

            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1004){

            try {
                tvlocation.setText(data.getStringExtra("address"));

                Log.i("onActivityResult", "onActivityResult: "+data.getStringExtra("address"));
                getLocationFromAddress1(getContext(), data.getStringExtra("address"));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public LatLng getLocationFromAddress1(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

            if (address.get(0).getSubLocality() == null) {
                savePref.setArea(address.get(0).getLocality());
                savePref.setstate(address.get(0).getAdminArea());
                savePref.setcity(address.get(0).getLocality());
                savePref.setAddress(address.get(0).getAddressLine(0));

            } else {

                savePref.setArea(address.get(0).getSubLocality());
                savePref.setstate(address.get(0).getAdminArea());
                savePref.setcity(address.get(0).getLocality());
                savePref.setAddress(address.get(0).getAddressLine(0));

            }


        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}