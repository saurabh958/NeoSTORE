package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.util.Log;
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

public class EditCartViewModel extends ViewModel {



    private Context context;
    private MutableLiveData<QuantityModel>editcartlivedata;

    public EditCartViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<QuantityModel>getEditcartlivedata()
    {
        if(editcartlivedata==null)
        {
            editcartlivedata=new MutableLiveData<>();
        }
        return editcartlivedata;
    }



    public void editCartitem(String access,String product_id,String quantityz)
    {
        Apiservice apiservice= RetroInstance.getCartretrofit().create(Apiservice.class);
        Call<QuantityModel>editcartcall=apiservice.editItem(access, product_id, quantityz);
        editcartcall.enqueue(new Callback<QuantityModel>() {
            @Override
            public void onResponse(Call<QuantityModel> call, Response<QuantityModel> response) {
                if(response.isSuccessful())
                {
                    editcartlivedata.postValue(response.body());
                    String msg=response.body().getUserMsg();
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();

                }
                else
                {
                    Log.d("annu","response fail error msg is"+response.errorBody().toString());
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
