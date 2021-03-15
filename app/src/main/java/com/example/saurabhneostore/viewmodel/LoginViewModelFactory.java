package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.Login.Login;

public class LoginViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private Context context;
    public LoginViewModelFactory(Login login)
    {
        this.context=login;
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(context);
    }
}