package com.example.mychatapp2.ui_AddRoom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.mychatapp2.BaseActivity;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityAddRoomBinding;
import com.example.mychatapp2.ui_Main.MainActivity;

public class AddRoomActivity extends BaseActivity {
AddRoomViewModel viewModel;
ActivityAddRoomBinding binding;

    public static final int PICK_IMAGE_REQUEST = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(this).get(AddRoomViewModel.class);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_room);
        binding.setVM(viewModel);
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        viewModel.ImageClick.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE_REQUEST);

            }
        }) ;


        viewModel.success.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("true")){
                    startActivity(new Intent(AddRoomActivity.this, MainActivity.class));
                }
            }
        });

        viewModel.progress.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    hideProgressDialog();
                    showProgressDialog("Loading");
                }

                else
                    hideProgressDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null){

            Glide.with(this).load(data.getData()).into(binding.roomImage);
            viewModel.ImageUri= data.getData();

        }
    }
}
