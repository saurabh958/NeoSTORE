package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.model.myordermodel.DetailOrder;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderViewModel extends ViewModel
{
    private Context context;
    private MutableLiveData<DetailOrder>detailOrderMutableLiveData;

    public DetailOrderViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<DetailOrder>getDetailOrderMutableLiveData()
    {
        if(detailOrderMutableLiveData==null)
        {
            detailOrderMutableLiveData=new MutableLiveData<>();
        }
        return detailOrderMutableLiveData;
    }

    public void loaddetailorder(String access,String orderid)
    {
        Apiservice apiservice= RetroInstance.getCartretrofit().create(Apiservice.class);
        Call<DetailOrder>detailOrderCall=apiservice.getOrderDetail(access,orderid);
        detailOrderCall.enqueue(new Callback<DetailOrder>() {
            @Override
            public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                if(response.isSuccessful())
                {
                    com.example.saurabhneostore.drawer.DetailOrder.progressBar.setVisibility(View.GONE);
                    detailOrderMutableLiveData.postValue(response.body());
                }
                else
                {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(
                                context,
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                        com.example.saurabhneostore.drawer.DetailOrder.progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        com.example.saurabhneostore.drawer.DetailOrder.progressBar.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<DetailOrder> call, Throwable t) {
                Toast.makeText(context,"Check Internet Connection",Toast.LENGTH_SHORT).show();
                System.out.println("-------------------------------------------------------");
                System.out.println(t.getMessage());
                System.out.println("------------ff------UnSucessful------------------");
            }
        });
    }
}
