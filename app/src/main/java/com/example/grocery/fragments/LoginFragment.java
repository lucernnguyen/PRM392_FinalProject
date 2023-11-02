package com.example.grocery.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.grocery.Activity.Login;
import com.example.grocery.Activity.LoginVIewModel;
import com.example.grocery.Activity.MainActivity;
import com.example.grocery.INTENTKEY;
import com.example.grocery.R;
import com.example.grocery.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private NavController navigation;
    private LoginVIewModel loginVIewModel;
    private String TAG="LoginFragment";
    private Login login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(LayoutInflater.from(requireContext()),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        navigation=Navigation.findNavController(binding.getRoot());
        login= (Login) requireActivity();
        binding.txtToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        loginVIewModel= new ViewModelProvider(requireActivity()).get(LoginVIewModel.class);
        if(loginVIewModel.getRegisterStatus().getValue()!=null){
            Log.d(TAG,loginVIewModel.getRegisterStatus().getValue().toString());
        }
        loginVIewModel.getUsername().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.username.setText(s);
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickLogin();
            }
        });
        loginVIewModel.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.txtErr.setVisibility(View.INVISIBLE);
                    Intent intent=new Intent(requireActivity(), MainActivity.class);
                    intent.putExtra(INTENTKEY.CID,loginVIewModel.getcID());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    binding.txtErr.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    public void onCLickLogin(){
        String username=binding.username.getText().toString();
        String password=binding.password.getText().toString();
        loginVIewModel.login(username,password);
        Toast.makeText(requireContext(), "Login success", Toast.LENGTH_SHORT).show();
    }
}