package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.drawer.DetailOrder;

public class DetailOrderViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private Context context;

    public DetailOrderViewModelFactory(DetailOrder detailOrder) {
        this.context = detailOrder;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new DetailOrderViewModel(context);
    }
}
