package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.drawer.AddAddress;
import com.example.saurabhneostore.model.Order.OrderModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {

    private Context context;
    private MutableLiveData<OrderModel> orderMutableLiveData;

    public OrderViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<OrderModel> getOrderMutableObserver() {
        if (orderMutableLiveData == null) {
            orderMutableLiveData = new MutableLiveData<>();
        }
        return orderMutableLiveData;
    }

    public void makeordercall(String access, String Address) {
        Apiservice apiservice = RetroInstance.getCartretrofit().create(Apiservice.class);
        Call<OrderModel> orderModelCall = apiservice.orderItem(access, Address);
        orderModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                if (response.isSuccessful()) {
                    visibility();


                    orderMutableLiveData.postValue(response.body());
                    Toast.makeText(context, response.body().getUserMsg(), Toast.LENGTH_LONG).show();


                } else {

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(
                                context,
                                jObjError.getString("user_msg"),
                                Toast.LENGTH_LONG).show();
                        visibility();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        visibility();
                    }

                }
            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                visibility();

            }
        });
    }


    public void visibility() {
        AddAddress.place_order.setVisibility(View.GONE);
        AddAddress.add_progress.setVisibility(View.INVISIBLE);
    }


}
