package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.model.QuantityModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuantityViewModel extends ViewModel
{

    private Context context;
    private MutableLiveData<QuantityModel>quantityLiveData;

    public QuantityViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<QuantityModel>getQuantityLiveData()
    {
        if(quantityLiveData==null)
        {
            quantityLiveData=new MutableLiveData<>();
        }
        return quantityLiveData;
    }

    public void sendQuantity(String access,String productid,String quantity)
    {
        Apiservice apiservice= RetroInstance.getCartretrofit().create(Apiservice.class);
        Call<QuantityModel>quantityModelCall=apiservice.quantityAdd(access,productid,quantity);
        quantityModelCall.enqueue(new Callback<QuantityModel>() {
            @Override
            public void onResponse(Call<QuantityModel> call, Response<QuantityModel> response) {
                if(response.isSuccessful())
                {
                    quantityLiveData.postValue(response.body());
                    Toast.makeText(context,response.body().getUserMsg(),Toast.LENGTH_LONG).show();

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
            public void onFailure(Call<QuantityModel> call, Throwable t) {

                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
                System.out.println("-------------------------------------------------------");
                System.out.println(t.getMessage());
                System.out.println("------------ff------UnSucessful------------------");

            }
        });
    }





}
