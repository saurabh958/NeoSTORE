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

public class DeleteCartItemViewModel extends ViewModel
{


    private Context context;
    private MutableLiveData<QuantityModel>deletecartlivedata;

    public DeleteCartItemViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<QuantityModel>getDeletecartlivedata()
    {
        if(deletecartlivedata==null)
        {
            deletecartlivedata=new MutableLiveData<>();
        }
        return deletecartlivedata;
    }

    public void deleteCartItem(String access,String productid)
    {
        Log.d("annu","in deletecart method begin to call ");
        Apiservice apiservice= RetroInstance.getCartretrofit().create(Apiservice.class);
        Call<QuantityModel>deleteCartCall=apiservice.deleteItem(access,productid);
        deleteCartCall.enqueue(new Callback<QuantityModel>() {
            @Override
            public void onResponse(Call<QuantityModel> call, Response<QuantityModel> response) {
                if(response.isSuccessful())
                {
                    Log.d("annu","in onresponse of deletecart");
                    deletecartlivedata.postValue(response.body());
                    Toast.makeText(context,"Item Deleted from cart",Toast.LENGTH_LONG).show();

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
