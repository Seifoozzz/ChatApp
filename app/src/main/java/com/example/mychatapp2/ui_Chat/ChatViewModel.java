package com.example.mychatapp2.ui_Chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Daos.MessageDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.Message;
import com.example.mychatapp2.DataBase.Models.Room;
import com.example.mychatapp2.ui_Main.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatViewModel extends ViewModel {
    public ObservableField<String> messageContent = new ObservableField<>("");
    public MutableLiveData Successmess =new MutableLiveData("");
    public MutableLiveData<List<Message>> messageList=new MutableLiveData<>(null);
    public MutableLiveData<String> RoomName = new MutableLiveData<>("");

public void onSendButtonClick(){
sendMessage();
}
    MainViewModel viewModel;
    private void sendMessage() {
        viewModel =new MainViewModel();
        final Message message=new Message();
        message.setContent(messageContent.get());
        message.setRoomId(viewModel.roomput.getValue().getRoomId());
        message.setRoomName(viewModel.roomput.getValue().getRoomName());
        message.setSenderName(Datautils.CurrentUser.getName());
        message.setSenderId(Datautils.CurrentUser.getId());
        message.setImage(Datautils.CurrentUser.getImage());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String Time = simpleDateFormat.format(calendar.getTime());
        message.setTime(Time);
        messageContent.set(null);

        MessageDao.addMessage(message, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    getAllMessages();
                }
                else
                    Log.e("Message",task.getException().getLocalizedMessage());
            }
        });
    }
List<Message> messages;
    public void getAllMessages() {
        MessageDao.getRoomMessage(viewModel.roomput.getValue().getRoomId(),new EventListener<QuerySnapshot>() {
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                if (e==null) {
                    messages = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Message message = document.toObject(Message.class);
                        messages.add(message);
                    }
                    messageList.setValue(messages);
                    Successmess.setValue("Success");
                } else {
                }


            }
        });




            }
    public List<Message> messageList2 = new ArrayList<>();
    private void subscribeMessages() {
        for (Message me : messages) {
            messageList2.add(me);
        }
        messageList.setValue(messageList2);
    }

            }