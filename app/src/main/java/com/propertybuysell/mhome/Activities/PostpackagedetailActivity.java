package com.propertybuysell.mhome.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.phonepe.intent.sdk.api.B2BPGRequest;
import com.phonepe.intent.sdk.api.B2BPGRequestBuilder;
import com.phonepe.intent.sdk.api.PhonePe;
import com.phonepe.intent.sdk.api.PhonePeInitException;
import com.phonepe.intent.sdk.api.models.PhonePeEnvironment;
import com.propertybuysell.mhome.ModelClasses.GetPostPackage_Inner;
import com.propertybuysell.mhome.ModelClasses.Getresmodel;
import com.propertybuysell.mhome.ModelClasses.UpdateDetailModel;
import com.propertybuysell.mhome.ModelClasses.UpdateModel;
import com.propertybuysell.mhome.ModelClasses.UserProfileDetailModel;
import com.propertybuysell.mhome.ModelClasses.UserProfileModel;
import com.propertybuysell.mhome.Paymentdata.GatewayOrderStatus;
import com.propertybuysell.mhome.Paymentdata.GetOrderIDRequest;
import com.propertybuysell.mhome.Paymentdata.GetOrderIDResponse;
import com.propertybuysell.mhome.Paymentdata.MyBackendService;
import com.propertybuysell.mhome.PrefUtils.SavePref;
import com.propertybuysell.mhome.R;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrl;
import com.propertybuysell.mhome.RetrofitUtils.ApiBaseUrlreteo;
import com.propertybuysell.mhome.RetrofitUtils.Constant;
import com.propertybuysell.mhome.RetrofitUtils.RetrofitService;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import kotlin.text.Charsets;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

//public class PostpackagedetailActivity extends AppCompatActivity implements Instamojo.InstamojoPaymentCallback {
public class PostpackagedetailActivity extends AppCompatActivity {


    TextView tv_pakname, tv_price, tv_numberpost, tv_duration,tv_des,tv_purchase;
    CardView card;
    SavePref savePref;
    private String apiEndPoint = "/pg/v1/pay";
    private String salt = "4f90307d-b88c-4539-9548-9236ba8c468f"; // salt key
    private String MERCHANT_ID = "M1N4OS44NQ8D";  // Merchant id

    private static int B2B_PG_REQUEST_CODE = 777;

    private String MERCHANT_TID = "00";
    B2BPGRequest b2BPGRequest;
    String checkpurchase;
    RetrofitService retrofitService,retrofitServicepay;
    LinearLayout loaderlayout;
    GetPostPackage_Inner getPostPackage_inner;


//    private Instamojo.Environment mCurrentEnv = Instamojo.Environment.PRODUCTION;
    private boolean mCustomUIFlow = false;

    private MyBackendService myBackendService;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postpackagedetail);

        ImageView backiv = findViewById(R.id.backic);
        backiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashActivity.disablefor1sec(v);
                onBackPressed();
            }
        });


        TextView headingtv = findViewById(R.id.headingtv);
        headingtv.setText("Property Post Package");
        savePref=new SavePref(PostpackagedetailActivity.this);
        getPostPackage_inner= (GetPostPackage_Inner) getIntent().getSerializableExtra("getPostPackage_inner");
        tv_pakname = findViewById(R.id.tv_pakname);
        tv_price = findViewById(R.id.tv_price);
        card = findViewById(R.id.card);
        tv_numberpost = findViewById(R.id.tv_numberpost);
        tv_duration = findViewById(R.id.tv_duration);
        tv_des = findViewById(R.id.tv_des);
        tv_purchase = findViewById(R.id.tv_purchase);
        retrofitService = ApiBaseUrl.getClient().create(RetrofitService.class);
        retrofitServicepay = ApiBaseUrlreteo.getClient().create(RetrofitService.class);
        loaderlayout = findViewById(R.id.loaderlayout);
        tv_pakname.setText(getPostPackage_inner.getPropertyPostPackageName());
        tv_price.setText("₹" + getPostPackage_inner.getPropertyPostPackagePrice());
        tv_numberpost.setText("Number of Post : " + getPostPackage_inner.getNoOfPropery());
        tv_duration.setText("Package Duration : " + getPostPackage_inner.getPackageDuration() + " Days");

        tv_des.setText(Html.fromHtml(getPostPackage_inner.getPropertyPostPackageDescription()));

        getUserProfile();
        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Constant.paymenturl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myBackendService = retrofit.create(MyBackendService.class);

        tv_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(PostpackagedetailActivity.this, ""+savePref.getUserphone(), Toast.LENGTH_SHORT).show();
                startPayment();
