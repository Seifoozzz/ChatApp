package com.example.mychatapp2.ui_Register;

import android.net.Uri;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Daos.UserDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.User;
import com.example.mychatapp2.DataBase.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterViewModel extends ViewModel {

    public ObservableField<String> UserNAme = new ObservableField<>("");
    public ObservableField<String> Email = new ObservableField<>("");
    public ObservableField<String> Password = new ObservableField<>("");
    public ObservableField<String> ConfirmPassword = new ObservableField<>("");
    public MutableLiveData<Boolean> onimageClick = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> onRegisterClick = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> progressbar = new MutableLiveData<>(false);
    public MutableLiveData<String> userNameError = new MutableLiveData<>();
    public MutableLiveData<String> emailError = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public MutableLiveData<String> passwordConfirmError = new MutableLiveData<>();


    public static String Name;
    public static String email;
    public static String password;
    public Uri imageUri;
    public Uri DownloadUrl;
    StorageReference mStorage;
    StorageReference FileRef;
    FirebaseAuth mAuth;




    public void OnRegisterClick() {
        if (validateForm()) {
            Name = UserNAme.get();
            email = Email.get();
            password = Password.get();
            onRegisterClick.setValue(true);
        }
    }

    public void OnImageClick(){
    onimageClick.setValue(true);
    }



 public void OnNextClick(){
        progressbar.setValue(true);
        StoreImage();

 }


    private void StoreImage() {
        MyDataBase.GetUserRefrence().document();
        mStorage= FirebaseStorage.getInstance().getReference().child("UserImage");
        FileRef=mStorage.child(System.currentTimeMillis()+".jpeg");
        FileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                FileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DownloadUrl = uri ;
                        registerUser();


                    }
                });

            }
        });
    }

    public void registerUser() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            AddUserToDataBase();
                        }
                    }
                });
    }

  public void AddUserToDataBase(){
final User user = new User();
user.setName(Name);
user.setEmail(email);
user.setId(mAuth.getCurrentUser().getUid());
user.setImage(DownloadUrl.toString());


UserDao.AddUser(user, new OnCompleteListener() {
    @Override
    public void onComplete(@NonNull Task task) {
        if(task.isSuccessful()){

            progressbar.setValue(false);


        }
    }
});

  }
    private boolean validateForm() {
        boolean isValid = true;
        if (UserNAme.get().trim().isEmpty()){
            userNameError.setValue("required");
        } else {
            userNameError.setValue(null);
        }
        if (Email.get().trim().isEmpty()){
            emailError.setValue("required");
            isValid = false;
        }else if (!isValidEmail()) {
            isValid= false;
            emailError.setValue("Please enter a valid email");
        }else {
            emailError.setValue(null);
        }
        if (Password.get().trim().isEmpty()){
            passwordError.setValue("required");
            isValid = false;
        }
        else if (Password.get().length()<6) {
            passwordError.setValue("password should more than 6 chars");
            isValid = false;
        }else {
            passwordError.setValue(null);
        }
        if (ConfirmPassword.get().trim().isEmpty()){
            passwordConfirmError.setValue("required");
            isValid = false;
        }else {
            passwordConfirmError.setValue(null);
        }
        if (!ConfirmPassword.get().equals(Password.get())){
            passwordError.setValue("Passwords doesn't match");
            passwordConfirmError.setValue("Passwords doesn't match");
            isValid = false;
        }else {
            passwordError.setValue(null);
            passwordConfirmError.setValue(null);
        }
        return isValid;
    }

    private boolean isValidEmail() {
        boolean isValid ;
        if(Patterns.EMAIL_ADDRESS.matcher(Email.get()).matches()){
            isValid = true;
        }
        else {
            isValid = false ;
        }
        return isValid ;
    }

}
