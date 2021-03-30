package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.drawer.ResetPass;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassViewModel extends ViewModel
{

    final private Context context;
    private MutableLiveData<LoginmModelz> resetpasswrd;


    public ResetPassViewModel(Context context) {

        this.context = context;

    }

    public MutableLiveData < LoginmModelz > getResetpasswrdlistObserver() {

        if (resetpasswrd == null) {
            resetpasswrd = new MutableLiveData < > ();

        }
        return resetpasswrd;
    }

    public void EditPasswordApiCall(String access,String oldpass,String newpass,String Confpass)
    {
        Apiservice apiservice = RetroInstance.getRetroClient().create(Apiservice.class);
        Log.d("editprofile","in apicall method");
        Call<LoginmModelz> resetpasswrdCall = apiservice.editPass(access,oldpass,newpass,Confpass);
        resetpasswrdCall.enqueue(new Callback<LoginmModelz>() {
            @Override
            public void onResponse(Call<LoginmModelz> call, Response<LoginmModelz> response) {
                if(response.isSuccessful())
                {
                    ResetPass.rstprogressBar.setVisibility(View.GONE);
                    ResetPass.reset.setVisibility(View.VISIBLE);
                    resetpasswrd.postValue(response.body());
                    Log.d("resetpass","in onresponse success");
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("resetpass","in else part of response ");
                        Log.d("resetpass",response.raw().toString());
                        Toast.makeText(context, jObjError.getString("user_msg"),Toast.LENGTH_SHORT).show();
                        ResetPass.rstprogressBar.setVisibility(View.GONE);
                        ResetPass.reset.setVisibility(View.VISIBLE);
                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        ResetPass.rstprogressBar.setVisibility(View.GONE);
                        ResetPass.reset.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginmModelz> call, Throwable t) {
                Log.d("resetpass","in on failure");
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                ResetPass.rstprogressBar.setVisibility(View.GONE);
                ResetPass.reset.setVisibility(View.VISIBLE);
            }
        });
    }


}
