package com.example.saurabhneostore.network;

import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.model.TableModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apiservice
{
    @FormUrlEncoded
    @POST("login")
    Call<LoginmModelz>checkdata(@Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("forgot")
    Call<LoginmModelz>forgetdata(@Field("email") String email);

    @FormUrlEncoded
    @POST("register")
    Call<LoginmModelz>createdata(@Field("first_name") String first_name,
                               @Field("last_name") String last_name,
                               @Field("email") String email,
                               @Field("password") String password,
                               @Field("confirm_password") String confirm_password,
                               @Field("gender") String gender,
                               @Field("phone_no") String phone_no);

    @GET("getList")
    Call<TableModel>getProduct(@Query("product_category_id")String categoryid,
                               @Query("limit")Integer limt,
                               @Query("page")Integer pagez);
    @FormUrlEncoded
    @POST("update")
    Call<LoginmModelz>editDetail(@Header("access_token") String token,
                                 @Field("first_name")String fname,
                                 @Field("last_name")String lname,
                                 @Field("email")String email,
                                 @Field("dob")String dob,
                                 @Field("profile_pic")String pic,
                                 @Field("phone_no")String phone);

    @FormUrlEncoded
    @POST("change")
    Call<LoginmModelz>editPass(@Header("access_token")String token,
                               @Field("old_password")String old,
                               @Field("password")String pass,
                               @Field("confirm_password")String confpass);

}
