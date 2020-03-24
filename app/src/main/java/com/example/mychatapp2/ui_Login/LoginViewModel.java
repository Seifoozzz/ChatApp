package com.example.mychatapp2.ui_Login;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mychatapp2.DataBase.Daos.UserDao;
import com.example.mychatapp2.DataBase.Datautils;
import com.example.mychatapp2.DataBase.Models.User;
import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginViewModel extends ViewModel {

    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> password = new ObservableField<>("");
    public MutableLiveData<String> emailError = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public MutableLiveData<String> intent = new MutableLiveData<>("");
    public MutableLiveData<String> Success = new MutableLiveData<>("");


    public void onLoginClick(){
        if(validateForm()){
            intent.setValue("X");
            LoginUser();
        }
    }

    FirebaseAuth mAuth;
    private void LoginUser() {
        mAuth = FirebaseAuth.getInstance();
        final User user = new User();
        mAuth.signInWithEmailAndPassword(email.get(),password.get())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            String h = mAuth.getUid();

                            UserDao.getCurrentUser(h, new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()){
                                        User user2  = task.getResult().toObject(User.class);
                                        user.setImage(user2.getImage());
                                        user.setName(user2.getName());
                                        user.setId(user2.getId());
                                        user.setEmail(user2.getEmail());
                                        String x = user.getEmail();
                                        Datautils.CurrentUser = user ;
                                        intent.setValue("Success");

                                    }
                                }
                            });


                        }else {
                            intent.setValue(task.getException().getLocalizedMessage().toString());
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean isValid = true;
        if (email.get().trim().isEmpty()){
            emailError.setValue("required");
            isValid = false;
        }else if (!isValidEmail()) {
            // todo:validate Email
            isValid= false;
            emailError.setValue("Please enter a valid email");
        }else {
            emailError.setValue(null);
        }
        if (password.get().trim().isEmpty()){
            passwordError.setValue("required");
            isValid = false;
        }
        else if (password.get().length()<6) {
            passwordError.setValue("password should more than 6 chars");
            isValid = false;
        }else {
            passwordError.setValue(null);
        }
        return isValid;
    }

    private boolean isValidEmail() {
        boolean isvalid ;
        if(Patterns.EMAIL_ADDRESS.matcher(email.get()).matches()){
            isvalid = true;
        } else {
            isvalid = false ;
        }
        return isvalid ;
    }

    public void onForgotPasswordClick(){
        Success.setValue("Forgot");
    }

    public void onSignUpClick(){
        Success.setValue("signUp");
    }

}