//                checkPaymentStatus(paymentID, orderID);
//                if (checkpurchase.equalsIgnoreCase("0")){
//                    Toast.makeText(PostpackagedetailActivity.this, "Already Purchsed", Toast.LENGTH_SHORT).show();
//                }else{
////                    paymentgetway();
//                }


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

    private void add_postpackagepurchase(String paymentid) {

        showLoader();


        retrofitService.add_postpackagepurchase(
               savePref.getUserId(),
                getPostPackage_inner.getPropertyPostPackageId(),
                getPostPackage_inner.getPropertyPostPackagePrice(),
                paymentid,
                paymentid,
                paymentid

        ).enqueue(new Callback<UpdateModel>() {
            @Override
            public void onResponse(Call<UpdateModel> call, Response<UpdateModel> response) {
                try {
                    final UpdateModel registerModel = response.body();
                    UpdateDetailModel registerDetailModel = registerModel.getJsonData().get(0);
                    Toast.makeText(PostpackagedetailActivity.this, "" + registerDetailModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (registerDetailModel.getSuccess().equalsIgnoreCase("1")) {

                        Intent i=new Intent(PostpackagedetailActivity.this,NewHomeActivity.class);
                        startActivity(i);
                        finish();

                    } else {
                        hideLoader();
                    }


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


//    public void paymentgetway(){
//
//        showLoader();
////        GetOrderIDRequest request = new GetOrderIDRequest();
////        request.setEnv(mCurrentEnv.name());
////        request.setBuyerName("test");
////        request.setBuyerEmail("anc@gmail.com");
////        request.setBuyerPhone("1234567890");
////        request.setDescription("");
////        request.setAmount("10");
// //       Call<GetOrderIDResponse> getOrderIDCall = myBackendService.createOrder("fees","10","tushar","tushar@gmail.com","8238906472");
//
//  Call<GetOrderIDResponse> getOrderIDCall = myBackendService.createOrder(getPostPackage_inner.getPropertyPostPackageName(),getPostPackage_inner.getPropertyPostPackagePrice(),savePref.getUsername(),savePref.getUserEmail(),savePref.getUserphone());
//        getOrderIDCall.enqueue(new retrofit2.Callback<GetOrderIDResponse>() {
//            @Override
//            public void onResponse(Call<GetOrderIDResponse> call, Response<GetOrderIDResponse> response) {
////                Toast.makeText(PostpackagedetailActivity.this, "hii", Toast.LENGTH_SHORT).show();
//
//                hideLoader();
//                if (response.isSuccessful()) {
//                    String orderId = response.body().getOrderID();
//
//
//                    Log.i("onResponse", "onResponse: "+orderId);
//                    // Toast.makeText(MainActivity.this, "hii", Toast.LENGTH_SHORT).show();
////                    if (!mCustomUIFlow) {
////                        // Initiate the default SDK-provided payment activity
//////                        initiateSDKPayment(orderId);
////
////                    } else {
////                        // OR initiate a custom UI activity
////                        ///     initiateCustomPayment(orderId);
////                    }
//
//                } else {
//                    // Handle api errors
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
////                        Log.d(TAG, "Error in response" + jObjError.toString());
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetOrderIDResponse> call, Throwable t) {
//                // Handle call failure
////                Log.d(TAG, "Failure");
//                hideLoader();
//            }
//        });
//    }



//    private void initiateSDKPayment(String orderID) {
//
//        Log.i("initiateSDKPayment", "initiateSDKPayment123: "+orderID);
//        Instamojo.getInstance().initiatePayment(PostpackagedetailActivity.this, orderID, this);
//    }

//    private void checkPaymentStatus(final String transactionID, final String orderID) {
////        if (transactionID == null && orderID == null) {
////            return;
////        }
//
//        showLoader();
//
////        Log.i("checkPaymentStatus", "checkPaymentStatus123: "+mCurrentEnv.name().toLowerCase()+"  "+
////                orderID+"  "+ transactionID+" "+ Constants.PAYMENT_ID);
////        showToast("Checking transaction status");
//        Call<GatewayOrderStatus> getOrderStatusCall = myBackendService.orderStatus(transactionID);
//        getOrderStatusCall.enqueue(new retrofit2.Callback<GatewayOrderStatus>() {
//            @Override
//            public void onResponse(Call<GatewayOrderStatus> call, final Response<GatewayOrderStatus> response) {
//
//
//                ///   Log.i("checkPaymentStatus", "onResponse: "+response.isSuccessful());
//                hideLoader();
//                if (response.isSuccessful()) {
//                    GatewayOrderStatus orderStatus = response.body();
//                    if (orderStatus.getStatus()==true) {
//                        Toast.makeText(PostpackagedetailActivity.this, "Transaction successful", Toast.LENGTH_SHORT).show();
//                        add_postpackagepurchase(orderStatus.getPaymentID());
////                        showToast("Transaction still pending");
//                        return;
//                    }
//
////                    showToast("Transaction successful for id - " + orderStatus.getPaymentID());
//                    refundTheAmount(transactionID, orderStatus.getAmount());
//
//                } else {
//                    Toast.makeText(PostpackagedetailActivity.this, "Error occurred while fetching transaction status", Toast.LENGTH_SHORT).show();
//
////                    showToast("Error occurred while fetching transaction status");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GatewayOrderStatus> call, Throwable t) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                hideLoader();
//                        Toast.makeText(PostpackagedetailActivity.this, "Failed to fetch the transaction status", Toast.LENGTH_SHORT).show();
//
////                        showToast("Failed to fetch the transaction status");
//                    }
//                });
//            }
//        });
//    }

//    private void refundTheAmount(String transactionID, String amount) {
//        if (transactionID == null || amount == null) {
//            return;
//        }
//
//      showLoader();
//        Toast.makeText(PostpackagedetailActivity.this, "Initiating a refund for", Toast.LENGTH_SHORT).show();
//
//        Call<ResponseBody> refundCall = myBackendService.refundAmount(
//                mCurrentEnv.name().toLowerCase(),
//                transactionID, amount);
//
//        refundCall.enqueue(new retrofit2.Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//              hideLoader();
//
//                if (response.isSuccessful()) {
//                    Toast.makeText(PostpackagedetailActivity.this, "IRefund initiated successfully", Toast.LENGTH_SHORT).show();
//
////                    showToast("Refund initiated successfully");
//
//                } else {
//                    Toast.makeText(PostpackagedetailActivity.this, "Failed to initiate a refund", Toast.LENGTH_SHORT).show();
//
////                    showToast("Failed to initiate a refund");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//              hideLoader();
//
//                Toast.makeText(PostpackagedetailActivity.this, "Failed to Initiate a refund", Toast.LENGTH_SHORT).show();
//
////                showToast("Failed to Initiate a refund");
//            }
//        });
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constants.REQUEST_CODE && data != null) {
//
//
//            String orderID = data.getStringExtra(Constants.ORDER_ID);
//            String transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
//            String paymentID = data.getStringExtra(Constants.PAYMENT_ID);
//            Log.i("checkPaymentStatus", "onActivityResult: "+orderID+" "+paymentID);
//            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
//            if (transactionID != null || paymentID != null) {
//                checkPaymentStatus(paymentID, orderID);
//            } else {
//
//                Toast.makeText(PostpackagedetailActivity.this, "Oops!! Payment was cancelled", Toast.LENGTH_SHORT).show();
//
////                showToast("Oops!! Payment was cancelled");
//            }
//        }
//    }

//    @Override
//    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
////        Log.d(TAG, "Payment complete");
////        Log.i("checkPaymentStatus", "onInstamojoPaymentComplete: "+"Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
////                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);
////        Toast.makeText(this, ""+"Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
////                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus, Toast.LENGTH_SHORT).show();
//
//
//        checkPaymentStatus(paymentID, orderID);
//
//    }

//    @Override
//    public void onPaymentCancelled() {
//    //    Log.d(TAG, "Payment cancelled");
//        Toast.makeText(PostpackagedetailActivity.this, "Payment cancelled by user", Toast.LENGTH_SHORT).show();
//
////        showToast("Payment cancelled by user");
//    }

//    @Override
//    public void onInitiatePaymentFailure(String errorMessage) {
//  //      Log.d(TAG, "Initiate payment failed");
//        Toast.makeText(PostpackagedetailActivity.this, "Initiating payment failed. Error", Toast.LENGTH_SHORT).show();
//
////        showToast("Initiating payment failed. Error: " + errorMessage);
//    }


    private void getUserProfile() {
        showLoader();

        retrofitService.userProfile(SavePref.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {

                try {
                    final UserProfileModel userModel = response.body();

                    UserProfileDetailModel userDetailModel = userModel.getJsonData().get(0);



                    if (userDetailModel.getActive_post_package().equalsIgnoreCase("1")) {
                        if (userDetailModel.getRemaining_post().equalsIgnoreCase("0")) {
                            checkpurchase="1";
                        } else {
                            checkpurchase="0";
                        }
                    } else {
                        checkpurchase="1";
                    }
                    hideLoader();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {
                hideLoader();
                t.printStackTrace();

            }
        });
    }

    public void startPayment() {
        Random r = new Random();
        int randomNumber = r.nextInt(100000);
//        tv.setText(String.valueOf(randomNumber));

        MERCHANT_TID="Abd_"+String.valueOf(randomNumber);
        Log.i("onCreate", "MERCHANT_TID: "+MERCHANT_TID);

//        String string_signature = PhonePe.getPackageSignature();


        PhonePe.init(PostpackagedetailActivity.this, PhonePeEnvironment.RELEASE, MERCHANT_ID, "dc1e2a94c4db43cda224701282b23a73");


        String string_signature = null;
        try {
            string_signature = PhonePe.getPackageSignature();
        } catch (PhonePeInitException e) {

        }
        Log.i("onCreate", "onCreatesign: "+string_signature);

//        String string_signature = PhonePe.getPackageSignature();


        try {
            PhonePe.setFlowId("12333");// Recommended, not mandatory , An alphanumeric string without any special character
//            List<UPIApplicationInfo> upiApps = PhonePe.getUpiApps();
            PhonePe.getUpiApps();
        } catch (PhonePeInitException exception) {
            exception.printStackTrace();
        }

        String pay = getPostPackage_inner.getPropertyPostPackagePrice();
        Double payment= Double.valueOf(pay)*100;
        JSONObject data = new JSONObject();
        try {
            data.put("merchantTransactionId", MERCHANT_TID); // String. Mandatory
            data.put("merchantUserId", MERCHANT_TID+"2658"); // String. Mandatory
            data.put("merchantId", MERCHANT_ID); // String. Mandatory
            data.put("amount", payment); // Long. Mandatory
            data.put("redirectMode", "REDIRECT"); // Long. Mandatory
            data.put("mobileNumber", savePref.getUserphone()); // String. Optional
            data.put("callbackUrl", "https://webhook.site/callback-url"); // String. Mandatory
            data.put("redirectUrl", "https://webhook.site/redirect-url"); // String. Mandatory
            JSONObject paymentInstrument = new JSONObject();
            paymentInstrument.put("type", "PAY_PAGE");
//            paymentInstrument.put("targetApp", "com.phonepe.app");
//            paymentInstrument.put("targetApp", "net.one97.paytm");
//            paymentInstrument.put("targetApp", "com.phonepe.app");
//            paymentInstrument.put("targetApp", "com.google.android.apps.nbu.paisa.user");
            data.put("paymentInstrument", paymentInstrument); // OBJECT. Mandatory
            JSONObject deviceContext = new JSONObject();
            deviceContext.put("deviceOS", "ANDROID");
            data.put("deviceContext", deviceContext);
            String payloadBase64 = android.util.Base64.encodeToString(
                    data.toString().getBytes(Charset.defaultCharset()), android.util.Base64.NO_WRAP);
            String checksum = sha256(payloadBase64 + apiEndPoint + salt) + "###1";
//            Log.d("eeeee", "onCreate: " + payloadBase64);
            Log.i("onCreate", "base64:  : "+payloadBase64);
            Log.d("eeeee", "onCreate: " + checksum);
            b2BPGRequest = new B2BPGRequestBuilder()
                    .setData(payloadBase64)
                    .setChecksum(checksum)
                    .setUrl(apiEndPoint)
                    .build();



            try {

                Intent intent = PhonePe.getImplicitIntent(PostpackagedetailActivity.this, b2BPGRequest, "");
                if (intent != null) {
                    startActivityForResult(intent, B2B_PG_REQUEST_CODE);
                }
            } catch (PhonePeInitException e) {
                e.printStackTrace();
            }
//            openpaydialog();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void checkStatus() {
        showLoader();
//        SHA256("/pg/v1/status/{merchantId}/{merchantTransactionId}" + saltKey) + "###" + saltIndex

        String xVerify = sha256("/pg/v1/status/" + MERCHANT_ID+"/"+MERCHANT_TID + salt) + "###1";
//        Log.d("phonepe", "onCreate  xverify : " + xVerify);
//
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-VERIFY", xVerify);
        headers.put("X-MERCHANT-ID", MERCHANT_ID);


//        Log.i("checkStatus", "checkStatus: "+MERCHANT_ID+"   "+MERCHANT_TID+"    "+ headers);
        retrofitServicepay.getcustomersupport(MERCHANT_ID, MERCHANT_TID,headers).enqueue(new Callback<Getresmodel>() {
            @Override
            public void onResponse(Call<Getresmodel> call, Response<Getresmodel> response) {
//                Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();


                try {
                    Toast.makeText(PostpackagedetailActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    if (response.body().getCode().equalsIgnoreCase("PAYMENT_SUCCESS")){
                        add_postpackagepurchase(response.body().getData().getTransactionId());

                    }else{
                        hideLoader();
                    }
                } catch (Exception e) {

                    hideLoader();
                }

//                Log.i("onResponse", "onResponse: "+response.body().getData().getTransactionId());
//
//                if (response.body().getSuccess()){
//                }
            }

            @Override
            public void onFailure(Call<Getresmodel> call, Throwable t) {

            }
        });

//        lifecycleScope.launch(Dispatchers.IO, () -> {
//            Response<YourResponseClass> res = ApiUtilities.getApiInterface().checkStatus(MERCHANT_ID, MERCHANT_TID, headers);
//            if (res.body() != null && res.body().success) {
//                Log.d("phonepe", "onCreate: success");
//                runOnUiThread(() -> Toast.makeText(MainActivity.this, res.body().message, Toast.LENGTH_SHORT).show());
//            }
//        });
    }

    private String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(Charsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void openpaydialog(){
        final Dialog dialog = new Dialog(PostpackagedetailActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.setContentView(R.layout.dialog_pay);

        dialog.setCancelable(false);
        Window window = dialog.getWindow();

        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout llphonepay = dialog.findViewById(R.id.llphonepay);
        LinearLayout llpaytm = dialog.findViewById(R.id.llpaytm);
        LinearLayout llgpay = dialog.findViewById(R.id.llgpay);
        ImageView imremove = dialog.findViewById(R.id.imremove);

        imremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideLoader();
                dialog.dismiss();
            }
        });
        llgpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = PhonePe.getImplicitIntent(PostpackagedetailActivity.this, b2BPGRequest, "com.google.android.apps.nbu.paisa.user");
                    if (intent != null) {
                        startActivityForResult(intent, B2B_PG_REQUEST_CODE);
                    }
                } catch (PhonePeInitException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        llphonepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = PhonePe.getImplicitIntent(PostpackagedetailActivity.this, b2BPGRequest, "com.phonepe.app");
                    if (intent != null) {
                        startActivityForResult(intent, B2B_PG_REQUEST_CODE);
                    }
                } catch (PhonePeInitException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        llpaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = PhonePe.getImplicitIntent(PostpackagedetailActivity.this, b2BPGRequest, "net.one97.paytm");
                    if (intent != null) {
                        startActivityForResult(intent, B2B_PG_REQUEST_CODE);
                    }
                } catch (PhonePeInitException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });



        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == B2B_PG_REQUEST_CODE) {


            checkStatus();
        }
    }
}