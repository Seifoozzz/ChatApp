package com.example.mychatapp2.ui_AddRoom;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Daos.RoomDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.Room;
import com.example.mychatapp2.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddRoomViewModel extends ViewModel {
    public ObservableField<String> RoomName = new ObservableField<>("");
    public ObservableField<String> RoomDesc = new ObservableField<>("");
    public MutableLiveData<String> success = new MutableLiveData<>();
    public MutableLiveData<Boolean> progress = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> ImageClick = new MutableLiveData<>();
    public Uri ImageUri;
    public Uri downloadUrl;
    StorageReference mStorage;
    StorageReference FileRef;
    FirebaseAuth mAuth ;
    FirebaseUser fbUser ;
    public Room room = new Room();
    public void onAddRoomClick(){

        storeImage();

        if(downloadUrl!=null) {
            progress.setValue(true);
            mAuth=FirebaseAuth.getInstance();
            fbUser=mAuth.getCurrentUser();
            room.setRoomName(RoomName.get());
            room.setRoomDesc(RoomDesc.get());
            room.setImage(downloadUrl.toString());
            room.setAdminUser(mAuth.getCurrentUser().getUid());
            RoomDao.AddRoom(room, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        success.setValue("true");
                        progress.setValue(false);
                    }
                }
            });

        }
    }

    public void OnImageClick(){
        ImageClick.setValue(true);
    }

 public void storeImage(){
     MyDataBase.GetRoomRefrence().document();
     mStorage= FirebaseStorage.getInstance().getReference().child("RoomImage");
     FileRef = mStorage.child(System.currentTimeMillis()+"jpeg");
    FileRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            FileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    downloadUrl=uri;

                }
            });
        }
    });

 }
}
