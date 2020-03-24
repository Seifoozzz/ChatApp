package com.example.mychatapp2.ui_EditRoom;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Daos.RoomDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.Room;
import com.example.mychatapp2.ui_Main.MainViewModel;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EditRoomViewModel extends ViewModel {
    public ObservableField<String> roomName = new ObservableField<>("");
    public ObservableField<String> roomDesc = new ObservableField<>("");
    public ObservableField<String> roomAdmin = new ObservableField<>("");
    public ObservableField<Uri> roomPic = new ObservableField<>();

    public MutableLiveData<String> success = new MutableLiveData<>();
    public MutableLiveData<String> ayhaga = new MutableLiveData<>();

    FirebaseAuth mAuth ;
    MainViewModel viewModel;
    public void onStart(){
        viewModel = new MainViewModel();
        roomName.set(viewModel.roomput.getValue().getRoomName());
        roomDesc.set(viewModel.roomput.getValue().getRoomDesc());
        ayhaga.setValue("s");
    }

    public Room rooms = new Room();
    public Room roomdeleted = new Room();

    public void onEditroomClick() {
        mAuth = FirebaseAuth.getInstance();
        viewModel =new MainViewModel();
        if(mAuth.getCurrentUser().getUid().equals(Datautils.CurrentUser.getId())){
            rooms.setRoomDesc(roomDesc.get());
            rooms.setRoomName(roomName.get());
            rooms.setRoomId(viewModel.roomput.getValue().getRoomId());
            rooms.setImage(viewModel.roomput.getValue().getImage());
            rooms.setAdminUser(viewModel.roomput.getValue().getAdminUser());

            roomdeleted = viewModel.roomput.getValue();
            viewModel.roomList2.add(rooms);
            RoomDao.DeleteRoom(rooms, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            RoomDao.addRoom2(rooms, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    viewModel.roomput.setValue(rooms);
                    success.setValue("S");
                }
            });
        } else
            success.setValue("X");
    }

    public void onChooseImage(){
        ayhaga.setValue("x");
    }

}
