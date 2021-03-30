package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.drawer.MyOrder;
import com.example.saurabhneostore.model.myordermodel.MyOrderModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderViewModel extends ViewModel
{

    private Context context;
    private MutableLiveData<MyOrderModel>myorderlivedata;

    public MyOrderViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<MyOrderModel>getMyorderlivedata()
    {
        if(myorderlivedata==null)
        {
            myorderlivedata=new MutableLiveData<>();
        }
        return myorderlivedata;
    }

    public void loadmyorder(String access)
    {
        Apiservice apiservice= RetroInstance.getCartretrofit().create(Apiservice.class);
        Call<MyOrderModel>myOrderModelCall=apiservice.getOrderList(access);
        myOrderModelCall.enqueue(new Callback<MyOrderModel>() {
            @Override
            public void onResponse(Call<MyOrderModel> call, Response<MyOrderModel> response) {
                if(response.isSuccessful())
                {
                    Log.d("annu","in on response");
                    MyOrder.progressBar.setVisibility(View.GONE);
                    myorderlivedata.postValue(response.body());
                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(
                                context,
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                        MyOrder.progressBar.setVisibility(View.GONE);

                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        MyOrder.progressBar.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<MyOrderModel> call, Throwable t) {
                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();

                System.out.println("-------------------------------------------------------");
                System.out.println(t.getMessage());
                System.out.println("------------ff------UnSucessful------------------");
            }
        });
    }


}
