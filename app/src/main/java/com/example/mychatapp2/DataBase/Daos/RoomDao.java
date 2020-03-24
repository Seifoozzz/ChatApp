package com.example.mychatapp2.DataBase.Daos;

import com.example.mychatapp2.DataBase.Models.Room;
import com.example.mychatapp2.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RoomDao {
    public static void AddRoom(Room room , OnCompleteListener<Void> onCompleteListener){
        DocumentReference documentReference=MyDataBase.GetRoomRefrence().document();
        room.setRoomId(documentReference.getId());
        documentReference.set(room).addOnCompleteListener(onCompleteListener);
    }
    public static void getAllRooms(OnCompleteListener<QuerySnapshot> onCompleteListener){
     MyDataBase.GetRoomRefrence()
             .get()
             .addOnCompleteListener(onCompleteListener);


    }
    public static void DeleteRoom(Room room,OnCompleteListener<Void> snapshot){
        MyDataBase.GetRoomRefrence()
                .document(room.getRoomId())
                .delete()
                .addOnCompleteListener(snapshot);
    }
    public static void addRoom2(Room rooms, OnCompleteListener onCompleteListener){
        DocumentReference reference = MyDataBase.GetRoomRefrence()
                .document(rooms.getRoomId());
        reference.set(rooms).addOnCompleteListener(onCompleteListener);
    }


}
