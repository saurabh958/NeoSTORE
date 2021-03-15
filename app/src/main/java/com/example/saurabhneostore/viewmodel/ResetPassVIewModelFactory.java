package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.drawer.ResetPass;

public class ResetPassVIewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;
    public ResetPassVIewModelFactory(ResetPass resetPass)
    {
        this.context=resetPass;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ResetPassViewModel(context);
    }

}
