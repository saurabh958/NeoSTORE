package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.model.MyCart.MyCartModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartViewModel extends ViewModel {


    private Context context;
    private MutableLiveData<MyCartModel>mycartlivedata;

    public MyCartViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<MyCartModel>getCartDataLiveData()
    {
        if(mycartlivedata==null)
        {
            mycartlivedata=new MutableLiveData<>();
        }
        return mycartlivedata;
    }

    public void loadcart(String access)
    {
        Log.d("annu","in loadcartapicall");

        Apiservice apiservice= RetroInstance.getCartretrofit().create(Apiservice.class);
        Call<MyCartModel>cartModelCall=apiservice.getCartDetail(access);
        cartModelCall.enqueue(new Callback<MyCartModel>() {
            @Override
            public void onResponse(Call<MyCartModel> call, Response<MyCartModel> response) {
                if(response.isSuccessful())
                {
                    Log.d("annu","in on response if condition");

                    mycartlivedata.postValue(response.body());
                    Toast.makeText(context,"Data Loaded",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(
                                context,
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyCartModel> call, Throwable t) {
                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
                System.out.println("-------------------------------------------------------");
                System.out.println(t.getMessage());
                System.out.println("------------ff------UnSucessful------------------");
            }
        });
    }






}
