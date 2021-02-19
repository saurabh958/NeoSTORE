package com.example.saurabhneostore.Register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi
{

    @POST("register")
    Call<RegisterModel>createdata(@Body RegisterModel registerModel);
}
