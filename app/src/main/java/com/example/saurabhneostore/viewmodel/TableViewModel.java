package com.example.saurabhneostore.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saurabhneostore.adapters.TableListAdapter;
import com.example.saurabhneostore.drawer.TableList;
import com.example.saurabhneostore.model.Datum;
import com.example.saurabhneostore.model.TableModel;
import com.example.saurabhneostore.network.Apiservice;
import com.example.saurabhneostore.network.RetroInstance;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableViewModel extends ViewModel {
    Context context;
    TableListAdapter adapter;


    private MutableLiveData<List<Datum>> tableModelMutableLiveData;

    public TableViewModel() {
//        tableModelMutableLiveData=new MutableLiveData<List<Datum>>();

    }

    public MutableLiveData<List<Datum>> getTableModelMutableLiveDataObserver() {
        if (tableModelMutableLiveData == null) {
            Log.d("pintu", "getmutablelivedataobserver");
            tableModelMutableLiveData = new MutableLiveData<List<Datum>>();
            makeApiCall("1", 10, 1);


        }
        return tableModelMutableLiveData;
    }


    public void makeApiCall(String category, Integer limitz, Integer pagez) {
        Log.d("pintu", "inmakeapicall");
        Apiservice apiservice = RetroInstance.getProductRetrofit().create(Apiservice.class);
        Call<TableModel> getProductCall = apiservice.getProduct(category, limitz, pagez);
        getProductCall.enqueue(new Callback<TableModel>() {
            @Override
            public void onResponse(Call<TableModel> call, Response<TableModel> response) {
                if (response.isSuccessful()) {
                    List<Datum> list = response.body().getData();
                    tableModelMutableLiveData.setValue(list);
                    //tableModelMutableLiveData.postValue(response.body());
                    Log.w("pintu", new Gson().toJson(response));

//                    Log.d("pintu", String.valueOf(response.body().getData()));
//                    List list = java.util.Arrays.asList(response.body().getData());
//                    Log.d("pintu", String.valueOf(list.size()));

                    //tableModels=new ArrayList<>(list);
//                    tableListAdapter=new TableListAdapter(TableList.this,response.body().getData());
//                    recyclerView.setAdapter(tableListAdapter);
//                    tableListAdapter.notifyDataSetChanged();

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //Toast.makeText(
//                                context,
//                                jObjError.getString("user_msg"),
//                                Toast.LENGTH_SHORT).show();
                        TableList.progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {

                        //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TableModel> call, Throwable t) {
                tableModelMutableLiveData.postValue(null);
                //Toast.makeText(context, "Check Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
