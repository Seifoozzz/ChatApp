package com.example.mychatapp2.ui_Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityRegisterBinding;
import com.example.mychatapp2.ui_Main.MainActivity;

public class RegisterActivity extends AppCompatActivity {

RegisterViewModel viewmodel ;
ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        binding.setVM(viewmodel);

        SubscribeTolivedata();
    }

    private void SubscribeTolivedata() {

        viewmodel.onRegisterClick.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(new Intent(RegisterActivity.this,ImageActivity.class));

                }

            }
        });
        viewmodel.userNameError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null || s.isEmpty()){
                    binding.userName.setError(null);
                }else{
                    binding.userName.setError(s);
                }
            }
        });

        viewmodel.emailError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s==null || s.isEmpty()){
                    binding.Email.setError(null);
                }else{
                    binding.Email.setError(s);
                }
            }
        });
        viewmodel.passwordError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s==null || s.isEmpty()){
                    binding.password.setError(null);
                }else{
                    binding.password.setError(s);
                }
            }
        });

        viewmodel.passwordConfirmError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s==null || s.isEmpty()){
                    binding.passwordConfirm.setError(null);
                }else{
                    binding.passwordConfirm.setError(s);
                }
            }
        });
    }
    }

