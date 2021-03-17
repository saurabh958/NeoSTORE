package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.model.ProductModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailViewModel extends ViewModel
{
    Context context;

    private MutableLiveData<ProductModel>productModelMutableLiveData;

    public MutableLiveData<ProductModel>getProductModelMutableLiveData()
    {
        if(productModelMutableLiveData==null)
        {
            productModelMutableLiveData=new MutableLiveData<>();
        }
        return productModelMutableLiveData;
    }

    public void makeProductApiCall(String productid)
    {
        Apiservice apiservice= RetroInstance.getProductRetrofit().create(Apiservice.class);
        Call<ProductModel>getProductDetail=apiservice.getProductDetail(productid);
        getProductDetail.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                if(response.isSuccessful())
                {
                    productModelMutableLiveData.postValue(response.body());
                }

                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context,
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                productModelMutableLiveData.postValue(null);
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
