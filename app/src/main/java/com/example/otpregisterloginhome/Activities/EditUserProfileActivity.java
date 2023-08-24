package com.example.otpregisterloginhome.Activities;


import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.Adapters.CityAdapter;
import com.example.otpregisterloginhome.Adapters.StateAdapter1;
import com.example.otpregisterloginhome.Adapters.StringArrayAdapter;
import com.example.otpregisterloginhome.ModelClasses.GetCity;
import com.example.otpregisterloginhome.ModelClasses.GetCity_Inner;
import com.example.otpregisterloginhome.ModelClasses.GetState;
import com.example.otpregisterloginhome.ModelClasses.GetState_Inner;
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
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;
import com.makeramen.roundedimageview.RoundedImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserProfileActivity extends RuntimePermissionsActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    LinearLayout nointernet;
    NetworkStateReceiver networkStateReceiver;
    TextView updateBtn;
    RadioButton rb_male,rb_female;

    EditText emailidet, usernameet, phoneet;
    ImageView addimage;
    RoundedImageView userimageiv;
    String  stateid = "", cityid = "";
    Spinner spinner_cat;
    ArrayList<String> arrayListcat=new ArrayList<>();
    String type="Male";

    String part_image;
    Boolean imgselected = false;
    SavePref savePref;
    LinearLayout loaderlayout;
    RetrofitService retrofitService;
    UserProfileDetailModel userDetailModel;
    TextView tvnameimage;
    MultipartBody.Part partImage;
    int passwordNotVisible = 1;

    ArrayList<GetState_Inner> getState_inners = new ArrayList<>();
    ArrayList<GetCity_Inner> getCity_inners = new ArrayList<>();

    EditText et_address,et_state,et_city,et_loc,et_flat,et_aadhar;

    @Override
    public void networkAvailable() {
        nointernet.setVisibility(View.GONE);
    }

    @Override
    public void networkUnavailable() {
        nointernet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        switch (requestCode) {
            case GALLERY_REQUEST_PERMISSIONS:
                openGallery();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode) {

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
        setContentView(R.layout.activity_edit_user_profile);

        nointernet = findViewById(R.id.nointernet);
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("My Profile");

        tvnameimage = findViewById(R.id.tvnameimage);

        savePref = new SavePref(this);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);
        et_address = findViewById(R.id.et_address);

        phoneet = findViewById(R.id.phoneet);
        et_state = findViewById(R.id.et_state);
        et_city = findViewById(R.id.et_city);
        et_loc = findViewById(R.id.et_loc);
        et_flat = findViewById(R.id.et_flat);
        et_aadhar = findViewById(R.id.et_aadhar);

        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);

        updateBtn = findViewById(R.id.updateBtn);
        emailidet = findViewById(R.id.emailidet);

        usernameet = findViewById(R.id.usernameet);
        addimage = findViewById(R.id.addimage);
        userimageiv = findViewById(R.id.userimageiv);
        spinner_cat = findViewById(R.id.spinner_cat);


        addimage = findViewById(R.id.addimage);


        rb_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rb_male.isChecked()) {
                    type = "Male";
                    //type_saveas.setVisibility(View.GONE);
                }
            }
        });

        rb_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (rb_female.isChecked()) {
                    type = "Female";
                    //type_saveas.setVisibility(View.GONE);
                }
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                startGalleryCheck();
            }
        });


        userimageiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                startGalleryCheck();
            }
        });

        tvnameimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                startGalleryCheck();
            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);


                if (validation()) {

                        profileupdatewithImage();


                }
            }
        });



        arrayListcat.add("Gen");
        arrayListcat.add("OBC");
        arrayListcat.add("ST");
        arrayListcat.add("SC");




        StringArrayAdapter adapter4 = new StringArrayAdapter(EditUserProfileActivity.this,
                R.layout.single_spinnerlist, arrayListcat);
        spinner_cat.setAdapter(adapter4);

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
                    Toast.makeText(EditUserProfileActivity.this, "Please Enter State First", Toast.LENGTH_SHORT).show();
                } else {
                    opencityDialog();
                }

            }
        });






        getUserProfile();

    }

    private void openGallery() {


        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(300, 300)
                .start(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                String path = resultUri.getPath();
                part_image = path;
                Glide.with(this)
                        .load(resultUri)
                        .placeholder(R.drawable.userimage)
                        .into(userimageiv);
                userimageiv.setVisibility(View.VISIBLE);
                 imgselected = true;
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(resultUri,
                        imageprojection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    part_image = cursor.getString(indexImage);
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

            }
        }
    }

    private boolean validation() {


        if (TextUtils.isEmpty(usernameet.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid First Name", Toast.LENGTH_SHORT).show();

            return false;
        } else  if (TextUtils.isEmpty(et_loc.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Location", Toast.LENGTH_SHORT).show();

            return false;
        } else  if (TextUtils.isEmpty(et_flat.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Flat", Toast.LENGTH_SHORT).show();

            return false;
        } else  if (TextUtils.isEmpty(et_city.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid City", Toast.LENGTH_SHORT).show();

            return false;
        } else  if (TextUtils.isEmpty(et_state.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid State", Toast.LENGTH_SHORT).show();

            return false;
        } else  if (TextUtils.isEmpty(et_aadhar.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Aadhar", Toast.LENGTH_SHORT).show();

            return false;
        } else  if (TextUtils.isEmpty(et_address.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Address", Toast.LENGTH_SHORT).show();

            return false;
        } else if (SplashActivity.isValidEmail(emailidet.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Valid Email Id", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }


    }

    private void profileupdatewithImage() {
        showLoader();
        Random r = new Random();
        int randomNumber = r.nextInt(1000000000);
        if(part_image!=null){
            File imagefile = new File(part_image);
            RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), imagefile);
             partImage = MultipartBody.Part.createFormData("imageurl", randomNumber+".jpg", image);
        }




        retrofitService.userProfileUpdateWithImage(
                getRequestBody(SavePref.getUserId()),
                getRequestBody(usernameet.getText().toString()),
                getRequestBody(emailidet.getText().toString()),
                getRequestBody(userDetailModel.gpasswordet()),
                getRequestBody(type),
                getRequestBody(spinner_cat.getSelectedItem().toString()),
                getRequestBody(et_address.getText().toString()),
                getRequestBody(et_state.getText().toString()),
                getRequestBody(et_city.getText().toString()),
                getRequestBody(et_loc.getText().toString()),
                getRequestBody(et_flat.getText().toString()),
                getRequestBody(et_aadhar.getText().toString()),
                partImage
        ).enqueue(new Callback<ProfileUpdateModel>() {
            @Override
            public void onResponse(Call<ProfileUpdateModel> call, Response<ProfileUpdateModel> response) {
                try {
                    final ProfileUpdateModel registerModel = response.body();
                    ProfileUpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(EditUserProfileActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {

                        finish();

                    } else {
                        hideLoader();
                    }


                } catch (Exception e) {
                    hideLoader();
                }


            }

            @Override
            public void onFailure(Call<ProfileUpdateModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });

    }



    public RequestBody getRequestBody(String s) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), s);
        return requestBody;
    }

    private void getUserProfile() {
        showLoader();

        Log.i("getUserProfile", "getUserProfile: "+SavePref.getUserId());
        retrofitService.userProfile(SavePref.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                try {
                    final UserProfileModel userModel = response.body();
                    userDetailModel = userModel.getJsonData().get(0);
                    usernameet.setText(userDetailModel.getUser_name());
                    emailidet.setText(userDetailModel.getEmail());

                    phoneet.setText(userDetailModel.getPhone());
                    et_address.setText(userDetailModel.getAddress());
                    et_state.setText(userDetailModel.getUserState());
                    et_city.setText(userDetailModel.getUserCity());
                    et_loc.setText(userDetailModel.getAreaOfLocation());
                    et_flat.setText(userDetailModel.getFlatNo());
                    et_aadhar.setText(userDetailModel.getAadharNumber());


                    if (userDetailModel.getGender().equalsIgnoreCase("Male")){
                        rb_male.setChecked(true);
                    }else{
                        rb_female.setChecked(true);
                    }

                    if (userDetailModel.getImageurl().equals("")) {
                        char letter = userDetailModel.getUser_name().charAt(0);
                        tvnameimage.setText(letter + "");
                        tvnameimage.setVisibility(View.VISIBLE);
                        userimageiv.setVisibility(View.GONE);
                    } else {
                        Glide.with(EditUserProfileActivity.this)
                                .load(userDetailModel.getImageurl())
                                .placeholder(R.drawable.userimage)
                                .into(userimageiv);
                        tvnameimage.setVisibility(View.GONE);
                        userimageiv.setVisibility(View.VISIBLE);
                    }


                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
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


}