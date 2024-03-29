package com.example.saurabhneostore.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance
{


    public static String BASE_URL="http://staging.php-dev.in:8844/trainingapp/api/users/";
    public static String PRODUCT_URL="http://staging.php-dev.in:8844/trainingapp/api/products/";
    public static String CART_URL="http://staging.php-dev.in:8844/trainingapp/api/";



    private static Retrofit retrofit;
    private static Retrofit productretrofit;
    private static Retrofit cartretrofit;

    public static Retrofit getRetroClient()
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return retrofit;
    }

    public static Retrofit getProductRetrofit()
    {

        if(productretrofit==null)
        {
            productretrofit=new Retrofit.Builder()
                    .baseUrl(PRODUCT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return productretrofit;
    }

    public static Retrofit getCartretrofit()
    {
        if(cartretrofit==null)
        {
            cartretrofit=new Retrofit.Builder()
                    .baseUrl(CART_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return cartretrofit;
    }

}
