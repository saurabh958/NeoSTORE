package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.drawer.EditProfile;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileViewModel extends ViewModel
{
    final private Context context;
    private MutableLiveData<LoginmModelz> editprofilelist;

    public EditProfileViewModel(Context context) {

        this.context = context;

    }

    public MutableLiveData < LoginmModelz > getEditprofilelistObserver() {

        if (editprofilelist == null) {
            editprofilelist = new MutableLiveData < > ();

        }
        return editprofilelist;
    }

    public void editApiCall(String access,String firstname,String lastname,String email,String dob,String pic,String phone) {
        Apiservice apiservice = RetroInstance.getRetroClient().create(Apiservice.class);
        Log.d("editprofile","in apicall method");
        Call<LoginmModelz> editprofileCall = apiservice.editDetail(access, firstname, lastname, email, dob, pic, phone);
        editprofileCall.enqueue(new Callback<LoginmModelz>() {
            @Override
            public void onResponse(Call<LoginmModelz> call, Response<LoginmModelz> response) {
                if (response.isSuccessful()) {
                    EditProfile.progressBar.setVisibility(View.GONE);
                    EditProfile.submit.setVisibility(View.VISIBLE);
                    editprofilelist.postValue(response.body());

                    LoginmModelz postresponse = response.body();
                    Log.d("editprofile","in onresponse success");


                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("editprofile","in else part of response ");
                        Log.d("bechan",response.raw().toString());
                        Toast.makeText(context, jObjError.getString("user_msg"),Toast.LENGTH_SHORT).show();
                        EditProfile.progressBar.setVisibility(View.GONE);
                        EditProfile.submit.setVisibility(View.VISIBLE);
                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        EditProfile.progressBar.setVisibility(View.GONE);
                        EditProfile.submit.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginmModelz> call, Throwable t) {
                Log.d("editprofile","in on failure");
                Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                EditProfile.progressBar.setVisibility(View.GONE);
                EditProfile.submit.setVisibility(View.VISIBLE);

            }
        });


    }
}
