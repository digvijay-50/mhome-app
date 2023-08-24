package com.example.otpregisterloginhome.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.Fragments.HomeFragment;
import com.example.otpregisterloginhome.Fragments.PackageFragment;
import com.example.otpregisterloginhome.Fragments.ProfileFragment;
import com.example.otpregisterloginhome.Fragments.ShortlistFragment;
import com.example.otpregisterloginhome.ModelClasses.GetNotification;
import com.example.otpregisterloginhome.ModelClasses.UpdateDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UpdateModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileModel;
import com.example.otpregisterloginhome.NetworkUtils.NetworkStateReceiver;
import com.example.otpregisterloginhome.Permission.PermissionHandler;
import com.example.otpregisterloginhome.Permission.Permissions;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.Constant;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewHomeActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
        }
    };


    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    ShortlistFragment shortlistFragment;
    PackageFragment packageFragment;
    RelativeLayout myprofilerl;
    RelativeLayout reachusrl;
    RelativeLayout changepasswordrl;
    RelativeLayout logoutll;
    ImageView drawericon;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    SavePref savePref;
    LinearLayout loaderlayout;
    RetrofitService retrofitService;
    RelativeLayout contactusrl;
    RelativeLayout aboutusrl;
    RelativeLayout privacypolicyrl;
    RelativeLayout termsandconditionsrl;
    RelativeLayout refundrl;
    RelativeLayout rateapprl;
    RelativeLayout shareapprl,r_noti;
    RoundedImageView userimage;
    TextView usernametv,tv_top;
    TextView tvnameimage,notificatiotv;


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID && grantResults.length > 0 && grantResults[0] == 0) {
            getLastLocation();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private void getLastLocation() {
        if (!checkPermissions()) {
            requestPermissionsLocation();
        } else if (isLocationEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(Task<Location> task) {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                        return;
                    }

                    if (savePref.getLocationLattHome().equalsIgnoreCase("")) {
                        savePref.setLocationLattHome(location.getLatitude() + "");
                        savePref.setLocationLonggHome(location.getLongitude() + "");
                    }

                }
            });
        } else {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show();
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        }
    }

    private void requestPermissionsLocation() {
        Permissions.check(this, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, getString(R.string.msg_rationale), new Permissions.Options().setRationaleDialogTitle(getString(R.string.txt_info)).setSettingsDialogTitle(getString(R.string.txt_warning)), new PermissionHandler() {
            @Override
            public void onGranted() {
                getLastLocation();
            }
            @Override
            public void onDenied(Context context, ArrayList<String> arrayList) {
                getLastLocation();
            }
        });
    }




    @Override
    public void networkAvailable() {
        nointernet.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        nointernet.setVisibility(View.VISIBLE);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


        userimage = findViewById(R.id.userimage);
        usernametv = findViewById(R.id.usernametv);
        tvnameimage = findViewById(R.id.tvnameimage);
        r_noti = findViewById(R.id.r_noti);
        notificatiotv = findViewById(R.id.notificatiotv);
        tv_top = findViewById(R.id.tv_top);

        tv_top.setText("Home");

        savePref = new SavePref(this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        getLastLocation();

        drawericon = findViewById(R.id.drawericon);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        setupDrawerToggle();
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        drawericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                mDrawerLayout.setDrawerListener(mDrawerToggle);
                setupDrawerToggle();
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        viewPager = findViewById(R.id.viewpager);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewPager.setCurrentItem(0);
                                savePref.setpage(0);
                                tv_top.setText("Home");
                                break;

                            case R.id.action_short:
                                savePref.setpage(1);
                                viewPager.setCurrentItem(1);
                                tv_top.setText("Shortlisted");
                                break;

                            case R.id.action_package:
                                savePref.setpage(2);
                                viewPager.setCurrentItem(2);
                                tv_top.setText("Packages");
                                break;

                            case R.id.action_profile:
                                savePref.setpage(3);
                                viewPager.setCurrentItem(3);
                                tv_top.setText("Account");
                                break;

                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position==0){
                    tv_top.setText("Home");
                }else  if (position==1){
                    tv_top.setText("Shortlisted");
                }else  if (position==2){
                    tv_top.setText("Packages");
                }else  if (position==3){
                    tv_top.setText("Account");
                }else
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setCurrentItem(savePref.getpage());
        myprofilerl = findViewById(R.id.myprofilerl);
        logoutll = findViewById(R.id.logoutll);


        r_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewHomeActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });


        myprofilerl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                Intent i = new Intent(NewHomeActivity.this, EditUserProfileActivity.class);
                startActivity(i);
            }
        });

        logoutll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                final Dialog dialog = new Dialog(NewHomeActivity.this);
                dialog.setContentView(R.layout.dialog_logout);
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
                        userLogout();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        myprofilerl = findViewById(R.id.myprofilerl);
        changepasswordrl = findViewById(R.id.changepasswordrl);

        changepasswordrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(NewHomeActivity.this, ChangePasswordActivity.class);
                startActivity(i);
            }
        });

        reachusrl = findViewById(R.id.reachusrl);

        reachusrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                final Dialog dialog = new Dialog(NewHomeActivity.this);
                dialog.setContentView(R.layout.dialog_contacts);
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView cancelbtn = dialog.findViewById(R.id.cancelbtn);
                cancelbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SplashActivity.disablefor1sec(v);
                        dialog.dismiss();
                    }
                });

                final CardView phonecv = dialog.findViewById(R.id.phonecv);
                final CardView emailcv = dialog.findViewById(R.id.emailcv);


                final TextView phonetv = dialog.findViewById(R.id.phonetv);
                final TextView emailtv = dialog.findViewById(R.id.emailtv);
                phonetv.setText(SplashActivity.settingsDetailModel.getApp_contact());
                emailtv.setText(SplashActivity.settingsDetailModel.getApp_email());
                phonecv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SplashActivity.disablefor1sec(v);
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", SplashActivity.settingsDetailModel.getApp_contact(), null)));
                    }
                });

                emailcv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SplashActivity.disablefor1sec(v);
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + SplashActivity.settingsDetailModel.getApp_email()));
                            intent.putExtra(Intent.EXTRA_SUBJECT, "");
                            intent.putExtra(Intent.EXTRA_TEXT, "");
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(NewHomeActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });


        contactusrl = findViewById(R.id.contactusrl);
        contactusrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(NewHomeActivity.this, SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_contact_us());
                intent.putExtra("header", "Contact Us");
                startActivity(intent);

            }
        });

        aboutusrl = findViewById(R.id.aboutusrl);
        aboutusrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                Intent intent = new Intent(NewHomeActivity.this, SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_about_us());
                intent.putExtra("header", "About Us");
                startActivity(intent);
            }
        });

        privacypolicyrl = findViewById(R.id.privacypolicyrl);
        privacypolicyrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(NewHomeActivity.this, SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_privacy_policy());
                intent.putExtra("header", "Privacy Policy");
                startActivity(intent);
            }
        });

        termsandconditionsrl = findViewById(R.id.termsandconditionsrl);
        termsandconditionsrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(NewHomeActivity.this, SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_terms_condition());
                intent.putExtra("header", "Terms & Conditions");
                startActivity(intent);
            }
        });

        refundrl = findViewById(R.id.refundrl);
        refundrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(NewHomeActivity.this, SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_cancellation_refund());
                intent.putExtra("header", "Refund Policy");
                startActivity(intent);
            }
        });

        rateapprl = findViewById(R.id.rateapprl);
        rateapprl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent irate = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + NewHomeActivity.this.getPackageName()));
                Intent new_intent = Intent.createChooser(irate, "Rate Us");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(new_intent);
            }
        });


        shareapprl = findViewById(R.id.shareapprl);
        shareapprl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + NewHomeActivity.this.getPackageName();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Share Via"));
                } catch (Exception e) {
                }
            }
        });


    }


    private void userLogout() {
        showLoader();
        retrofitService.userLogout(
                SavePref.getUserId()
        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(NewHomeActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {
                        SavePref.setLoggedIn(false);
                        Intent intent = new Intent(NewHomeActivity.this, MobileRegisterActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                    }
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<UpdateModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        shortlistFragment = new ShortlistFragment();
        packageFragment = new PackageFragment();
        profileFragment = new ProfileFragment();


        adapter.addFragment(homeFragment);
        adapter.addFragment(shortlistFragment);
        adapter.addFragment(packageFragment);
        adapter.addFragment(profileFragment);


        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }


    private void getUserProfile() {
        showLoader();

        retrofitService.userProfile(SavePref.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {


                final UserProfileModel userModel = response.body();

                UserProfileDetailModel userDetailModel = userModel.getJsonData().get(0);

                if (userDetailModel.getSuccess().equals("1")) {

                    usernametv.setText(userDetailModel.getUser_name());

                    savePref.setproperty(userDetailModel.getTotal_post_property());

                    if (userDetailModel.getImageurl().equals("")) {
                        char letter = userDetailModel.getUser_name().charAt(0);
                        tvnameimage.setText(letter + "");
                        savePref.setproperty(userDetailModel.getTotal_post_property());

                        tvnameimage.setVisibility(View.VISIBLE);
                        userimage.setVisibility(View.GONE);

                    } else {

                        Glide.with(NewHomeActivity.this)
                                .load(userDetailModel.getImageurl())
                                .placeholder(R.drawable.userimage)
                                .into(userimage);

                        tvnameimage.setVisibility(View.GONE);
                        userimage.setVisibility(View.VISIBLE);
                    }

                } else {
                    Toast.makeText(NewHomeActivity.this, "" + userDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    SavePref.setLoggedIn(false);
                    Intent intent = new Intent(NewHomeActivity.this, MobileRegisterActivity.class);
                    startActivity(intent);
                    finish();


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
    protected void onResume() {
        super.onResume();
        getnotifcation();
        getUserProfile();
        Constant.SearchlookingTo="";
        Constant.Typesofproperty="";
        Constant.configuration="";
        Constant.Floordetail="";
        Constant.Furnished="";
        Constant.Avaliablefor="";
        Constant.property="";
        Constant.pricerange="";

    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem()==0){
            final Dialog dialog = new Dialog(NewHomeActivity.this);
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

        }else{
            viewPager.setCurrentItem(0);
        }


    }

        public class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment) {
                mFragmentList.add(fragment);
            }

        }


    private void getnotifcation() {
        //showLoader();
        retrofitService.get_notification(savePref.getUserId()).enqueue(new Callback<GetNotification>() {
            @Override
            public void onResponse(Call<GetNotification> call, final retrofit2.Response<GetNotification> response) {
                try {


                    int count= Integer.parseInt(response.body().getTotalCount());
                    ///    Log.i("onResponse" ,"onResponse: "+count);
                    int count1= Integer.parseInt(savePref.getnoticount());
                    if (!savePref.getnoticount().equalsIgnoreCase(response.body().getTotalCount())){
                        notificatiotv.setVisibility(View.VISIBLE);
                        int count2=count-count1;
                        if (count2>=0){
                            notificatiotv.setText(count2 + "");
                        }else{
                            notificatiotv.setText("0");
                        }

                    }else{
                        notificatiotv.setVisibility(View.GONE);
                    }

                    if (savePref.getnoticount().equalsIgnoreCase("0")){
                        notificatiotv.setText(response.body().getTotalCount() + "");
                    }
                    //  hideLoader();
                } catch (Exception e) {
                    //   hideLoader();
                }
            }

            @Override
            public void onFailure(Call<GetNotification> call, Throwable t) {
                t.printStackTrace();
                //  hideLoader();
            }
        });
    }

}