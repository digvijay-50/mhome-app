package com.propertybuysell.mhome.Paymentdata;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyBackendService {

//    @POST("/order")
//    Call<GetOrderIDResponse> createOrder(@Body GetOrderIDRequest request);
//    @GET("test1.php?order")
//    Call<GetOrderIDResponse> createOrder();

//    @GET("test2.php?status")
//    Call<GatewayOrderStatus> orderStatus(@Query("env") String env, @Query("order_id") String orderID,
//                                         @Query("transaction_id") String transactionID);


    @GET("test1.php?order")
    Call<GetOrderIDResponse> createOrder(@Query("purpose") String purpose,@Query("amount") String amount,
                                         @Query("buyer_name") String buyer_name,
                                         @Query("email") String email,@Query("phone") String phone);


    @GET("test2.php?status")
    Call<GatewayOrderStatus> orderStatus(@Query("transaction_id") String transactionID);

    @POST("/refund")
    Call<ResponseBody> refundAmount(@Query("env") String env,
                                    @Query("transaction_id") String transaction_id,
                                    @Query("amount") String amount);
}
