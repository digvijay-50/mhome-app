package com.example.otpregisterloginhome.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.ModelClasses.FavModel;
import com.example.otpregisterloginhome.ModelClasses.GetProperty;
import com.example.otpregisterloginhome.ModelClasses.GetProperty_Inner;
import com.example.otpregisterloginhome.ModelClasses.UpdateModel;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertydetailActivity extends AppCompatActivity {


    LinearLayout loaderlayout, ll_apart, ll_furni, ll_home, ll_flor, ll_avaliable, ll_facing;
    SavePref savePref;
    RetrofitService retrofitService;
    String p_id;
    TextView tv_name, tv_address, tv_area, tv_startfrom, tv_property, tv_confi, tv_floor, tv_furni, tv_avali, tv_property1, tv_confi1, tv_des, tv_contactdetail,
            tv_checkpic, tv_tehsil, tv_khasra, tv_vilage, tv_facing,tv_viewcheck;
    LinearLayout card;
    RoundedImageView im_top;
    ImageView im_fav;
    RelativeLayout r_gallary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertydetail);
        loaderlayout = findViewById(R.id.loaderlayout);

        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        savePref = new SavePref(PropertydetailActivity.this);
        p_id = getIntent().getStringExtra("p_id");

        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Property Detail");

        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        tv_viewcheck = findViewById(R.id.tv_viewcheck);
        ll_facing = findViewById(R.id.ll_facing);
        card = findViewById(R.id.card);
        ll_flor = findViewById(R.id.ll_flor);
        ll_avaliable = findViewById(R.id.ll_avaliable);
        im_top = findViewById(R.id.im_top);
        im_fav = findViewById(R.id.im_fav);
        tv_area = findViewById(R.id.tv_area);
        ll_apart = findViewById(R.id.ll_apart);
        tv_startfrom = findViewById(R.id.tv_startfrom);
        tv_property = findViewById(R.id.tv_property);
        tv_confi = findViewById(R.id.tv_confi);
        tv_floor = findViewById(R.id.tv_floor);
        tv_furni = findViewById(R.id.tv_furni);
        tv_avali = findViewById(R.id.tv_avali);
        tv_property1 = findViewById(R.id.tv_property1);
        tv_confi1 = findViewById(R.id.tv_confi1);
        ll_furni = findViewById(R.id.ll_furni);
        ll_home = findViewById(R.id.ll_home);
        tv_des = findViewById(R.id.tv_des);
        tv_facing = findViewById(R.id.tv_facing);
        tv_checkpic = findViewById(R.id.tv_checkpic);
        tv_contactdetail = findViewById(R.id.tv_contactdetail);
        r_gallary = findViewById(R.id.r_gallary);
        tv_tehsil = findViewById(R.id.tv_tehsil);
        tv_khasra = findViewById(R.id.tv_khasra);
        tv_vilage = findViewById(R.id.tv_vilage);
        get_property();

    }


    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }


    private void get_property() {
        showLoader();
        retrofitService.get_single_property(SavePref.getUserId(), p_id).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {
                try {
                    setdata(response.body().getJsonData().get(0));
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<GetProperty> call, Throwable t) {
                hideLoader();
                t.printStackTrace();
            }
        });
    }

    private void setdata(GetProperty_Inner getProperty_inner) {
        try {
            tv_name.setText(getProperty_inner.getPropertyTitle());
            if (getProperty_inner.getPropertyArea().equalsIgnoreCase(getProperty_inner.getPropertyCity())) {
                tv_address.setText(getProperty_inner.getPropertyCity() + " , " + getProperty_inner.getPropertyState() + "   (" + getProperty_inner.getPropertyPincode() + " )");
            } else {
                tv_address.setText(getProperty_inner.getPropertyArea() + " , " + getProperty_inner.getPropertyCity() + " , " + getProperty_inner.getPropertyState() + "  (" + getProperty_inner.getPropertyPincode() + " )");
            }

            tv_property.setText(getProperty_inner.getPropertySubType());

            if (getProperty_inner.getFloorDetail().equalsIgnoreCase("")) {
                ll_flor.setVisibility(View.GONE);
            } else {
                ll_flor.setVisibility(View.VISIBLE);
            }

            if (getProperty_inner.getFurnishingDetail().equalsIgnoreCase("")) {
                ll_furni.setVisibility(View.GONE);
            } else {
                ll_furni.setVisibility(View.VISIBLE);
            }

            if (getProperty_inner.getAvailableFor().equalsIgnoreCase("")) {
                ll_avaliable.setVisibility(View.GONE);
            } else {
                ll_avaliable.setVisibility(View.VISIBLE);
            }
//            Toast.makeText(this, ""+getProperty_inner.getPropertySubType(), Toast.LENGTH_SHORT).show();
//            if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Apartment")){
//                ll_apart.setVisibility(View.VISIBLE);
//                ll_furni.setVisibility(View.GONE);
//                ll_home.setVisibility(View.GONE);
//            }else  if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Home")){
//                ll_apart.setVisibility(View.GONE);
//                ll_home.setVisibility(View.VISIBLE);
//                ll_furni.setVisibility(View.VISIBLE);
//            }else  if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Plot")){
//                ll_apart.setVisibility(View.GONE);
//                ll_home.setVisibility(View.VISIBLE);
//                ll_furni.setVisibility(View.GONE);
//            }else  if (getProperty_inner.getPropertySubType().equalsIgnoreCase("Land")){
//                ll_apart.setVisibility(View.GONE);
//                ll_home.setVisibility(View.VISIBLE);
//                ll_furni.setVisibility(View.GONE);
//            }


            if (getProperty_inner.getPropertyTehsil().equalsIgnoreCase("")) {
                tv_tehsil.setVisibility(View.GONE);
            } else {
                tv_tehsil.setVisibility(View.VISIBLE);
            }
            if (getProperty_inner.getPropertyKhasraNo().equalsIgnoreCase("")) {
                tv_khasra.setVisibility(View.GONE);
            } else {
                tv_khasra.setVisibility(View.VISIBLE);
            }
            if (getProperty_inner.getPropertyDistrict().equalsIgnoreCase("")) {
                tv_vilage.setVisibility(View.GONE);
            } else {
                tv_vilage.setVisibility(View.VISIBLE);
            }

            tv_tehsil.setText("Tehsil : " + getProperty_inner.getPropertyTehsil());
            tv_khasra.setText("Khasra No. : " + getProperty_inner.getPropertyKhasraNo());
            tv_vilage.setText("District/Village : " + getProperty_inner.getPropertyDistrict());

            if (getProperty_inner.getProperty_facing().equalsIgnoreCase("")) {
                ll_facing.setVisibility(View.GONE);
            } else {
                ll_facing.setVisibility(View.VISIBLE);
            }

            tv_facing.setText(getProperty_inner.getProperty_facing());
            tv_property1.setText(getProperty_inner.getPropertySubType());
            tv_confi.setText(getProperty_inner.getPropertyConfiguration());
            tv_confi1.setText(getProperty_inner.getPropertyConfiguration());
            tv_floor.setText(getProperty_inner.getFloorDetail());
            tv_furni.setText(getProperty_inner.getFurnishingDetail());
            tv_avali.setText(getProperty_inner.getAvailableFor());
            tv_viewcheck.setText(getProperty_inner.getProperty_view()+ " Views");
            tv_checkpic.setText(getProperty_inner.getPropertyGallery().size() + " Photos");
            tv_des.setText(Html.fromHtml(getProperty_inner.getPropertyDescription()));
            int firstcheck = Integer.parseInt(getProperty_inner.getProperty_final_price());


            if (firstcheck >= 10000000) {
                DecimalFormat df = new DecimalFormat("#.#######");
                double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                String out = df.format(lakhs);

                tv_startfrom.setText("₹ " + out + " Cr");
            } else if (firstcheck >= 100000) {

                DecimalFormat df = new DecimalFormat("#.######");
                double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                String out = df.format(lakhs);
                tv_startfrom.setText("₹ " + out + " Lac");
            } else {
                tv_startfrom.setText("₹ " + firstcheck + "");
            }

            tv_area.setText(getProperty_inner.getAreaDetail() + " " + getProperty_inner.getAreaUnitDetail());


            r_gallary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(PropertydetailActivity.this, GallaryActivity.class);
                    i.putExtra("gallary", getProperty_inner);
                    startActivity(i);
                }
            });
            tv_contactdetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SplashActivity.disablefor1sec(v);
                    final Dialog dialog = new Dialog(PropertydetailActivity.this);
                    dialog.setContentView(R.layout.dialog_sharedetail_app);
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
                            //   finishAffinity();
                            add_contact_property(getProperty_inner);
                            dialog.dismiss();
                        }
                    });


                    dialog.show();


