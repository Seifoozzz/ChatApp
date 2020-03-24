package com.example.mychatapp2.ui_ChangePass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.mychatapp2.BaseActivity;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityChangePassBinding;
import com.example.mychatapp2.ui_Main.MainActivity;

public class ChangePassActivity extends BaseActivity {
    ChangePassViewModel viewModel;
    ActivityChangePassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        viewModel= ViewModelProviders.of(this).get(ChangePassViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_pass);
        binding.setVM(viewModel);
        viewModel.ali();

        viewModel.isValid.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showMessage("Password Changed Successfully", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            startActivity(new Intent(ChangePassActivity.this, MainActivity.class));
                        }
                    },true);

                }
            }
        });
    }
    }

