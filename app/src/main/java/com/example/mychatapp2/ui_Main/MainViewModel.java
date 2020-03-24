package com.example.mychatapp2.ui_Main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Daos.RoomDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.Room;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
public MutableLiveData<Boolean> OnAddClick =new MutableLiveData<>(false);
public MutableLiveData SuccessRoom = new MutableLiveData("");
public static MutableLiveData <Room>roomput=new MutableLiveData();
public MutableLiveData<List<Room>> roomsList = new MutableLiveData<>();
public void OnAddClick(){
    OnAddClick.setValue(true);
}
List<Room>roomList;
public void getAllRooms(){

    RoomDao.getAllRooms(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                roomList = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    Room room = document.toObject(Room.class);
                    roomList.add(room);
                }

                roomsList.setValue(roomList);
                SuccessRoom.setValue("success");
            } else {
                Log.e("Error getting rooms", task.getException().getLocalizedMessage());
            }
        }
    });
}

    public List<Room> roomList2 = new ArrayList<>();
    private void subscribeRoom() {
        for (Room ro : roomList) {
            roomList2.add(ro);
        }
        roomsList.setValue(roomList2);
    }
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public void logOut(){
        mAuth.signOut();
        Datautils.CurrentUser=null;
    }





    }

