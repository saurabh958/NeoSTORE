package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.drawer.MyCart;

public class DeleteCartViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public DeleteCartViewModelFactory(MyCart myCart) {
        this.context = myCart;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new DeleteCartItemViewModel(context);
    }
}
