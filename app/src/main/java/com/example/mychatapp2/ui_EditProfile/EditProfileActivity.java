package com.example.mychatapp2.ui_EditProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityEditProfileBinding;
import com.example.mychatapp2.ui_Main.MainActivity;

public class EditProfileActivity extends AppCompatActivity {
    EditProfileViewModel viewModel;
    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(this).get(EditProfileViewModel.class);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);
        binding.setVM(viewModel);
        viewModel.onStart();
        viewModel.ChangePass.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
                }
            }
        });
    }
}