//                    final Dialog dialog = new Dialog(PropertydetailActivity.this);
//                    dialog.setContentView(R.layout.dialog_contacts);
//                    Window window = dialog.getWindow();
//                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    TextView cancelbtn = dialog.findViewById(R.id.cancelbtn);
//                    TextView tv_topname = dialog.findViewById(R.id.tv_topname);
//
//                    tv_topname.setText("Contact us");
//                    cancelbtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            SplashActivity.disablefor1sec(v);
//                            dialog.dismiss();
//                        }
//                    });
//
//                    final CardView phonecv = dialog.findViewById(R.id.phonecv);
//                    final CardView emailcv = dialog.findViewById(R.id.emailcv);
//
//
//                    final TextView phonetv = dialog.findViewById(R.id.phonetv);
//                    final TextView emailtv = dialog.findViewById(R.id.emailtv);
//                    phonetv.setText(getProperty_inner.getPhone());
//                    emailtv.setText(getProperty_inner.getEmail());
//                    phonecv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            SplashActivity.disablefor1sec(v);
//                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getProperty_inner.getPhone(), null)));
//                        }
//                    });
//
//                    emailcv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            SplashActivity.disablefor1sec(v);
//                            try {
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getProperty_inner.getEmail()));
//                                intent.putExtra(Intent.EXTRA_SUBJECT, "");
//                                intent.putExtra(Intent.EXTRA_TEXT, "");
//                                startActivity(intent);
//                            } catch (Exception e) {
//                                Toast.makeText(PropertydetailActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                    dialog.show();
                }
            });
            if (getProperty_inner.getFav_user().equalsIgnoreCase("1")) {
                Glide.with(PropertydetailActivity.this)
                        .load(getResources().getDrawable(R.drawable.favourite))
                        .into(im_fav);
            } else {
                Glide.with(PropertydetailActivity.this)
                        .load(getResources().getDrawable(R.drawable.unfavourite))
                        .into(im_fav);
            }
            Glide.with(PropertydetailActivity.this)
                    .load(getProperty_inner.getProductImage())
                    .placeholder(R.drawable.no_image)
                    .into(im_top);


            im_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_fav(getProperty_inner.getPropertyId(), getProperty_inner, im_fav);

                }
            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void add_fav(String propertyId, GetProperty_Inner getProperty_inner, ImageView im_fav) {
        showLoader();
        retrofitService.add_fav(SavePref.getUserId(), propertyId).enqueue(new Callback<FavModel>() {
            @Override
            public void onResponse(Call<FavModel> call, Response<FavModel> response) {
                try {
                    Toast.makeText(PropertydetailActivity.this, "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getJsonData().get(0).getSuccess().equalsIgnoreCase("1")) {

                        if (response.body().getJsonData().get(0).getFid().equalsIgnoreCase("0")) {
                            getProperty_inner.setFav_user("0");
                        } else {
                            getProperty_inner.setFav_user("1");
                        }
                        //   notifyDataSetChanged();
                        if (getProperty_inner.getFav_user().equalsIgnoreCase("1")) {
                            Glide.with(PropertydetailActivity.this)
                                    .load(getResources().getDrawable(R.drawable.favourite))
                                    .into(im_fav);
                        } else {
                            Glide.with(PropertydetailActivity.this)
                                    .load(getResources().getDrawable(R.drawable.unfavourite))
                                    .into(im_fav);
                        }
                        //get_property();
                    } else {

                    }
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<FavModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

    private void add_contact_property(GetProperty_Inner getProperty_inner) {
        showLoader();
        Log.i("add_contact_property", "add_contact_property: "+getProperty_inner.getUserId()+" "+ getProperty_inner.getPropertyId()+" "+ SavePref.getUserId());
        retrofitService.add_contact_property(getProperty_inner.getUserId(), getProperty_inner.getPropertyId(), SavePref.getUserId()).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    Toast.makeText(PropertydetailActivity.this, "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getJsonData().get(0).getSuccess().equalsIgnoreCase("1")) {

                        final Dialog dialog = new Dialog(PropertydetailActivity.this);
                        dialog.setContentView(R.layout.dialog_contacts);
                        Window window = dialog.getWindow();
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        TextView cancelbtn = dialog.findViewById(R.id.cancelbtn);
                        TextView tv_topname = dialog.findViewById(R.id.tv_topname);

                        tv_topname.setText("Contact us");
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
                        phonetv.setText(getProperty_inner.getPhone());
                        emailtv.setText(getProperty_inner.getEmail());
                        phonecv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SplashActivity.disablefor1sec(v);
                                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getProperty_inner.getPhone(), null)));
                            }
                        });

                        emailcv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SplashActivity.disablefor1sec(v);
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getProperty_inner.getEmail()));
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                                    intent.putExtra(Intent.EXTRA_TEXT, "");
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(PropertydetailActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        });
                        dialog.show();
                        //get_property();
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