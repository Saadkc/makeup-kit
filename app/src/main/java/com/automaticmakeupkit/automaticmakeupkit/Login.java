package com.automaticmakeupkit.automaticmakeupkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
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

public class Login extends AppCompatActivity {

    ImageView imgPlainLogo;
    TextView txtLoginWelcome, txtLoginSlogan;
    Button SignIn, callSignUp, forgotPassword;
    TextInputLayout inpEmail, inpPassword;
    CircularProgressIndicator circularProgressIndicator;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        imgPlainLogo = findViewById(R.id.plain_logo);
        txtLoginWelcome = findViewById(R.id.login_welcome);
        txtLoginSlogan = findViewById(R.id.login_text);
        inpEmail = findViewById(R.id.email);
        inpPassword = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgot_password);
        SignIn = findViewById(R.id.signin);
        callSignUp = findViewById(R.id.signup_new_user);
        circularProgressIndicator = findViewById(R.id.progress_circular);

        mAuth = FirebaseAuth.getInstance();
    }

    public void callSignUp(View view) {

        Intent intent = new Intent(Login.this, SignUp.class);

        Pair[] pairs = new Pair[8];
        pairs[0] = new Pair<View,String>(imgPlainLogo, "logo_image");
        pairs[1] = new Pair<View,String>(txtLoginWelcome, "logo_text");
        pairs[2] = new Pair<View,String>(txtLoginSlogan, "logo_slogan");
        pairs[3] = new Pair<View,String>(inpEmail, "email_tran");
        pairs[4] = new Pair<View,String>(inpPassword, "password_tran");
        pairs[5] = new Pair<View,String>(forgotPassword, "signin_tran");
        pairs[6] = new Pair<View,String>(SignIn, "signin_tran");
        pairs[7] = new Pair<View,String>(callSignUp, "signin_signup_tran");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, activityOptions.toBundle());

    }

    public void callForgotPassword(View view) {

        Intent intent = new Intent(Login.this, ForgotPassword.class);

        Pair[] pairs = new Pair[8];
        pairs[0] = new Pair<View,String>(imgPlainLogo, "lock_tran");
        pairs[1] = new Pair<View,String>(txtLoginWelcome, "logo_text");
        pairs[2] = new Pair<View,String>(txtLoginSlogan, "logo_slogan");
        pairs[3] = new Pair<View,String>(inpEmail, "email_tran");
        pairs[4] = new Pair<View,String>(inpPassword, "email_tran");
        pairs[5] = new Pair<View,String>(forgotPassword, "reset_password_tran");
        pairs[6] = new Pair<View,String>(SignIn, "reset_password_tran");
        pairs[7] = new Pair<View,String>(callSignUp, "reset_password_tran");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
        startActivity(intent, activityOptions.toBundle());
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

    private Boolean validatePassword(String Password) {
        String val = Password;

        if(val.isEmpty()) {
            inpPassword.setError("Required");
            inpPassword.requestFocus();
            return false;
        }

        else {
            inpPassword.setError(null);
            inpPassword.setErrorEnabled(false);
            return true;
        }

    }

    public void loginUser(View view) {

        String emailAddress = inpEmail.getEditText().getText().toString().trim();
        String Password = inpPassword.getEditText().getText().toString().trim();

        if(!validateEmailAddress(emailAddress) | !validatePassword(Password)) {
            return;
        }

        circularProgressIndicator.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailAddress, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "You have signed in successfully.", Toast.LENGTH_LONG).show();
                            circularProgressIndicator.setVisibility(View.GONE);
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Failed to log in, please try again.", Toast.LENGTH_LONG).show();
                            circularProgressIndicator.setVisibility(View.GONE);
                        }
                    }
                });

    }
}