package com.example.grocery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.grocery.Activity.Login;
import com.example.grocery.Activity.LoginVIewModel;
import com.example.grocery.R;
import com.example.grocery.databinding.FragmentRegisterBinding;
import com.example.grocery.models.Customer;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private NavController navigation;
    private LoginVIewModel loginVIewModel;
    private Customer customer;
    private Login login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRegisterBinding.inflate(LayoutInflater.from(requireContext()),container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){

        navigation= Navigation.findNavController(binding.getRoot());
        login= (Login) requireActivity();
        binding.txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation.navigate(R.id.loginFragment);
            }
        });
        loginVIewModel= new ViewModelProvider(requireActivity()).get(LoginVIewModel.class);
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickRegister();
            }
        });

        loginVIewModel.getRegisterStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    navigation.navigate(R.id.action_registerFragment_to_loginFragment);
                    binding.txtErr.setVisibility(View.INVISIBLE);
                }else {
                    binding.txtErr.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void onCLickRegister(){
        String username=binding.username.getText().toString();
        String password=binding.password.getText().toString();
        String name=binding.name.getText().toString();
        String sdt=binding.sdt.getText().toString();
        String confirmPass=binding.confirmPass.getText().toString();
        if(!confirmPass.equals(password)){
            Toast.makeText(requireContext(),"COnfirm pass not correct",Toast.LENGTH_SHORT).show();
        }else if(username.isEmpty()||password.isEmpty()||name.isEmpty()||sdt.isEmpty()||confirmPass.isEmpty()){
            Toast.makeText(requireContext(), "Enter enought fileds", Toast.LENGTH_SHORT).show();
        }else {
            Customer customer=new Customer(0,username,password,name,sdt,10000);
            loginVIewModel.register(customer);
            Toast.makeText(requireContext(), "Register success", Toast.LENGTH_SHORT).show();
        }
    }
}