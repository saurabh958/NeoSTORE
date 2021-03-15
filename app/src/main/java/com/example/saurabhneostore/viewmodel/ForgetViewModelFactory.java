package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.forgetpassword.Forget;

public class ForgetViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private Context context;
    public ForgetViewModelFactory(Forget forget)
    {
        this.context=forget;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ForgetViewModel(context);
    }
}
