package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.forgetpassword.Forget;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetViewModel extends ViewModel
{
    private Context context;
    private MutableLiveData<LoginmModelz> loginList;

    public ForgetViewModel(Context context) {

        this.context = context;

    }

    public MutableLiveData < LoginmModelz > getForgetListObserver() {

        if (loginList == null) {
            loginList = new MutableLiveData < > ();
        }
        return loginList;
    }

    public void forgetapicall(String email)
    {
        Apiservice apiservice= RetroInstance.getRetroClient().create(Apiservice.class);
        Call<LoginmModelz> loginmModelzCall=apiservice.forgetdata(email);
        loginmModelzCall.enqueue(new Callback<LoginmModelz>() {
            @Override
            public void onResponse(Call<LoginmModelz> call, Response<LoginmModelz> response)
            {
                if(response.isSuccessful())
                {
                    visibility();

                    loginList.postValue(response.body());
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(
                                context,
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_SHORT).show();
                        visibility();
                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        visibility();
                    }
                }


            }

            @Override
            public void onFailure(Call<LoginmModelz> call, Throwable t) {
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                visibility();

            }
        });
    }
    public void visibility()
    {
        Forget.submit.setVisibility(View.VISIBLE);
        Forget.progressBar.setVisibility(View.INVISIBLE);
    }


}
