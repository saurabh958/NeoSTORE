package com.example.saurabhneostore.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.drawer.EditProfile;

public class EditProfileViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private Context context;
    public EditProfileViewModelFactory(EditProfile editProfile)
    {
        this.context=editProfile;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditProfileViewModel(context);
    }
}
