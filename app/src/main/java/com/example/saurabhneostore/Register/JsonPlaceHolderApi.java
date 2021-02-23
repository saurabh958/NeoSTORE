package com.example.saurabhneostore.Register;

import com.example.saurabhneostore.Login.Apisuccess;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi
{
    @FormUrlEncoded
    @POST("register")
    Call<Apisuccess>createdata(@Field("first_name") String first_name,
                               @Field("last_name") String last_name,
                               @Field("email") String email,
                               @Field("password") String password,
                               @Field("confirm_password") String confirm_password,
                               @Field("gender") String gender,
                               @Field("phone_no") String phone_no);



}
