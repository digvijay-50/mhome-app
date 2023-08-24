package com.example.otpregisterloginhome.RetrofitUtils;


import com.example.otpregisterloginhome.Activities.ProfileUpdateModel;
import com.example.otpregisterloginhome.Activities.VerifyOTPModel;
import com.example.otpregisterloginhome.ModelClasses.BannerModel;
import com.example.otpregisterloginhome.ModelClasses.FavModel;
import com.example.otpregisterloginhome.ModelClasses.GetAdvertisementbanner;
import com.example.otpregisterloginhome.ModelClasses.GetAdvocate;
import com.example.otpregisterloginhome.ModelClasses.GetCity;
import com.example.otpregisterloginhome.ModelClasses.GetNotification;
import com.example.otpregisterloginhome.ModelClasses.GetProperty;
import com.example.otpregisterloginhome.ModelClasses.GetPropertyPostPackage;
import com.example.otpregisterloginhome.ModelClasses.GetState;
import com.example.otpregisterloginhome.ModelClasses.ImagestorageModel;
import com.example.otpregisterloginhome.ModelClasses.LoginModel;
import com.example.otpregisterloginhome.ModelClasses.SettingsModel;
import com.example.otpregisterloginhome.ModelClasses.UpdateModel;
import com.example.otpregisterloginhome.ModelClasses.UserProfileModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface RetrofitService {


    @Multipart
    @POST("api/admin/add_image_storage")
    Call<ImagestorageModel> add_image_storage(
            @Part MultipartBody.Part imageurl
    );


    @FormUrlEncoded
    @POST("api/admin/user_forgot_passsword")
    Call<UpdateModel> updatePassword(
            @Field("user_id") String user_id,
            @Field("phone") String phone,
            @Field("new_password") String new_password);


    @FormUrlEncoded
    @POST("api/admin/user_forgot_verify_otp")
    Call<UpdateModel> changePassOTP(
            @Field("phone") String phone);




    @FormUrlEncoded
    @POST("api/admin/add_postpackagepurchase")
    Call<UpdateModel> add_postpackagepurchase(
            @Field("user_id") String user_id,
            @Field("property_post_package_id") String property_post_package_id,
            @Field("txtamount") String txtamount,
            @Field("orderid") String orderid,
            @Field("checksum") String checksum,
            @Field("txtid") String txtid);





    @FormUrlEncoded
    @POST("api/admin/user_mobile_register_new")
    Call<UpdateModel> userMobileRegisteration
            (@Field("phone") String phone,
             @Field("token") String token
            );

    @FormUrlEncoded
    @POST("api/admin/get_property_post_package")
    Call<GetPropertyPostPackage> get_property_post_package
            (@Field("user_id") String user_id
            );

    @FormUrlEncoded
    @POST("api/admin/get_advertisementbanner")
    Call<GetAdvertisementbanner> get_advertisementbanner
            (
                    @Field("user_id") String user_id,
                    @Field("banner_section") String banner_section,
                    @Field("city") String city
            );


    @FormUrlEncoded
    @POST("api/admin/get_fav")
    Call<GetProperty> get_fav
            (@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/admin/get_property")
    Call<GetProperty> get_property
            (@Field("user_id") String user_id,
             @Field("search") String search,
             @Field("short_by") String short_by,
             @Field("property_type") String property_type,
             @Field("property_for") String property_for,
             @Field("property_sub_type") String property_sub_type,
             @Field("property_state") String property_state,
             @Field("property_city") String property_city,
             @Field("property_area") String property_area,
             @Field("property_configuration") String property_configuration,
             @Field("furnishing_detail") String furnishing_detail,
             @Field("floor_detail") String floor_detail,
             @Field("available_for") String available_for,
             @Field("price_detail") String price_detail,
             @Field("price_value_one") String price_value_one,
             @Field("price_value_two") String price_value_two,
             @Field("property_facing") String property_facing,
             @Field("page") int page
            );


    @FormUrlEncoded
    @POST("api/admin/get_user_rent_property")
    Call<GetProperty> get_user_rent_property
            (@Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("api/admin/get_user_view_property_log")
    Call<GetProperty> get_user_view_property_log
            (@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/admin/get_user_sell_property")
    Call<GetProperty> get_user_sell_property
            (@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api/admin/get_single_property")
    Call<GetProperty> get_single_property
            (@Field("user_id") String user_id,
             @Field("property_id") String property_id);


    @FormUrlEncoded
    @POST("api/admin/add_fav")
    Call<FavModel> add_fav
            (@Field("user_id") String user_id,
             @Field("property_id") String property_id);

    @FormUrlEncoded
    @POST("api/admin/add_contact_property")
    Call<UpdateModel> add_contact_property
            (@Field("property_user_id") String property_user_id,
             @Field("property_id") String property_id,
             @Field("property_contact_user_id") String property_contact_user_id
            );

    @FormUrlEncoded
    @POST("api/admin/get_city")
    Call<GetCity> get_city(@Field("state_id") String state_id);

    @FormUrlEncoded
    @POST("api/admin/add_advertisment_form")
    Call<UpdateModel> add_advertisment_form(
            @Field("user_id") String user_id,
            @Field("form_name") String form_name,
            @Field("form_mobile_no") String form_mobile_no,
            @Field("form_city") String form_city,
            @Field("form_state") String form_state,
            @Field("form_pincode") String form_pincode
    );


    @GET("api/admin/get_state")
    Call<GetState> get_state();

    @FormUrlEncoded
    @POST("api/admin/get_advocate")
    Call<GetAdvocate> get_advocate(
            @Field("advocate_city") String advocate_city
    );


    @FormUrlEncoded
    @POST("api/admin/user_verify_otp")
    Call<VerifyOTPModel> postConfirmOTP
            (@Field("phone") String phone,
             @Field("confirm_code") String confirm_code);


    @FormUrlEncoded
    @POST("api/admin/user_login")
    Call<LoginModel> userLogin(@Field("email") String email,
                               @Field("password") String password);


    @FormUrlEncoded
    @POST("api/admin/get_notification")
    Call<GetNotification> get_notification(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("api/admin/user_profile")
    Call<UserProfileModel> userProfile(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("api/admin/user_change_passsword")
    Call<UpdateModel> changePassword(@Field("new_password") String new_password,
                                     @Field("old_password") String old_password,
                                     @Field("user_id") String user_id);

//    @FormUrlEncoded
//    @POST("api/admin/user_profile_update")
//    Call<UpdateModel> userProfileUpdate(
//            @Field("user_id") String user_id,
//            @Field("user_name") String user_name,
//            @Field("email") String email,
//            @Field("password") String password,
//            @Field("imageurl") String imageurl);


    @Multipart
    @POST("api/admin/user_profile_update")
    Call<ProfileUpdateModel> userProfileUpdateWithImage(
            @Part("user_id") RequestBody user_id,
            @Part("user_name") RequestBody user_name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("gender") RequestBody gender,
            @Part("user_category") RequestBody user_category,
            @Part("address") RequestBody address,
            @Part("user_state") RequestBody user_state,
            @Part("user_city") RequestBody user_city,
            @Part("area_of_location") RequestBody area_of_location,
            @Part("flat_no") RequestBody flat_no,
            @Part("aadhar_number") RequestBody aadhar_number,
            @Part MultipartBody.Part imageurl);


    @Multipart
    @POST("api/admin/add_property")
    Call<UpdateModel> add_property(
            @Part("user_id") RequestBody user_id,
            @Part("property_for") RequestBody property_for,
            @Part("property_type") RequestBody property_type,
            @Part("property_sub_type") RequestBody property_sub_type,
            @Part("property_state") RequestBody property_state,
            @Part("property_city") RequestBody property_city,
            @Part("property_area") RequestBody property_area,
            @Part("property_pincode") RequestBody property_pincode,
            @Part("property_tehsil") RequestBody property_tehsil,
            @Part("property_khasra_no") RequestBody property_khasra_no,
            @Part("property_district") RequestBody property_district,
            @Part("property_category") RequestBody property_category,
            @Part("property_configuration") RequestBody property_configuration,
            @Part("furnishing_detail") RequestBody furnishing_detail,
            @Part("floor_detail") RequestBody floor_detail,
            @Part("available_for") RequestBody available_for,
            @Part("area_detail") RequestBody area_detail,
            @Part("area_unit_detail") RequestBody area_unit_detail,
            @Part("price_detail") RequestBody price_detail,
            @Part("price_unit_detail") RequestBody price_unit_detail,
            @Part("property_title") RequestBody property_title,
            @Part("property_description") RequestBody property_description,
            @Part("property_gallery") RequestBody property_gallery,
            @Part("property_final_price") RequestBody property_final_price,
            @Part("property_facing") RequestBody property_facing,
            @Part MultipartBody.Part imageurl);

    @Multipart
    @POST("api/admin/edit_property")
    Call<UpdateModel> edit_property(
            @Part("user_id") RequestBody user_id,
            @Part("property_for") RequestBody property_for,
            @Part("property_type") RequestBody property_type,
            @Part("property_sub_type") RequestBody property_sub_type,
            @Part("property_state") RequestBody property_state,
            @Part("property_city") RequestBody property_city,
            @Part("property_area") RequestBody property_area,
            @Part("property_pincode") RequestBody property_pincode,
            @Part("property_tehsil") RequestBody property_tehsil,
            @Part("property_khasra_no") RequestBody property_khasra_no,
            @Part("property_district") RequestBody property_district,
            @Part("property_category") RequestBody property_category,
            @Part("property_configuration") RequestBody property_configuration,
            @Part("furnishing_detail") RequestBody furnishing_detail,
            @Part("floor_detail") RequestBody floor_detail,
            @Part("available_for") RequestBody available_for,
            @Part("area_detail") RequestBody area_detail,
            @Part("area_unit_detail") RequestBody area_unit_detail,
            @Part("price_detail") RequestBody price_detail,
            @Part("price_unit_detail") RequestBody price_unit_detail,
            @Part("property_title") RequestBody property_title,
            @Part("property_description") RequestBody property_description,
            @Part("property_gallery") RequestBody property_gallery,
            @Part("property_id") RequestBody property_id,
            @Part("property_final_price") RequestBody property_final_price,
            @Part("property_facing") RequestBody property_facing,

            @Part MultipartBody.Part imageurl);


    @FormUrlEncoded
    @POST("api/admin/get_banner")
    Call<BannerModel> get_banner(
            @Field("city") String city);

    @FormUrlEncoded
    @POST("api/admin/user_logout")
    Call<UpdateModel> userLogout(
            @Field("user_id") String user_id);


    @GET("api/admin/settings")
    Call<SettingsModel> getSettings();


}