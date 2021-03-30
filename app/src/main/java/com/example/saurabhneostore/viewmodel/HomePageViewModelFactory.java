package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.homepage.Homepage;

public class HomePageViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private Context context;
    public  HomePageViewModelFactory(Homepage homepage)
    {
        this.context=homepage;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FetchViewModel(context);
    }
}
