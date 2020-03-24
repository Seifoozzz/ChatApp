package com.example.mychatapp2.ui_Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mychatapp2.DataBase.Daos.MessageDao;
import com.example.mychatapp2.DataBase.Models.Message;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityChatBinding;
import com.example.mychatapp2.ui_EditRoom.EditRoomActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
 ChatViewModel viewModel;
 ActivityChatBinding binding;
 MessageAdapter adapter;
 RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        binding.setVM(viewModel);
        viewModel.getAllMessages();
        adapter=new MessageAdapter(this,null);
        layoutManager=new LinearLayoutManager(ChatActivity.this);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        binding.chatRecycler.setLayoutManager(layoutManager);
        binding.chatRecycler.setAdapter(adapter);
subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        viewModel.messageList.observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                if (messages!=null){
                    adapter.changeData(viewModel.messageList.getValue());
                }
            }
        });
        binding.chatToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() ==R.id.EditRoom)
                    startActivity(new Intent(ChatActivity.this, EditRoomActivity.class));
                return true;
            }
        });

        viewModel.RoomName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s!=null){
                    binding.chatToolbar.setTitle(s);
                }
            }
        });
    }
}
