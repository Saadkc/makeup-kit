package com.automaticmakeupkit.automaticmakeupkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void NavigateScreen (View view){
        Intent intent = new Intent(Dashboard.this, occasionScreen.class);
        startActivity(intent);
    }
}