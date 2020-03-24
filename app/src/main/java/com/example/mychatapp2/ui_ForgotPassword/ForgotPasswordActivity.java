package com.example.mychatapp2.ui_ForgotPassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.mychatapp2.BaseActivity;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityForgotPasswordBinding;
import com.example.mychatapp2.ui_Login.LoginActivity;

public class ForgotPasswordActivity extends BaseActivity {
 ForgotPasswordViewModel viewModel;
 ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forgot_password);
        binding.setVM(viewModel);
        subscribeToLiveData();

        viewModel.Success.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s==null){
                    showProgressDialog("Loading");
                }
                else if(s.equals("Success")){
                    hideProgressDialog();
                    startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                }
                else if(!s.equals("Success") && s!= null && s.isEmpty()){
                    if(!s.isEmpty()){
                        showMessage(s, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        },true);
                    }

                }
            }
        });
    }

    private void subscribeToLiveData() {
        viewModel.emailError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null || s.isEmpty()) {
                    binding.Email.setError(null);
                } else
                    binding.Email.setError(s);
            }
        });
    }
    }


