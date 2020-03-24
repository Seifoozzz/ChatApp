package com.example.mychatapp2.ui_Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.mychatapp2.BaseActivity;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityLoginBinding;
import com.example.mychatapp2.ui_ForgotPassword.ForgotPasswordActivity;
import com.example.mychatapp2.ui_Main.MainActivity;
import com.example.mychatapp2.ui_Register.RegisterActivity;

public class LoginActivity extends BaseActivity {

    LoginViewModel viewModel;
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setVM(viewModel);
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        viewModel.intent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if ( s.equals("Success")){
                    viewModel.onLoginClick();
                    hideProgressDialog();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                }
                else if(s!="Success" && s!="X" && !s.isEmpty()){
                    hideProgressDialog();
                    showMessage(s, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    },false);
                }
                else if (s.equals("X") ){
                    showProgressDialog("Loading");


                }
            }
        });

        viewModel.emailError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null || s.isEmpty()) {
                    binding.Email.setError(null);
                } else {
                    binding.Email.setError(s);
                }
            }
        });
        viewModel.passwordError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s == null || s.isEmpty()) {
                    binding.password.setError(null);
                } else {
                    binding.password.setError(s);
                }
            }
        });

        viewModel.Success.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s == "Forgot"){
                    startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                }
                else if(s == "signUp"){
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }

            }
        });
    }
}
