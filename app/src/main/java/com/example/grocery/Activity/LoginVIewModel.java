package com.example.grocery.Activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.grocery.database.Repository;
import com.example.grocery.models.Customer;

public class LoginVIewModel extends AndroidViewModel {
    private Repository repository;
    MutableLiveData<Boolean> loginStatus=new MutableLiveData<>();
    MutableLiveData<Boolean> registerStatus=new MutableLiveData<>();
    Integer cID=0;
    MutableLiveData<String> username=new MutableLiveData<>();

    public LoginVIewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
    }


    public MutableLiveData<Boolean> getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(MutableLiveData<Boolean> loginStatus) {
        this.loginStatus = loginStatus;
    }

    public MutableLiveData<Boolean> getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(MutableLiveData<Boolean> registerStatus) {
        this.registerStatus = registerStatus;
    }

    public Integer getcID() {
        return cID;
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String s) {
        username.postValue(s);
    }


    public void login(String username,String pass){
        int result= repository.login(username,pass);
        if(result==0){
            loginStatus.postValue(false);
        }else {
            loginStatus.postValue(true);
            cID=result;
        }

    }

    public void register(Customer customer){

        boolean status =repository.register(customer);
        if(status){
            registerStatus.postValue(true);
            username.postValue(customer.getUsername());
        }else {
            registerStatus.postValue(false);
        }
    }
}
