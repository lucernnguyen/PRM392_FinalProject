package com.example.grocery.Activity;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

public class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
   private int cID;
   private Application application;
   private String TAG="VIEWMODELFACTORY";
   public ViewModelFactory(Application application,int cID){
        this.application=application;
       this.cID=cID;
   }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        try{
            if(modelClass.isAssignableFrom(CartViewModel.class)){
                return (T) new CartViewModel(application,cID);
            }

        }catch (Exception exception){
            Log.d(TAG,exception.getMessage());
        }
        return (T) new CartViewModel(application,cID);
    }


}
