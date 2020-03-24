package com.example.mychatapp2.ui_EditRoom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.mychatapp2.BaseActivity;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityEditRoomBinding;
import com.example.mychatapp2.ui_Main.MainActivity;

public class EditRoomActivity extends BaseActivity {
    EditRoomViewModel viewModel;
    ActivityEditRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        viewModel= ViewModelProviders.of(this).get(EditRoomViewModel.class);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_room);
        binding.setVM(viewModel);
        viewModel.onStart();

        viewModel.success.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!="X"){
                    startActivity(new Intent(EditRoomActivity.this, MainActivity.class));
                } else {
                    showMessage("Only Admin Can Edit Room", "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    },true);
                }
            }
        });

    }
    }

