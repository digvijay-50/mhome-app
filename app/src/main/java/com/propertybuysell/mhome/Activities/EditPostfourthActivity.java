package com.propertybuysell.mhome.Activities;

import static com.propertybuysell.mhome.RetrofitUtils.Constant.Avaliablefor;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Floordetail;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Furnished;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.SearchlookingTo;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.Typesofproperty;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.area_sq;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.configuration;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.price_sq;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.property;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_area;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_cast;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_city;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_district;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_khasra;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_pincode;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_state;
import static com.propertybuysell.mhome.RetrofitUtils.Constant.s_tehsil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.propertybuysell.mhome.ModelClasses.GetProperty_Inner;
import com.propertybuysell.mhome.ModelClasses.ImageaddModel;
import com.propertybuysell.mhome.ModelClasses.ImagestorageModel;
import com.propertybuysell.mhome.ModelClasses.UpdateDetailModel;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.Permission.PermissionHandler;
import com.propertybuysell.mhome.Permission.Permissions;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.Constant;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    ImageView im_add,im_pdf;
    MultipartBody.Part partImage;
    SavePref savePref;

    boolean uploadpdf = false;
    MultipartBody.Part partpdf;
    Uri filepath1;
    RequestBody pdf;
    String displayname1;
    int PICK_IMAGE_REQUEST = 111;

    RecyclerView recycler;
    LinearLayout loaderlayout;
    MultipartBody.Part partimag;
    public RetrofitService retrofitService;
    RequestBody mainreqimg;
    TextView tv_proceed;
    String reqcode;
    ArrayList<ImageaddModel> arrayList=new ArrayList<>();
    ArrayList<String> finalarrayList=new ArrayList<>();
    String et_area,et_exprice,et_priceper,et_exprice2,et_flornumber;
    EditText et_title,et_des;
    GetProperty_Inner getProperty_inner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfourth_post);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
        TextView tv_top1=findViewById(R.id.tv_top1);
        tv_top1.setText("Edit Basic Details");

        getProperty_inner= (GetProperty_Inner) getIntent().getSerializableExtra("getProperty_inner");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            checkPermissionOfStorage1();
        }else{
            checkPermissionOfStorage();
        }
        im_thumb=findViewById(R.id.im_thumb);
        loaderlayout = findViewById(R.id.loaderlayout);
        recycler=findViewById(R.id.recycler);
        im_add=findViewById(R.id.im_add);
        tv_proceed=findViewById(R.id.tv_proceed);
        et_title=findViewById(R.id.et_title);
        et_des=findViewById(R.id.et_des);
        im_pdf=findViewById(R.id.im_pdf);
        savePref=new SavePref(EditPostfourthActivity.this);
        recycler.setLayoutManager(new GridLayoutManager(EditPostfourthActivity.this,3));
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

        if (getProperty_inner.getProperty_pdf().equalsIgnoreCase("")){
            Glide.with(EditPostfourthActivity.this)
                    .load(R.drawable.add_image)
                    .placeholder(R.drawable.add_image)
                    .into(im_pdf);

        }else{
            Glide.with(EditPostfourthActivity.this)
                    .load(R.drawable.pdf)
                    .placeholder(R.drawable.add_image)
                    .into(im_pdf);

        }


        im_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_IMAGE_REQUEST);
            }
        });
        im_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqcode = "10002";
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(300, 300)
                        .start(EditPostfourthActivity.this);
            }
        });

        im_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqcode = "10003";
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(16, 9)
                        .start(EditPostfourthActivity.this);
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


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (reqcode.equalsIgnoreCase("10002")){
                    Uri resultUri = result.getUri();
                    String path = resultUri.getPath();
                    part_image = path;
                    Glide.with(EditPostfourthActivity.this)
                            .load(resultUri)
                            .placeholder(R.drawable.userimage)
                            .into(im_thumb);
                    String[] imageprojection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(resultUri,
                            imageprojection, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int indexImage = cursor.getColumnIndex(imageprojection[0]);
                        part_image = cursor.getString(indexImage);
                    }
                }else{
                    Uri resultUri = result.getUri();
                    String path = resultUri.getPath();
                    part_image2 = path;
//                    Glide.with(AddPostfourthActivity.this)
//                            .load(resultUri)
//                            .placeholder(R.drawable.userimage)
//                            .into(im_thumb);
                    if (part_image2 != null) {
                        add_image_storage(part_image2);
                    }
                    String[] imageprojection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(resultUri,
                            imageprojection, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int indexImage = cursor.getColumnIndex(imageprojection[0]);
                        part_image2 = cursor.getString(indexImage);
                    }
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;
            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        displayname1 = displayName;
                        filepath1 = uri;
                        im_pdf.setImageResource(R.drawable.pdf);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
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

        Random r = new Random();
        int randomNumber = r.nextInt(1000000000);
        if (part_image!=null){

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

        if (filepath1 != null) {
            uploadpdf = true;
            InputStream iStream = null;

            try {
                Random r1 = new Random();

                iStream = getContentResolver().openInputStream(filepath1);
                final byte[] inputData = getBytes(iStream);
                pdf = RequestBody.create(MediaType.parse("multipart/form-data"), inputData);
                // if(r_type.equalsIgnoreCase("1")){
                partpdf = MultipartBody.Part.createFormData("property_pdf", randomNumber + ".pdf", pdf);

                //                             }else{
                //                                partImage = MultipartBody.Part.createFormData("a_resume", edt_name.getText().toString() + ".doc", image);
                //
                //                            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
                getRequestBody("0"),
                partpdf,
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

    public void checkPermissionOfStorage1() {
        String[] permissions = {Manifest.permission.READ_MEDIA_IMAGES};
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
                checkPermissionOfStorage1();
            }
        });
    }

}