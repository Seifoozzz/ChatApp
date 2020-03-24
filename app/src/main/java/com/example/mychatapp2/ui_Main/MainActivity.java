package com.example.mychatapp2.ui_Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mychatapp2.BaseActivity;
import com.example.mychatapp2.DataBase.Daos.MessageDao;
import com.example.mychatapp2.DataBase.Daos.RoomDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.Message;
import com.example.mychatapp2.DataBase.Models.Room;
import com.example.mychatapp2.R;
import com.example.mychatapp2.databinding.ActivityMainBinding;
import com.example.mychatapp2.ui_AddRoom.AddRoomActivity;
import com.example.mychatapp2.ui_AddRoom.RoomsAdabter;
import com.example.mychatapp2.ui_Chat.ChatActivity;
import com.example.mychatapp2.ui_Chat.ChatViewModel;
import com.example.mychatapp2.ui_EditProfile.EditProfileActivity;
import com.example.mychatapp2.ui_Login.LoginActivity;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends BaseActivity {
     MainViewModel viewModel;
     ActivityMainBinding binding;
   RoomsAdabter adabter;
   RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setVM(viewModel);
        viewModel.getAllRooms();
        adabter=new RoomsAdabter(this,null);
        layoutManager=new LinearLayoutManager(MainActivity.this);
       binding.RecyclerView.setLayoutManager(layoutManager);
       binding.RecyclerView.setAdapter(adabter);



       adabter.setOnItemClickListener(new RoomsAdabter.onItemClickListener() {
           @Override
           public void onItemClick(int position, Room room) {
               viewModel.roomput.setValue(room);
               startActivity(new Intent(MainActivity.this, ChatActivity.class));

           }
       });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new
                ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int swipeDir) {
                        final int position = viewHolder.getAdapterPosition();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        if (swipeDir == ItemTouchHelper.LEFT ||swipeDir == ItemTouchHelper.RIGHT ) {
                            final Room deletedRoom = viewModel.roomsList.getValue().get(position);
                            viewModel.roomput.setValue(deletedRoom);
                            String deletedId = deletedRoom.getRoomId();

                                        adabter.remove(position);

                                final ChatViewModel chatViewModel = new ChatViewModel();
                                chatViewModel.getAllMessages();
                                MessageDao.deleteMessageByRoomId(deletedId, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        for(Message m : chatViewModel.messageList2){
                                            MessageDao.deleteMessageByRoomId(m.getId(),
                                                    new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    });
                                        }
                                    }
                                });

                        }
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.RecyclerView);
        subscribeToLiveData();

    }

    private void subscribeToLiveData() {

        viewModel.roomsList.observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                if(rooms!=null){
                    adabter.changeData(viewModel.roomsList.getValue());

                }
            }
        });

        viewModel.OnAddClick.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    startActivity(new Intent(MainActivity.this,AddRoomActivity.class));

                }
            }
        });
        binding.ToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                 if (item.getItemId() == R.id.Myprofile){
                    startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                }
                 else if (item.getItemId() ==  R.id.Logout){
                     viewModel.logOut();
                     startActivity(new Intent(MainActivity.this, LoginActivity.class));
                     finish();}
                return true;
            }
        });
    }


}
