package com.propertybuysell.mhome.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.propertybuysell.mhome.Activities.AddPostActivity;
import com.propertybuysell.mhome.Activities.AdvocatelistActivity;
import com.propertybuysell.mhome.Activities.ContactformActivity;

import com.propertybuysell.mhome.Activities.MainSelectlocationActivity;
import com.propertybuysell.mhome.Activities.PostedpropertyActivity;
import com.propertybuysell.mhome.Activities.PropertylistadsbottomActivity;
import com.propertybuysell.mhome.Activities.PropertylistadstopActivity;
import com.propertybuysell.mhome.Activities.PropertylistbhkActivity;
import com.propertybuysell.mhome.Activities.PropertylistcityActivity;
import com.propertybuysell.mhome.Activities.SearchpropertyActivity;
import com.propertybuysell.mhome.Activities.SplashActivity;
import com.propertybuysell.mhome.Adapters.AdvocateAdapter;
import com.propertybuysell.mhome.Adapters.PostpackageAdapter;
import com.propertybuysell.mhome.Adapters.SliderAdapter;
import com.propertybuysell.mhome.ModelClasses.GetAdvertisementbanner;
import com.propertybuysell.mhome.ModelClasses.GetAdvocate;
import com.propertybuysell.mhome.ModelClasses.GetCityList_Inner;
import com.propertybuysell.mhome.ModelClasses.GetHomePropertyCityList;
import com.propertybuysell.mhome.ModelClasses.GetPropertyPostPackage;
import com.propertybuysell.mhome.ModelClasses.Gethomeads;
import com.propertybuysell.mhome.ModelClasses.Gethomeads_Inner;
import com.propertybuysell.mhome.ModelClasses.Gethomebottomads;
import com.propertybuysell.mhome.ModelClasses.Gethomebottomads_Inner;
import com.propertybuysell.mhome.ModelClasses.UserProfileDetailModel;
import com.propertybuysell.mhome.ModelClasses.UserProfileModel;
import com.propertybuysell.mhome.OtherUtils.AutoSwitcherViewPager;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.Constant;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {


    SavePref savePref;

    LinearLayout loaderlayout;

    RelativeLayout re_post, r_location;

    LinearLayout rl_loc,llinsta,llfacebook,lltwiter,llyoutube;
    RetrofitService retrofitService;
    RecyclerView re_package, re_advocate, re_topads, re_bottomads, re_bhk,re_city;
    ImageView im_contact;
    LinearLayout llchat;
    ArrayList<String> bhklist = new ArrayList<>();

    LinearLayout im_p_property;
    AutoSwitcherViewPager autoSwitcherViewPager;
    CircleIndicator indicator;

    AutoSwitcherViewPager autoSwitcherViewPager1;
    CircleIndicator indicator1;
    ImageView im_search;
    EditText mSearchBoxView;
    TextView tv_post, tv_alladvocate, tvlocation, tv_numberproperty, tv_toppost;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        autoSwitcherViewPager = view.findViewById(R.id.autoSwitcherViewPager);
        indicator = view.findViewById(R.id.indicator);
        autoSwitcherViewPager1 = view.findViewById(R.id.autoSwitcherViewPager1);
        indicator1 = view.findViewById(R.id.indicator1);
        re_topads = view.findViewById(R.id.re_topads);
        re_bottomads = view.findViewById(R.id.re_bottomads);
        re_bhk = view.findViewById(R.id.re_bhk);
        re_city = view.findViewById(R.id.re_city);
        lltwiter = view.findViewById(R.id.lltwiter);
        llyoutube = view.findViewById(R.id.llyoutube);

        llinsta = view.findViewById(R.id.llinsta);
        llfacebook = view.findViewById(R.id.llfacebook);

        savePref = new SavePref(getActivity());
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = view.findViewById(R.id.loaderlayout);
        im_search = view.findViewById(R.id.im_search);
        tv_post = view.findViewById(R.id.tv_post);
        llchat = view.findViewById(R.id.llchat);

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
        re_package.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        re_advocate.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        re_topads.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        re_bottomads.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        re_bhk.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        re_city.setLayoutManager(new GridLayoutManager(getContext(), 3));


        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);


        String text = "<font color=#000000>Want to</font> <font color=#cc0029> Sell/Rent</font></font> <font color=#000000> Your Property?</font>";
        tv_toppost.setText(Html.fromHtml(text));



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                getBanners();
                getUserProfile();
                get_property_post_package();
                get_advertisementbanner();
                get_advocate();
                get_home_property_ads_top_list();
                get_home_property_ads_bottom_list();
                get_home_property_city_list();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        bhklist.add("1 BHK");
        bhklist.add("2 BHK");
        bhklist.add("3 BHK");
        bhklist.add("4 BHK");
        bhklist.add("5 BHK");
        bhklist.add("Other");

        llinsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(SplashActivity.settingsDetailModel.getApp_instagram()));
                startActivity(i);
            }
        });

        lltwiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(SplashActivity.settingsDetailModel.getApp_twitter()));
                startActivity(i);
            }
        });

        llfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(SplashActivity.settingsDetailModel.getApp_facebook()));
                startActivity(i);
            }
        });
        llyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(SplashActivity.settingsDetailModel.getApp_youtube()));
                startActivity(i);
            }
        });

        re_bhk.setAdapter(new bhkAdapter(getContext(), bhklist));
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });

        im_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SearchpropertyActivity.class);
                startActivity(i);
            }
        });

        rl_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MainSelectlocationActivity.class);

