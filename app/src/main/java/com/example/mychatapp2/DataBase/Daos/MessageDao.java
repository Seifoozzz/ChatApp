package com.example.mychatapp2.DataBase.Daos;

import com.example.mychatapp2.DataBase.Models.Message;
import com.example.mychatapp2.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageDao {

    public static void addMessage(Message message, OnCompleteListener<Void> onCompleteListener){

        DocumentReference documentReference= MyDataBase.GetMessageRefrence().document();
        message.setId(documentReference.getId());
        documentReference.set(message).addOnCompleteListener(onCompleteListener);
    }
    public static void getRoomMessage(String RoomId , EventListener<QuerySnapshot> onCompleteListener){

        MyDataBase.GetMessageRefrence().whereEqualTo("roomId",RoomId).orderBy("time", Query.Direction.ASCENDING)

                .addSnapshotListener(onCompleteListener);
    }
    public static void deleteMessageByRoomId(String message,OnCompleteListener<Void> snapshotEventListener)  {
        MyDataBase.GetMessageRefrence()
                .document(message)
                .delete()
                .addOnCompleteListener(snapshotEventListener);
    }
    public static void getMessages( EventListener<QuerySnapshot> onCompleteListener){
        MyDataBase.GetMessageRefrence().orderBy("time", Query.Direction.DESCENDING)
                .limit(1).addSnapshotListener(onCompleteListener);

    }
}
