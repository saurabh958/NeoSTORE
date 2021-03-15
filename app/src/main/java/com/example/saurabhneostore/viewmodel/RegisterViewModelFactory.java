package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.Register.Register;

public class RegisterViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private Context context;
    public RegisterViewModelFactory(Register register)
    {
        this.context=register;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RegisterViewModel(context);
    }
}
