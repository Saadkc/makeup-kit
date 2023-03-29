package com.automaticmakeupkit.automaticmakeupkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class information_screen extends AppCompatActivity {

    Button button = findViewById(R.id.button);
    Button button2 = findViewById(R.id.button2);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_screen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new Intent to navigate to the second activity
                Intent intent = new Intent(information_screen.this, SignUp.class);

                // Start the second activity using the Intent
                startActivity(intent);
            }
        });
    }
}