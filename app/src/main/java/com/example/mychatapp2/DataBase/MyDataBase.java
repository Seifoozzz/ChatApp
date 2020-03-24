package com.example.mychatapp2.DataBase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyDataBase  {
    public static String UserRefrence="Users";
    public static String RoomRefrence="Rooms";
    public static String MessageRefrence="Messages";


    private static FirebaseFirestore myDatabase;

    public static FirebaseFirestore getInstance(){
        if (myDatabase == null){
            myDatabase = FirebaseFirestore.getInstance();
        }
        return myDatabase;
    }


   public static CollectionReference GetUserRefrence(){

       return getInstance().collection(UserRefrence);


   }
   public static CollectionReference GetRoomRefrence(){
        return getInstance().collection(RoomRefrence);
   }

public static CollectionReference GetMessageRefrence(){

        return getInstance().collection(MessageRefrence);
}
}
