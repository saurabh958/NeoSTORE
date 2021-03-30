package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.model.FetchAccount.Data;
import com.example.saurabhneostore.model.FetchAccount.FetchDetailModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchViewModel extends ViewModel
{
    private Context context;
    private MutableLiveData<Data>fetchlivedata;

    public FetchViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<Data>getFetchlivedata()
    {
        if(fetchlivedata==null)
        {
            fetchlivedata=new MutableLiveData<>();
        }
        return fetchlivedata;
    }

    public void FetchDetail(String access)
    {

        Apiservice apiservice= RetroInstance.getRetroClient().create(Apiservice.class);
        Call<FetchDetailModel> fetchDetailModelCall=apiservice.fetchAccountDetail(access);
        fetchDetailModelCall.enqueue(new Callback<FetchDetailModel>() {
            @Override
            public void onResponse(Call<FetchDetailModel> call, Response<FetchDetailModel> response) {
                if(response.isSuccessful())
                {
                    fetchlivedata.postValue(response.body().getData());
                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("user_msg"),Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }

            }

            @Override
            public void onFailure(Call<FetchDetailModel> call, Throwable t) {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
