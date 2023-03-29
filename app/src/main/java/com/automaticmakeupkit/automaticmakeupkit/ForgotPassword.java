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

public class ForgotPassword extends AppCompatActivity {

    Button goBackLogo;
    ImageView imgForgotPasswordLogo;
    TextView txtForgotPasswordWelcome, txtForgotPasswordSlogan;
    Button resetPassword;
    TextInputLayout inpEmail;
    CircularProgressIndicator circularProgressIndicator;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forgot_password);

        goBackLogo = findViewById(R.id.go_back_icon);
        imgForgotPasswordLogo = findViewById(R.id.forgot_password_icon);
        txtForgotPasswordWelcome = findViewById(R.id.forgot_password_welcome);
        txtForgotPasswordSlogan = findViewById(R.id.forgot_password_text);
        inpEmail = findViewById(R.id.email);
        resetPassword = findViewById(R.id.reset_password);
        circularProgressIndicator = findViewById(R.id.progress_circular);

        mAuth = FirebaseAuth.getInstance();
    }

    public void callSignIn(View view) {

        Intent intent = new Intent(ForgotPassword.this, Login.class);

        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(imgForgotPasswordLogo, "logo_image");
        pairs[1] = new Pair<View, String>(txtForgotPasswordWelcome, "logo_text");
        pairs[2] = new Pair<View, String>(txtForgotPasswordSlogan, "logo_slogan");
        pairs[3] = new Pair<View, String>(inpEmail, "email_tran");
        pairs[4] = new Pair<View, String>(resetPassword, "signin_tran");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(ForgotPassword.this, pairs);
        startActivity(intent, activityOptions.toBundle());

    }

    private Boolean validateEmailAddress(String emailAddress) {
        String val = emailAddress;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            inpEmail.setError("Required");
            inpEmail.requestFocus();
            return false;
        } else if (!val.matches(emailPattern)) {
            inpEmail.setError("Invalid Email Address");
            inpEmail.requestFocus();
            return false;
        } else {
            inpEmail.setError(null);
            inpEmail.setErrorEnabled(false);
            return true;
        }

    }

    public void resetPassword(View view) {

        String emailAddress = inpEmail.getEditText().getText().toString().trim();

        if (!validateEmailAddress(emailAddress)) {
            return;
        }

        circularProgressIndicator.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Please check your email for reset password instructions", Toast.LENGTH_LONG).show();
                    circularProgressIndicator.setVisibility(View.GONE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(ForgotPassword.this, Login.class);
                            startActivity(intent);
                        }
                    }, 2500);

                } else {
                    Toast.makeText(ForgotPassword.this, "You are not registered, please sign up.", Toast.LENGTH_LONG).show();
                    circularProgressIndicator.setVisibility(View.GONE);
                }

            }
        });

    }
}