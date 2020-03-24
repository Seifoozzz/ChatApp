package com.example.mychatapp2.DataBase.Daos;

import android.media.MediaPlayer;

import com.example.mychatapp2.DataBase.Models.User;
import com.example.mychatapp2.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserDao {

    public static void AddUser(User user, OnCompleteListener onCompleteListener){

        MyDataBase.GetUserRefrence()
                .document(user.getId())
                .set(user)
                .addOnCompleteListener(onCompleteListener);
    }
    public static void getCurrentUser(String userId ,
                                      OnCompleteListener<DocumentSnapshot> onCompleteListener){
        MyDataBase.GetUserRefrence()
                .document(userId)
                .get()
                .addOnCompleteListener(onCompleteListener);
    }

}
