package com.example.otpregisterloginhome.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.Activities.ChangePasswordActivity;
import com.example.otpregisterloginhome.Activities.EditUserProfileActivity;
import com.example.otpregisterloginhome.Activities.MobileRegisterActivity;
import com.example.otpregisterloginhome.Activities.SettingDataActivity;
import com.example.otpregisterloginhome.Activities.SplashActivity;
import com.example.otpregisterloginhome.Activities.ViewPropertylogActivity;
import com.example.otpregisterloginhome.ModelClasses.UpdateDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UpdateModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileModel;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;
import com.makeramen.roundedimageview.RoundedImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {


    RoundedImageView userimage;
    TextView usernametv;
    TextView tvnameimage;
    RetrofitService retrofitService;
    SavePref savePref;
    TextView emailidtv;
    TextView phonetv;
    ImageView editiv;
    RelativeLayout myprofilerl;
    RelativeLayout myprofilerl1,r_log;
    TextView logoutcv;
    LinearLayout loaderlayout;
    RelativeLayout privacypolicyrl;
    RelativeLayout termsandconditionsrl;
    RelativeLayout aboutus;
    RelativeLayout contactusrl;
    RelativeLayout reachusrl;

    RelativeLayout refund;
    RelativeLayout changepasswordrl;
    RelativeLayout rateapprl;
    RelativeLayout shareapprl;


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


        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);

        privacypolicyrl = view.findViewById(R.id.privacypolicyrl);
        shareapprl = view.findViewById(R.id.shareapprl);
        contactusrl = view.findViewById(R.id.contactusrl);
        reachusrl = view.findViewById(R.id.reachusrl);
        r_log = view.findViewById(R.id.r_log);

        refund = view.findViewById(R.id.refundrl);
        termsandconditionsrl = view.findViewById(R.id.termsandconditionsrl);
        aboutus = view.findViewById(R.id.aboutusrl);
        rateapprl = view.findViewById(R.id.rateapprl);
        changepasswordrl = view.findViewById(R.id.changepasswordrl);
        loaderlayout = view.findViewById(R.id.loaderlayout);
        emailidtv = view.findViewById(R.id.emailidtv);
        phonetv = view.findViewById(R.id.phonetv);
        logoutcv = view.findViewById(R.id.logoutcv);
        myprofilerl = view.findViewById(R.id.myprofilerl);
        editiv = view.findViewById(R.id.editiv);
        myprofilerl1 = view.findViewById(R.id.myprofilerl1);
        userimage = view.findViewById(R.id.userimage);
        usernametv = view.findViewById(R.id.usernametv);
        tvnameimage = view.findViewById(R.id.tvnameimage);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {

                getUserProfile();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(getActivity());

        editiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(getContext(), EditUserProfileActivity.class);
                startActivity(i);
            }
        });

        changepasswordrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(i);
            }
        });


        myprofilerl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(getContext(), EditUserProfileActivity.class);
                startActivity(i);
            }
        });

        r_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(getContext(), ViewPropertylogActivity.class);
                startActivity(i);
            }
        });


        logoutcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                final Dialog dialog = new Dialog(getActivity());
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

        myprofilerl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(getContext(), EditUserProfileActivity.class);
                startActivity(i);
            }
        });


        myprofilerl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(getContext(), EditUserProfileActivity.class);
                startActivity(i);
            }
        });


        refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(getContext(), SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_cancellation_refund());
                intent.putExtra("header", "Refund Policy");
                startActivity(intent);
            }
        });

        termsandconditionsrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(getContext(), SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_terms_condition());
                intent.putExtra("header", "Terms & Conditions");
                startActivity(intent);
            }
        });

        contactusrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(getContext(), SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_contact_us());
                intent.putExtra("header", "Contact Us");
                startActivity(intent);
            }
        });


        reachusrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                final Dialog dialog = new Dialog(getActivity());
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
                            Toast.makeText(getActivity(), "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });

        rateapprl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent irate = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName()));
                Intent new_intent = Intent.createChooser(irate, "Rate Us");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(new_intent);
            }
        });


        privacypolicyrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent intent = new Intent(getContext(), SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_privacy_policy());
                intent.putExtra("header", "Privacy Policy");
                startActivity(intent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);

                Intent intent = new Intent(getContext(), SettingDataActivity.class);
                intent.putExtra("text", SplashActivity.settingsDetailModel.getApp_about_us());
                intent.putExtra("header", "About Us");
                startActivity(intent);
            }
        });

        shareapprl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Share Via"));
                } catch (Exception e) {
                }
            }
        });


        myprofilerl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                Intent i = new Intent(getContext(), EditUserProfileActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserProfile();
    }

    private void getUserProfile() {
        showLoader();

        retrofitService.userProfile(SavePref.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                try {
                    final UserProfileModel userModel = response.body();
                    UserProfileDetailModel userDetailModel = userModel.getJsonData().get(0);
                    usernametv.setText(userDetailModel.getUser_name());
                    emailidtv.setText(userDetailModel.getEmail());
                    phonetv.setText(userDetailModel.getPhone());


                    if (userDetailModel.getImageurl().equals("")) {
                        char letter = userDetailModel.getUser_name().charAt(0);
                        tvnameimage.setText(letter + "");
                        tvnameimage.setVisibility(View.VISIBLE);
                        userimage.setVisibility(View.GONE);
                    } else {
                        Glide.with(getActivity())
                                .load(userDetailModel.getImageurl())
                                .placeholder(R.drawable.userimage)
                                .into(userimage);
                        tvnameimage.setVisibility(View.GONE);
                        userimage.setVisibility(View.VISIBLE);
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
                    Toast.makeText(getActivity(), "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {


                        SavePref.setLoggedIn(false);
                        Intent intent = new Intent(getActivity(), MobileRegisterActivity.class);
                        startActivity(intent);
                        getActivity().finish();

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


}