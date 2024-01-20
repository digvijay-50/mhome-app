package com.propertybuysell.mhome.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.propertybuysell.mhome.ModelClasses.FavModel;
import com.propertybuysell.mhome.ModelClasses.GetProperty;
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.Permission.PermissionHandler;
import com.propertybuysell.mhome.Permission.Permissions;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PropertydetailActivity extends AppCompatActivity {


    LinearLayout loaderlayout, ll_apart, ll_furni, ll_home, ll_flor, ll_avaliable, ll_facing;
    SavePref savePref;
    RetrofitService retrofitService;

    DownloadManager manager;
    ImageView im_sold;
    RecyclerView re_similer;
    String p_id;
    TextView tv_name, tv_address, tv_area, tv_startfrom, tv_property, tv_confi, tv_floor, tv_furni, tv_avali, tv_property1, tv_confi1, tv_des, tv_contactdetail,
            tv_checkpic, tv_tehsil, tv_khasra, tv_vilage, tv_facing, tv_viewcheck,tv_view,tv_download;
    LinearLayout card,llpdf;
    RoundedImageView im_top;
    ImageView im_fav;
    RelativeLayout r_gallary;
    PropertyAdapter propertyAdapter;


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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            checkPermissionOfStorage1();
            // only for gingerbread and newer versions
        } else {
            checkPermissionOfStorage();
        }


        tv_name = findViewById(R.id.tv_name);
        tv_download = findViewById(R.id.tv_download);
        llpdf = findViewById(R.id.llpdf);
        tv_address = findViewById(R.id.tv_address);
        tv_viewcheck = findViewById(R.id.tv_viewcheck);
        ll_facing = findViewById(R.id.ll_facing);
        im_sold = findViewById(R.id.im_sold);
        card = findViewById(R.id.card);
        ll_flor = findViewById(R.id.ll_flor);
        tv_view = findViewById(R.id.tv_view);
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
        re_similer = findViewById(R.id.re_similer);
        propertyAdapter = new PropertyAdapter(PropertydetailActivity.this);
        re_similer.setAdapter(propertyAdapter);

        get_property(p_id);

    }


    public void showLoader() {
        loaderlayout.setVisibility(View.VISIBLE);
        loaderlayout.setClickable(true);
    }

    public void hideLoader() {
        loaderlayout.setVisibility(View.GONE);
        loaderlayout.setClickable(false);
    }


    private void get_property(String p_id) {
        showLoader();
        retrofitService.get_single_property(SavePref.getUserId(), p_id).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {
                try {
                    setdata(response.body().getJsonData().get(0));
                    get_similer_property();
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

    private void get_similer_property() {
        propertyAdapter.clearall();
        showLoader();
        retrofitService.get_similer_property(SavePref.getUserId(), p_id, 0).enqueue(new Callback<GetProperty>() {
            @Override
            public void onResponse(Call<GetProperty> call, Response<GetProperty> response) {
                try {
                    re_similer.setLayoutManager(new LinearLayoutManager(PropertydetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    propertyAdapter.addAll(response.body().getJsonData());
                    //                    re_similer.setAdapter(new PropertyAdapter());
//                    setdata(response.body().getJsonData().get(0));
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




            if(getProperty_inner.getProperty_pdf().equalsIgnoreCase("")){
                llpdf.setVisibility(View.GONE);
            }else{
                llpdf.setVisibility(View.VISIBLE);
            }

            tv_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://docs.google.com/gview?embedded=true&url="+getProperty_inner.getProperty_pdf()));
                    startActivity(intent);
//                    Toast.makeText(PropertydetailActivity.this, ""+getProperty_inner.getProperty_pdf(), Toast.LENGTH_SHORT).show();
//                    Intent i=new Intent(PropertydetailActivity.this,WebviewActivity.class);
//                    i.putExtra("url",getProperty_inner.getProperty_pdf());
//                    startActivity(i);
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(getProperty_inner.getProperty_pdf()));
//                    startActivity(intent);
                }
            });

            tv_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String URL = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";

                    DownloadAndShareImagesTask task = new DownloadAndShareImagesTask();
                    task.execute(getProperty_inner.getProperty_pdf());


                }
            });

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


//            Toast.makeText(PropertydetailActivity.this, ""+getProperty_inner.getProperty_sold(), Toast.LENGTH_SHORT).show();

            if (getProperty_inner.getProperty_sold().equalsIgnoreCase("0")){
                im_sold.setVisibility(View.GONE);
                tv_contactdetail.setVisibility(View.VISIBLE);
            }else{
                im_sold.setVisibility(View.VISIBLE);
                tv_contactdetail.setVisibility(View.GONE);
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
            tv_viewcheck.setText(getProperty_inner.getProperty_view() + " Views");
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
//        Log.i("add_contact_property", "add_contact_property: "+getProperty_inner.getUserId()+" "+ getProperty_inner.getPropertyId()+" "+ SavePref.getUserId());
        retrofitService.add_contact_property(getProperty_inner.getUserId(), getProperty_inner.getPropertyId(), SavePref.getUserId()).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    Toast.makeText(PropertydetailActivity.this, "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getJsonData().get(0).getSuccess().equalsIgnoreCase("1")) {

                        final Dialog dialog = new Dialog(PropertydetailActivity.this);
                        dialog.setContentView(R.layout.dialog_contactswhats);
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

                                openWhatsApp("+91"+getProperty_inner.getPhone());
//                                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getProperty_inner.getPhone(), null)));
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

    public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
        PropertyAdapter.ViewHolder viewHolder;
        Context mContext;
        ArrayList<GetProperty_Inner> getProperty_inners = new ArrayList<>();


        public PropertyAdapter(Context context, List<GetProperty_Inner> getProperty_inners) {
            this.mContext = context;
            this.getProperty_inners = (ArrayList<GetProperty_Inner>) getProperty_inners;
        }

        public PropertyAdapter(Context context) {
            this.mContext = context;

        }


        @Override
        public PropertyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.single_propertysimiler_item, parent, false);
            viewHolder = new PropertyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull PropertyAdapter.ViewHolder holder, final int position) {

            try {
                holder.tv_name.setText(getProperty_inners.get(position).getPropertyTitle());
                if (getProperty_inners.get(position).getPropertyArea().equalsIgnoreCase(getProperty_inners.get(position).getPropertyCity())) {
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyCity() + " , " + getProperty_inners.get(position).getPropertyState());
                } else {
                    holder.tv_address.setText(getProperty_inners.get(position).getPropertyArea() + " , " + getProperty_inners.get(position).getPropertyCity() + " , " + getProperty_inners.get(position).getPropertyState());
                }

//                holder.tv_startfrom.setText("₹" + getProperty_inners.get(position).getProperty_final_price());

                int firstcheck = Integer.parseInt(getProperty_inners.get(position).getProperty_final_price());


                if (firstcheck >= 10000000) {
                    DecimalFormat df = new DecimalFormat("#.#######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 10000000;
                    String out = df.format(lakhs);

                    holder.tv_startfrom.setText("₹ " + out + " Cr");
                } else if (firstcheck >= 100000) {

                    DecimalFormat df = new DecimalFormat("#.######");
                    double lakhs = Double.parseDouble(String.valueOf(firstcheck)) / 100000;
                    String out = df.format(lakhs);
                    holder.tv_startfrom.setText("₹ " + out + " Lac");
                } else {
                    holder.tv_startfrom.setText("₹ " + firstcheck + "");
                }
                holder.tv_area.setText(getProperty_inners.get(position).getAreaDetail() + " " + getProperty_inners.get(position).getAreaUnitDetail());

                if (getProperty_inners.get(position).getFav_user().equalsIgnoreCase("1")) {
                    Glide.with(mContext)
                            .load(mContext.getResources().getDrawable(R.drawable.favourite))
                            .into(holder.im_fav);
                } else {
                    Glide.with(mContext)
                            .load(mContext.getResources().getDrawable(R.drawable.unfavourite))
                            .into(holder.im_fav);
                }
                Glide.with(mContext)
                        .load(getProperty_inners.get(position).getProductImage())
                        .placeholder(R.drawable.no_image)
                        .into(holder.im_top);


                if (getProperty_inners.get(position).getProperty_sold().equalsIgnoreCase("0")){
                    holder.im_sold.setVisibility(View.GONE);
                }else{
//                    Toast.makeText(mContext, "hii", Toast.LENGTH_SHORT).show();
                    holder.im_sold.setVisibility(View.VISIBLE);
                }



                holder.im_fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_fav(getProperty_inners.get(position).getPropertyId(), getProperty_inners.get(position), holder.im_fav);

                    }
                });
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        p_id = getProperty_inners.get(position).getPropertyId();

                        get_property(getProperty_inners.get(position).getPropertyId());
//
//                        Intent i = new Intent(PropertydetailActivity.this, PropertydetailActivity.class);
//                        i.putExtra("p_id", getProperty_inners.get(position).getPropertyId());
//                        startActivity(i);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public int getItemCount() {
            return getProperty_inners.size();
        }

        public void addAll(List<GetProperty_Inner> getProperty_inners) {
            for (GetProperty_Inner getProperty_inner : getProperty_inners) {
                add(getProperty_inner);
            }
        }

        private void add(GetProperty_Inner getProperty_inner) {
            this.getProperty_inners.add(getProperty_inner);
            notifyDataSetChanged();
        }

        public void clearall() {
            getProperty_inners = new ArrayList<>();
            notifyDataSetChanged();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_name, tv_address, tv_area, tv_startfrom;
            LinearLayout card;
            RoundedImageView im_top;
            ImageView im_fav,im_sold;

            ViewHolder(View itemView) {
                super(itemView);

                tv_name = itemView.findViewById(R.id.tv_name);
                tv_address = itemView.findViewById(R.id.tv_address);
                card = itemView.findViewById(R.id.card);
                im_top = itemView.findViewById(R.id.im_top);
                im_fav = itemView.findViewById(R.id.im_fav);
                tv_area = itemView.findViewById(R.id.tv_area);
                im_sold = itemView.findViewById(R.id.im_soldcheck);
                tv_startfrom = itemView.findViewById(R.id.tv_startfrom);

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
                                Glide.with(mContext)
                                        .load(mContext.getResources().getDrawable(R.drawable.favourite))
                                        .into(im_fav);
                            } else {
                                Glide.with(mContext)
                                        .load(mContext.getResources().getDrawable(R.drawable.unfavourite))
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

    }


    private class DownloadAndShareImagesTask extends AsyncTask<String, Void, ArrayList<Uri>> {

        @Override
        protected ArrayList<Uri> doInBackground(String... urls) {
            ArrayList<Uri> imageUris = new ArrayList<>();
//            Log.i("doInBackground", "doInBackground123: " + urls.length);
            for (String url : urls) {
                try {
                    // Download the image
                    InputStream inputStream = new URL(url).openStream();
                    File imageFile = saveImageLocally(inputStream);

                    // Create a content URI for the saved image file using FileProvider
                    Uri imageUri = FileProvider.getUriForFile(PropertydetailActivity.this, getApplicationContext().getPackageName() + ".fileprovider",
                            imageFile);
                    imageUris.add(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            Log.i("doInBackground", "doInBackground: " + imageUris.size());

            return imageUris;
        }

        @Override
        protected void onPostExecute(ArrayList<Uri> imageUris) {
            hideLoader();

            // Share the downloaded images
            Toast.makeText(PropertydetailActivity.this, "PDF Download Successfully", Toast.LENGTH_SHORT).show();
        }

        private File saveImageLocally(InputStream inputStream) throws IOException {

            File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            Random r = new Random();
            int randomNumber = r.nextInt(1000000000);
//            tv.setText(String.valueOf(randomNumber));
            File imageFile = new File(directory, randomNumber + "accc.pdf");

            OutputStream outputStream = new FileOutputStream(imageFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();

            return imageFile;
        }
    }


    public void checkPermissionOfStorage1() {


        String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
        String rationale = getString(R.string.msg_rationale);
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle(getString(R.string.txt_info))
                .setSettingsDialogTitle(getString(R.string.txt_warning));
        Permissions.check(PropertydetailActivity.this, permissions, rationale, options, new PermissionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                checkPermissionOfStorage1();
            }
        });
    }

    public void checkPermissionOfStorage() {


        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String rationale = getString(R.string.msg_rationale);
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle(getString(R.string.txt_info))
                .setSettingsDialogTitle(getString(R.string.txt_warning));
        Permissions.check(PropertydetailActivity.this, permissions, rationale, options, new PermissionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                checkPermissionOfStorage();
            }
        });
    }

    private void openWhatsApp(String phonenumber) {
        try {

//            Log.i("openWhatsApp", "openWhatsApp: " + phonenumber);
            if (whatsappInstalledOrNot("com.whatsapp")) {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("whatsapp://send/?text=&phone=" + phonenumber)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(PropertydetailActivity.this, "Please install WhatsApp", Toast.LENGTH_SHORT).show();
                } catch (Exception e2) {
                    Toast.makeText(PropertydetailActivity.this, "Error while encoding your text message", Toast.LENGTH_SHORT).show();
                }
            } else if (whatsappInstalledOrNot("com.whatsapp.w4b")) {
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("whatsapp://send/?text=&phone=" + phonenumber)));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(PropertydetailActivity.this, "Please install WhatsApp", Toast.LENGTH_SHORT).show();
                } catch (Exception e2) {
                    Toast.makeText(PropertydetailActivity.this, "Error while encoding your text message", Toast.LENGTH_SHORT).show();
                }
            } else {
                Intent goToMarket = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.whatsapp"));
                Toast.makeText(PropertydetailActivity.this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
                startActivity(goToMarket);
            }
        } catch (Exception e3) {
        }
    }


    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;


    }
}