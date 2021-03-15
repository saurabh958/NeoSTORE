package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel
{
    final private Context context;
    private MutableLiveData<LoginmModelz> loginList;

    public RegisterViewModel(Context context) {

        this.context = context;

    }
    public MutableLiveData < LoginmModelz > getRegisterListObserver() {

        if (loginList == null) {
            loginList = new MutableLiveData < > ();
        }
        return loginList;
    }

    public void makeregisterapicall(String firstname, String lastname, String email, String password,
                                    String confirmpassword, String gender, String phonenumber)
    {
        Apiservice apiservice= RetroInstance.getRetroClient().create(Apiservice.class);
        Call<LoginmModelz> loginmModelzCall=apiservice.createdata(firstname, lastname, email, password, confirmpassword,
                gender, phonenumber);
        loginmModelzCall.enqueue(new Callback<LoginmModelz>() {
            @Override
            public void onResponse(Call<LoginmModelz> call, Response<LoginmModelz> response) {
                if(response.isSuccessful()){
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
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginmModelz> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }

}
