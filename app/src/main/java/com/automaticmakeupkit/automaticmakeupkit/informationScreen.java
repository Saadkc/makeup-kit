package com.automaticmakeupkit.automaticmakeupkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class informationScreen extends AppCompatActivity {


    Button SignIn, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_screen);

        SignIn = findViewById(R.id.button);
        login = findViewById(R.id.button2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Create a new Intent to navigate to the second activity
//                Intent intent = new Intent(informationScreen.this, SignUp.class);
//
//                // Start the second activity using the Intent
//                startActivity(intent);
//            }
//        });
    }

    public void LoginScreen (View view){
        Intent intent = new Intent(informationScreen.this, Login.class);
        startActivity(intent);
    }

    public void SignupScreen (View view){
        Intent intent = new Intent(informationScreen.this, SignUp.class);
        startActivity(intent);
    }
}