//                Log.i("onActivityResult", "onClick: " + tvlocation.getText().toString());
                i.putExtra("address", tvlocation.getText().toString());
                startActivity(i);

            }
        });
        r_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SearchpropertyActivity.class);
                startActivity(i);

            }
        });

        mSearchBoxView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SearchpropertyActivity.class);
                startActivity(i);

            }
        });


//        Log.i("onCreateView", "onCreateView: " + savePref.getproperty());
        getUserProfile();
        im_p_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PostedpropertyActivity.class);
                startActivity(i);
            }
        });
        tv_alladvocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AdvocatelistActivity.class);
                startActivity(i);
            }
        });
        im_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ContactformActivity.class);
                startActivity(i);
            }
        });

        re_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    if (checkpurchase.equalsIgnoreCase("0")) {
                Intent i = new Intent(getContext(), AddPostActivity.class);
                startActivity(i);
//                } else {
//                    Toast.makeText(getContext(), "Please Purchsed Plan", Toast.LENGTH_SHORT).show();
//
//                }


            }
        });

//        tv_post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(getContext(), AddPostActivity.class);
//                startActivity(i);
//            }
//        });

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
        get_home_property_ads_top_list();
        get_home_property_ads_bottom_list();
        get_home_property_city_list();
        get_advertisementbanner();
    }

    private void getBanners() {
        showLoader();


        retrofitService.get_advertisementbanner(savePref.getUserId(), "1", savePref.getcity()).enqueue(new Callback<GetAdvertisementbanner>() {
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
                    re_package.setAdapter(new PostpackageAdapter(getContext(), response.body().getJsonData()));
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
                    re_advocate.setAdapter(new AdvocateAdapter(getContext(), response.body().getJsonData()));
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

    private void get_home_property_ads_top_list() {
        showLoader();
        retrofitService.get_home_property_ads_top_list().enqueue(new Callback<Gethomeads>() {
            @Override
            public void onResponse(Call<Gethomeads> call, Response<Gethomeads> response) {
                try {
                    re_topads.setAdapter(new HomeadsAdapter(getContext(), response.body().getJsonData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<Gethomeads> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });
    }

    private void get_home_property_ads_bottom_list() {
        showLoader();
        retrofitService.get_home_property_ads_bottom_list().enqueue(new Callback<Gethomebottomads>() {
            @Override
            public void onResponse(Call<Gethomebottomads> call, Response<Gethomebottomads> response) {
                try {
                    re_bottomads.setAdapter(new HomeadsbottomAdapter(getContext(), response.body().getJsonData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hideLoader();
            }

            @Override
            public void onFailure(Call<Gethomebottomads> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });
    }

    private void get_advertisementbanner() {
        showLoader();
        retrofitService.get_advertisementbanner(savePref.getUserId(), "2", savePref.getcity()).enqueue(new Callback<GetAdvertisementbanner>() {
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


                if (userDetailModel.getActive_post_package().equalsIgnoreCase("1")) {
                    if (userDetailModel.getRemaining_post().equalsIgnoreCase("0")) {
                        Constant.checkpurchase = "1";
                    } else {
                        Constant.checkpurchase = "0";
                    }
                } else {
                    Constant.checkpurchase = "1";
                }

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
        if (requestCode == 1004) {

            try {
                tvlocation.setText(data.getStringExtra("address"));

//                Log.i("onActivityResult", "onActivityResult: " + data.getStringExtra("address"));
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

    private void openWhatsApp() {
        try {
            String phonenumber = SplashActivity.settingsDetailModel.getApp_contact();
            Log.i("openWhatsApp", "openWhatsApp: " + phonenumber);
            if (whatsappInstalledOrNot("com.whatsapp")) {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("whatsapp://send/?text=&phone=" + phonenumber)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "Please install WhatsApp", Toast.LENGTH_SHORT).show();
                } catch (Exception e2) {
                    Toast.makeText(getContext(), "Error while encoding your text message", Toast.LENGTH_SHORT).show();
                }
            } else if (whatsappInstalledOrNot("com.whatsapp.w4b")) {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("whatsapp://send/?text=&phone=" + phonenumber)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "Please install WhatsApp", Toast.LENGTH_SHORT).show();
                } catch (Exception e2) {
                    Toast.makeText(getContext(), "Error while encoding your text message", Toast.LENGTH_SHORT).show();
                }
            } else {
                Intent goToMarket = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.whatsapp"));
                Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                startActivity(goToMarket);
            }
        } catch (Exception e3) {
        }
    }


    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getContext().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;


    }


    private class HomeadsAdapter extends RecyclerView.Adapter<HomeadsAdapter.ViewHolder> {
        Context context;
        List<Gethomeads_Inner> gethomeads_inners;

        public HomeadsAdapter(Context context, List<Gethomeads_Inner> gethomeads_inners) {
            this.context = context;
            this.gethomeads_inners = gethomeads_inners;
        }

        @NonNull
        @Override
        public HomeadsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.single_adslist, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeadsAdapter.ViewHolder holder, int position) {

            Glide.with(context)
                    .load(gethomeads_inners.get(position).getHomePropertyTopImage())
                    .placeholder(R.drawable.no_image)
                    .into(holder.im_area);

            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(), PropertylistadstopActivity.class);
                    i.putExtra("property_configuration",gethomeads_inners.get(position).getHomePropertyAdsTopId());
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return gethomeads_inners.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RoundedImageView im_area;
            LinearLayout llmain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                im_area = itemView.findViewById(R.id.im_area);
                llmain = itemView.findViewById(R.id.llmain);
            }
        }
    }

    private class HomeadsbottomAdapter extends RecyclerView.Adapter<HomeadsbottomAdapter.ViewHolder> {
        Context context;
        List<Gethomebottomads_Inner> gethomeads_inners;

        public HomeadsbottomAdapter(Context context, List<Gethomebottomads_Inner> gethomeads_inners) {
            this.context = context;
            this.gethomeads_inners = gethomeads_inners;
        }

        @NonNull
        @Override
        public HomeadsbottomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.single_adslist, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeadsbottomAdapter.ViewHolder holder, int position) {

            Glide.with(context)
                    .load(gethomeads_inners.get(position).getHomePropertyBottomImage())
                    .placeholder(R.drawable.no_image)
                    .into(holder.im_area);

            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(), PropertylistadsbottomActivity.class);
                    i.putExtra("property_configuration",gethomeads_inners.get(position).getHomePropertyAdsBottomId());
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return gethomeads_inners.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RoundedImageView im_area;
            LinearLayout llmain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                im_area = itemView.findViewById(R.id.im_area);
                llmain = itemView.findViewById(R.id.llmain);
            }
        }
    }

    private class bhkAdapter extends RecyclerView.Adapter<bhkAdapter.ViewHolder> {
        Context context;
        List<String> gethomeads_inners;

        public bhkAdapter(Context context, ArrayList<String> gethomeads_inners) {
            this.context = context;
            this.gethomeads_inners = gethomeads_inners;
        }

        @NonNull
        @Override
        public bhkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.single_bhklist, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull bhkAdapter.ViewHolder holder, int position) {

            holder.tv_bhk.setText(gethomeads_inners.get(position));

            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), PropertylistbhkActivity.class);
                    i.putExtra("property_configuration",gethomeads_inners.get(position));
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return gethomeads_inners.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            RoundedImageView im_area;
            LinearLayout llmain;
            TextView tv_bhk;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                im_area = itemView.findViewById(R.id.im_area);
                tv_bhk = itemView.findViewById(R.id.tv_bhk);
                llmain = itemView.findViewById(R.id.llmain);
            }
        }
    }

    public void get_home_property_city_list(){
        retrofitService.get_home_property_city_list().enqueue(new Callback<GetHomePropertyCityList>() {
            @Override
            public void onResponse(Call<GetHomePropertyCityList> call, Response<GetHomePropertyCityList> response) {
                re_city.setAdapter(new CityHomeAdapter(getContext(),response.body().getJsonData()));
            }

            @Override
            public void onFailure(Call<GetHomePropertyCityList> call, Throwable t) {

            }
        });
    }


    public class CityHomeAdapter extends RecyclerView.Adapter <CityHomeAdapter.ViewHolder>{

        Context context;
        List<GetCityList_Inner> getCityList_inners;
        public CityHomeAdapter(Context context, List<GetCityList_Inner> getCityList_inners) {
            this.context=context;
            this.getCityList_inners=getCityList_inners;
        }

        @NonNull
        @Override
        public CityHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(context).inflate(R.layout.single_homecitylist,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CityHomeAdapter.ViewHolder holder, int position) {

            holder.tv_cityname.setText(getCityList_inners.get(position).getCity());

            Glide.with(context)
                    .load(getCityList_inners.get(position).getCityImage())
                    .placeholder(R.drawable.no_image)
                    .into(holder.im_city);

            holder.llmain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getContext(), PropertylistcityActivity.class);
                    i.putExtra("property_configuration",getCityList_inners.get(position).getHomePropertyCityId());
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return getCityList_inners.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_cityname;
            ImageView im_city;
            LinearLayout llmain;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_cityname=itemView.findViewById(R.id.tv_cityname);
                im_city=itemView.findViewById(R.id.im_city);
                llmain=itemView.findViewById(R.id.llmain);
            }
        }
    }
}