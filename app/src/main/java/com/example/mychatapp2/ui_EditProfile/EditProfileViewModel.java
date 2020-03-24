package com.example.mychatapp2.ui_EditProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Datautils;
import com.google.firebase.auth.FirebaseAuth;

public class EditProfileViewModel extends ViewModel {
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userEmail = new MutableLiveData<>();
    public MutableLiveData<String> UserImage = new MutableLiveData<>();
    public MutableLiveData<Boolean> ChangePass = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> editClick = new MutableLiveData<>(false);



    public void onStart(){
        userName.setValue(Datautils.CurrentUser.getName());
        userEmail.setValue(Datautils.CurrentUser.getEmail());

    }


    public void onEditClick(){


    }
    public void onChangePasswordClick(){
        ChangePass.setValue(true);


    }
}
