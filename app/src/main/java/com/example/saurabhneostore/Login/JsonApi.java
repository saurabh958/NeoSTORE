package com.example.saurabhneostore.Login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonApi
{
    @FormUrlEncoded
    @POST("login")
    Call<Apisuccess>checkdata(@Field("email") String email,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("forgot")
    Call<Apisuccess>forgetdata(@Field("email") String email);
}
