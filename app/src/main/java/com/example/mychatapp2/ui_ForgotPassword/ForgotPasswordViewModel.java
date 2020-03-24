package com.example.mychatapp2.ui_ForgotPassword;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends ViewModel {
    public ObservableField<String> Email = new ObservableField<>();
    public MutableLiveData<String> emailError = new MutableLiveData<>("");
    public MutableLiveData<String> Success = new MutableLiveData<>("");



    FirebaseAuth mAuth = FirebaseAuth.getInstance();



    public void onSendPass(){
        if(validateForm()){
            Success.setValue(null);
            mAuth.sendPasswordResetEmail(Email.get()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Success.setValue("Success");
                    }
                    else
                    {
                        Success.setValue(task.getException().getLocalizedMessage());
                    }

                }
            });

        }


    }


    private boolean validateForm() {
        boolean isValid = true;

        if (Email.get().trim().isEmpty()) {
            emailError.setValue("required");
            isValid = false;
        } else if (!isValidEmail()) {
            //todo : validate email
            isValid = false;
            emailError.setValue("please enter a valid email");
        } else {
            emailError.setValue(null);
        }
        return isValid;
    }

    private boolean isValidEmail() {
        boolean isvalid ;

        if(Patterns.EMAIL_ADDRESS.matcher(Email.get()).matches()){
            isvalid = true;
        }
        else {
            isvalid = false ;
        }

        return isvalid ;

    }

}
