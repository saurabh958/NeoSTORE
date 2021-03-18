package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.drawer.ProductDetail;

public class QuantityViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public QuantityViewModelFactory(ProductDetail productDetail)
    {
        this.context=productDetail;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new QuantityViewModel(context);
    }
}
