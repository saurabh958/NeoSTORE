package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.drawer.AddAddress;

public class OrderViewModelFactory extends ViewModelProvider.NewInstanceFactory
{


    private Context context;

    public OrderViewModelFactory(AddAddress addAddress) {
        this.context = addAddress;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OrderViewModel(context);
    }
}
