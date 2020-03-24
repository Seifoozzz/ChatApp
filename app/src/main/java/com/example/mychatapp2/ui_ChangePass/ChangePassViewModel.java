package com.example.mychatapp2.ui_ChangePass;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Daos.MessageDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChangePassViewModel extends ViewModel {
    public ObservableField<String> newPassword = new ObservableField<>();

    public MutableLiveData<Boolean> isValid = new MutableLiveData<>(false);

FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public void onReset(){
        if (!newPassword.get().trim().isEmpty()){
            mAuth.getCurrentUser().updatePassword(newPassword.get()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        isValid.setValue(true);

                    }

                    else
                    {
                        Log.e("ok",task.getException().getLocalizedMessage());
                    }
                }
            });
        }

    }
    List<Message> messageList = new ArrayList<>();
    Message message ;
    public void ali(){

        MessageDao.getMessages(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {


                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){

                    message = dc.getDocument().toObject(Message.class);
                    messageList.add(message);
                }
                String x =    message.getContent();
                String y = message.getId() ;

            }
        });
    }
}
