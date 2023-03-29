package com.automaticmakeupkit.automaticmakeupkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ImageView imgPlainLogo;
    TextView txtSignUpWelcome, txtSignUpSlogan;
    Button SignUp, callSignIn;
    TextInputLayout inpFullName, inpEmail, inpPhone, inpPassword;
    CircularProgressIndicator circularProgressIndicator;

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        imgPlainLogo = findViewById(R.id.plain_logo);
        txtSignUpWelcome = findViewById(R.id.signup_welcome);
        txtSignUpSlogan = findViewById(R.id.signup_text);
        inpFullName = findViewById(R.id.name);
        inpEmail = findViewById(R.id.email);
        inpPhone = findViewById(R.id.phone);
        inpPassword = findViewById(R.id.password);
        SignUp = findViewById(R.id.signup);
        callSignIn = findViewById(R.id.signin_existing_user);
        circularProgressIndicator = findViewById(R.id.progress_circular);

        mAuth = FirebaseAuth.getInstance();
    }

    public void callSignIn(View view) {

        Intent intent = new Intent(SignUp.this, Login.class);

        Pair[] pairs = new Pair[9];
        pairs[0] = new Pair<View,String>(imgPlainLogo, "logo_image");
        pairs[1] = new Pair<View,String>(txtSignUpWelcome, "logo_text");
        pairs[2] = new Pair<View,String>(txtSignUpSlogan, "logo_slogan");
        pairs[3] = new Pair<View,String>(inpFullName, "email_tran");
        pairs[4] = new Pair<View,String>(inpEmail, "email_tran");
        pairs[5] = new Pair<View,String>(inpPhone, "password_tran");
        pairs[6] = new Pair<View,String>(inpPassword, "password_tran");
        pairs[7] = new Pair<View,String>(SignUp, "signin_tran");
        pairs[8] = new Pair<View,String>(callSignIn, "signin_signup_tran");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
        startActivity(intent, activityOptions.toBundle());

    }


    private Boolean validateFullName(String fullName) {
        String val = fullName;

        if(val.isEmpty()) {
            inpFullName.setError("Required");
            inpFullName.requestFocus();
            return false;
        }

        else {
            inpFullName.setError(null);
            inpFullName.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmailAddress(String emailAddress) {
        String val = emailAddress;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()) {
            inpEmail.setError("Required");
            inpEmail.requestFocus();
            return false;
        }

        else if(!val.matches(emailPattern)) {
            inpEmail.setError("Invalid Email Address");
            inpEmail.requestFocus();
            return false;
        }

        else {
            inpEmail.setError(null);
            inpEmail.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePhoneNumber(String phoneNumber) {
        String val = phoneNumber;

        if(val.isEmpty()) {
            inpPhone.setError("Required");
            inpPhone.requestFocus();
            return false;
        }

        else {
            inpPhone.setError(null);
            inpPhone.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword(String Password) {
        String val = Password;

        String passwordPattern = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if(val.isEmpty()) {
            inpPassword.setError("Required");
            inpPassword.requestFocus();
            return false;
        }

        else if(!val.matches(passwordPattern)) {
            inpPassword.setError("Weak Password");
            return false;
        }

        else {
            inpPassword.setError(null);
            inpPassword.setErrorEnabled(false);
            return true;
        }

    }

    public void registerUser(View view) {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        String fullName = inpFullName.getEditText().getText().toString().trim();
        String emailAddress = inpEmail.getEditText().getText().toString().trim();
        String phoneNumber = inpPhone.getEditText().getText().toString().trim();
        String Password = inpPassword.getEditText().getText().toString().trim();


        if(!validateFullName(fullName) | !validateEmailAddress(emailAddress) | !validatePhoneNumber(phoneNumber) | !validatePassword(Password)) {
            return;
        }

        circularProgressIndicator.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailAddress, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println(task);
                        if(task.isSuccessful()){
                            UserHelper user = new UserHelper(fullName, emailAddress, phoneNumber);
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "You have registered successfully.", Toast.LENGTH_LONG).show();
                                        circularProgressIndicator.setVisibility(View.GONE);

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(SignUp.this, Login.class);
                                                startActivity(intent);
                                            }
                                        },2500);

                                    }
                                    else {
                                        Toast.makeText(SignUp.this, "User already exists, please log in.", Toast.LENGTH_LONG).show();
                                        circularProgressIndicator.setVisibility(View.GONE);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUp.this, "User already exists, please log in.", Toast.LENGTH_LONG).show();
                            circularProgressIndicator.setVisibility(View.GONE);
                        }
                    }
                });



    }
}