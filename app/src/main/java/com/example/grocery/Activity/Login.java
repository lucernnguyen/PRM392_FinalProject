package com.example.grocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.grocery.databinding.ActivityLoginBinding;


public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private static LoginVIewModel loginVIewModel;


    public static LoginVIewModel getLoginVIewModel() {
        return loginVIewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginVIewModel= new  ViewModelProvider(this).get(LoginVIewModel.class);

    }
}