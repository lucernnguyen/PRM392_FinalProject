package com.example.grocery.Activity;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.grocery.database.Repository;
import com.example.grocery.models.Product;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private String TAG="MAINVIEWMODEL";
    private Repository repository;
    private LiveData<List<Product>> listP;

    private int getCountCart;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        listP=repository.getAllProduct();
        Log.d(TAG,"Constructor");

    }
    public Repository getRepository(){
        return repository;
    }
    public void setCountCart(int cID){
        getCountCart=repository.getCount(cID);
    }
    public int getCountCart(){
        return getCountCart;
    }
    public LiveData<List<Product>> getListP() {
        return listP;
    }
}
