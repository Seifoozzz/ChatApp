package com.example.mychatapp2.ui_Register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.mychatapp2.BaseActivity;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityImageBinding;

public class ImageActivity extends BaseActivity {
    RegisterViewModel viewmodel;
    ActivityImageBinding binding;
    public static final int PICK_IMAGE_REQUEST = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_image);
        viewmodel= ViewModelProviders.of(this).get(RegisterViewModel.class);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_image);
        binding.setVM(viewmodel);
        SubscribeTolivedata();

    }

    private void SubscribeTolivedata() {
        viewmodel.onimageClick.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    getUri();
                }
            }
        });

        viewmodel.progressbar.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    showProgressDialog("Loading...");
                }
                else
                    hideProgressDialog();
            }
        });
    }

    private void getUri() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null){

            Glide.with(this).load(data.getData()).into(binding.circleImageView);
            viewmodel.imageUri= data.getData();

        }
    }
}
