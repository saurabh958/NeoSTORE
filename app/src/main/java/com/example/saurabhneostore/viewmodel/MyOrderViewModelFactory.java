package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.drawer.MyOrder;

public class MyOrderViewModelFactory extends ViewModelProvider.NewInstanceFactory
{

    private Context context;

    public MyOrderViewModelFactory(MyOrder myOrder) {
        this.context = myOrder;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new MyOrderViewModel(context);
    }
}
