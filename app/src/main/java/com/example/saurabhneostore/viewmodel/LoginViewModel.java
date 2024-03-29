package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel
{

    final private Context context;
    private MutableLiveData<LoginmModelz>loginList;

    public LoginViewModel(Context context) {

        this.context = context;

    }

    public MutableLiveData < LoginmModelz > getLoginListObserver() {

        if (loginList == null) {
            loginList = new MutableLiveData < > ();
        }
        return loginList;
    }

    public void makeApiCall(String emial,String Pass)
    {
        Apiservice apiservice= RetroInstance.getRetroClient().create(Apiservice.class);
        Call<LoginmModelz> loginmModelzCall=apiservice.checkdata(emial,Pass);
        loginmModelzCall.enqueue(new Callback<LoginmModelz>() {
            @Override
             public void onResponse(Call<LoginmModelz> call, Response<LoginmModelz> response) {
                if(response.isSuccessful())
                {
                    visibility();
                    loginList.postValue(response.body());
                    LoginmModelz postresponse=response.body();

//                    String fname=postresponse.getData().getFirstName();
//                    String lname=postresponse.getData().getLastName();
//                    String email=postresponse.getData().getEmail();
//                    String gend=postresponse.getData().getGender();
//                    String phoneno=postresponse.getData().getPhoneNo();
//
//                    Appconstant.mydatas.add(0,new MyData(fname,lname,email,gend,phoneno));

                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(context, jObjError.getString("user_msg"),Toast.LENGTH_SHORT).show();
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
    Login.login.setVisibility(View.VISIBLE);
    Login.login_progress.setVisibility(View.INVISIBLE);
}

}