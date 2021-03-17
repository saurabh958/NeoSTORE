package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.model.RateModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateVM extends ViewModel {

    private Context context;
    private MutableLiveData<RateModel> rate_list;


    public RateVM(Context context) {

        this.context = context;
// this.loginModel=loginModel;
// loginList = new MutableLiveData<>();
    }



    public MutableLiveData<RateModel> getRatingObserver() {

        if (rate_list == null) {
            rate_list = new MutableLiveData<>();
// loadProductLists();
        }
        return rate_list;
    }

    public void loadRating(String product_Id,String rating) {
        Apiservice apiService = RetroInstance.getProductRetrofit().create(Apiservice.class);

        Call<RateModel> call = apiService.ratePost(product_Id,rating);
        System.out.println("--------------------------TABELSvm-------------");
        call.enqueue(new Callback<RateModel>() {
            @Override
            public void onResponse(Call<RateModel> call, Response<RateModel> response) {
                System.out.println("---------------onResponse-----------TABELSvm-------------");
                if (response.isSuccessful()) {
                    response.code();
                    rate_list.postValue(response.body());


                    Toast.makeText(context, response.body().getUserMsg(), Toast.LENGTH_SHORT).show();

                } else {
                    System.out.println(" response code " +response.code());
                    System.out.println(" response code " +response.message());
                    Log.d("Saurabh", response.errorBody().toString());
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        System.out.println("-----DM----------------------------------------");
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
            public void onFailure(Call<RateModel> call, Throwable t) {

                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
                System.out.println("-------------------------------------------------------");
                System.out.println(t.getMessage());
                System.out.println("------------ff------UnSucessful------------------");
            }
        });
    }
}


