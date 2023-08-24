package com.example.otpregisterloginhome.Activities;

import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Avaliablefor;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Floordetail;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Furnished;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.Typesofproperty;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.area_sq;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.configuration;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.price_sq;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.property;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_area;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_cast;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_city;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_district;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_khasra;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_pincode;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_state;
import static com.example.otpregisterloginhome.RetrofitUtils.Constant.s_tehsil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.otpregisterloginhome.ModelClasses.GetProperty_Inner;
import com.example.otpregisterloginhome.ModelClasses.ImageaddModel;
import com.example.otpregisterloginhome.ModelClasses.ImagestorageModel;
import com.example.otpregisterloginhome.ModelClasses.UpdateDetailModel;
import com.example.otpregisterloginhome.ModelClasses.UpdateModel;
import com.example.otpregisterloginhome.Permission.PermissionHandler;
import com.example.otpregisterloginhome.Permission.Permissions;
import com.example.otpregisterloginhome.PrefUtils.SavePref;
import com.example.otpregisterloginhome.R;
import com.example.otpregisterloginhome.RetrofitUtils.ApiBaseUrl;
import com.example.otpregisterloginhome.RetrofitUtils.Constant;
import com.example.otpregisterloginhome.RetrofitUtils.RetrofitService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostfourthActivity extends AppCompatActivity {

    ImageView im_thumb;
    String part_image,part_image1;
    String part_image2;
    ImageView im_add;
    MultipartBody.Part partImage;
    SavePref savePref;
    RecyclerView recycler;
    LinearLayout loaderlayout;
    MultipartBody.Part partimag;
    public RetrofitService retrofitService;
    RequestBody mainreqimg;
    TextView tv_proceed;
    ArrayList<ImageaddModel> arrayList=new ArrayList<>();
    ArrayList<String> finalarrayList=new ArrayList<>();
    String et_area,et_exprice,et_priceper,et_exprice2,et_flornumber;
    EditText et_title,et_des;

    GetProperty_Inner getProperty_inner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfourth_post);


        ImageView backic = findViewById(R.id.backic);
        backic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });
        et_area=getIntent().getStringExtra("et_area");
        et_exprice=getIntent().getStringExtra("et_exprice");
        et_priceper=getIntent().getStringExtra("et_priceper");
        et_exprice2=getIntent().getStringExtra("et_exprice2");
        et_flornumber=getIntent().getStringExtra("et_flornumber");

        Log.i("onCreate", "onCreate: "+et_exprice2);

        TextView tv_top1=findViewById(R.id.tv_top1);
        tv_top1.setText("Edit Basic Details");

        getProperty_inner= (GetProperty_Inner) getIntent().getSerializableExtra("getProperty_inner");
        checkPermissionOfStorage();

        im_thumb=findViewById(R.id.im_thumb);
        loaderlayout = findViewById(R.id.loaderlayout);
        recycler=findViewById(R.id.recycler);
        im_add=findViewById(R.id.im_add);
        tv_proceed=findViewById(R.id.tv_proceed);
        et_title=findViewById(R.id.et_title);
        et_des=findViewById(R.id.et_des);
        savePref=new SavePref(EditPostfourthActivity.this);
        recycler.setLayoutManager(new GridLayoutManager(EditPostfourthActivity.this,2));
        //imageAdapter=new ImageAdapter(MainActivity.this);
        retrofitService= ApiBaseUrl.getClient().create(RetrofitService.class);



        et_title.setText(getProperty_inner.getPropertyTitle());
        et_des.setText(getProperty_inner.getPropertyDescription());

        for (int i = 0; i < getProperty_inner.getPropertyGallery().size(); i++) {
            arrayList.add(new ImageaddModel(getProperty_inner.getPropertyGallery().get(i)));

        }
        recycler.setAdapter(new ImageAdapter(EditPostfourthActivity.this,arrayList));


        Glide.with(EditPostfourthActivity.this)
                .load(getProperty_inner.getProductImage())
                .placeholder(R.drawable.add_image)
                .into(im_thumb);

        im_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 10002);
            }
        });

        im_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1001);
            }
        });

        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()){
                    finalarrayList=new ArrayList<>();
                    for (int i = 0; i < arrayList.size(); i++) {
                        //   Log.i("onClick", "onClickup: "+arrayList.get(i).getUrl());
                        finalarrayList.add(arrayList.get(i).getUrl());
                    }
                    //  Log.i("onClick", "onClickup: "+TextUtils.join(",",finalarrayList));
                    add_property(TextUtils.join(",",finalarrayList));
                }

             //   add_addall_image(TextUtils.join(",",finalarrayList));
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10002){
            try {
                Uri dataimage = data.getData();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataimage, imageprojection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getColumnIndex(imageprojection[0]);
                    part_image = cursor.getString(indexImage);
                    if (part_image != null) {
                        File image = new File(part_image);
                        Glide.with(EditPostfourthActivity.this)
                                .load(BitmapFactory.decodeFile(image.getAbsolutePath()))
                                .into(im_thumb);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else  if (requestCode==1001){
            Uri dataimage = data.getData();
            String[] imageprojection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(dataimage, imageprojection, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int indexImage = cursor.getColumnIndex(imageprojection[0]);
                part_image2 = cursor.getString(indexImage);
                if (part_image2 != null) {
                    //   File image = new File(part_image2);
                    add_image_storage(part_image2);
                    //imageAdapter.addAll(arrayList);
//                    Glide.with(MainActivity.this)
//                            .load(BitmapFactory.decodeFile(image.getAbsolutePath()))
//                            .into(im_add);
                }
            }
        }
    }

    public void checkPermissionOfStorage() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String rationale = getString(R.string.msg_rationale);
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle(getString(R.string.txt_info))
                .setSettingsDialogTitle(getString(R.string.txt_warning));
        Permissions.check(EditPostfourthActivity.this, permissions, rationale, options, new PermissionHandler() {
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

    private void add_image_storage(String path) {
        showLoader();

        Random r = new Random();
        int randomNumber = r.nextInt(1000000000);
        File imagefile = new File(path);
        mainreqimg = RequestBody.create(MediaType.parse("multipart/form-data"), imagefile);
        partimag = MultipartBody.Part.createFormData("image_path", randomNumber + ".jpg", mainreqimg);
        retrofitService.add_image_storage(partimag).enqueue(new Callback<ImagestorageModel>() {
            @Override
            public void onResponse(Call<ImagestorageModel> call, final Response<ImagestorageModel> response) {
                try {
                    Toast.makeText(EditPostfourthActivity.this, "" + response.body().getJsonData().get(0).getMsg(), Toast.LENGTH_SHORT).show();
                    arrayList.add(new ImageaddModel(response.body().getJsonData().get(0).getImage_path()));
                    recycler.setAdapter(new ImageAdapter(EditPostfourthActivity.this,arrayList));
                    hideLoader();
                } catch (Exception e) {
                    hideLoader();
                }
            }

            @Override
            public void onFailure(Call<ImagestorageModel> call, Throwable t) {
                t.printStackTrace();
                hideLoader();
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

    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        public ArrayList<ImageaddModel> categoryDetailModels=new ArrayList<>();
        private Context context;


        public ImageAdapter(Context context, ArrayList<ImageaddModel> categoryDetailModels) {
            this.context = context;
            this.categoryDetailModels = categoryDetailModels;
        }



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View viewItem = inflater.inflate(R.layout.image_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(viewItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            try {
                Glide.with(context)
                        .load(Constant.imageurl+categoryDetailModels.get(position).getUrl())
                        .into(holder.categoryiv);

                holder.im_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        categoryDetailModels.remove(position);
                        notifyDataSetChanged();
                    }
                });
            } catch (Exception e) {
            }
        }

        public void add(ImageaddModel  r) {
            this.categoryDetailModels.add(r);
            notifyItemInserted(categoryDetailModels.size() - 1);
        }

        public void addAll(List<ImageaddModel> moveTransactionDetailss) {
            for (ImageaddModel result : moveTransactionDetailss) {
                add(result);
            }
        }

        @Override
        public int getItemCount() {
            return categoryDetailModels.size();
        }

        protected class ViewHolder extends RecyclerView.ViewHolder {
            CardView linearLayout;
            TextView categorytv;
            ImageView categoryiv,im_remove;

            public ViewHolder(final View v) {
                super(v);
                linearLayout = itemView.findViewById(R.id.linearLayout);
                categorytv = itemView.findViewById(R.id.categorytv);
                categoryiv = itemView.findViewById(R.id.categoryiv);
                im_remove = itemView.findViewById(R.id.im_remove);
            }
        }
    }


    public RequestBody getRequestBody(String s) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), s);
        return requestBody;
    }




    private void add_property(String join) {
        showLoader();
        if (part_image!=null){
            Random r = new Random();
            int randomNumber = r.nextInt(1000000000);
            File imagefile = new File(part_image);
            RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), imagefile);
             partImage = MultipartBody.Part.createFormData("property_image", randomNumber+".jpg", image);

        }

//        Log.i("add_property", "add_property: "+savePref.getUserId()+" SearchlookingTo "+SearchlookingTo+" property "+property+" Typesofproperty "+Typesofproperty+" s_state "+s_state+" s_city "+s_city+" s_area "+s_area
//        +" s_pincode "+s_pincode+" s_cast "+s_cast+" configuration "+configuration+" Furnished "+Furnished+" Floordetail "+Floordetail+" Avaliablefor "+Avaliablefor+" et_area "+et_area+" area_sq "+area_sq+" et_priceper "+et_priceper+" price_sq "+price_sq
//        +" et_title "+et_title.getText().toString()+" et_des "+et_des.getText().toString());

        String price;
//        if (et_priceper.equalsIgnoreCase("")){
//            price=et_exprice;
//        }else{
            price=et_priceper;
    //    }
        retrofitService.edit_property(
                getRequestBody(savePref.getUserId()),
                getRequestBody(SearchlookingTo),
                getRequestBody(property),
                getRequestBody(Typesofproperty),
                getRequestBody(s_state),
                getRequestBody(s_city),
                getRequestBody(s_area),
                getRequestBody(s_pincode),
                getRequestBody(s_tehsil),
                getRequestBody(s_khasra),
                getRequestBody(s_district),
                getRequestBody(s_cast),
                getRequestBody(configuration),
                getRequestBody(Furnished),
                getRequestBody(Floordetail),
                getRequestBody(Avaliablefor),
                getRequestBody(et_area),
                getRequestBody(area_sq),
                getRequestBody(price),
                getRequestBody(price_sq),
                getRequestBody(et_title.getText().toString()),
                getRequestBody(et_des.getText().toString()),
                getRequestBody(join),
                getRequestBody(getProperty_inner.getPropertyId()),
                getRequestBody(et_exprice2),
                getRequestBody(Constant.property_facing),

                partImage).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(EditPostfourthActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {

                       Intent i=new Intent(EditPostfourthActivity.this,PostedpropertyActivity.class);
                       startActivity(i);

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
    private boolean validation() {

        if (TextUtils.isEmpty(et_title.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();

            return false;
        } else if (TextUtils.isEmpty(et_des.getText().toString().trim())) {
            Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }


    }
}