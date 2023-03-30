package com.automaticmakeupkit.automaticmakeupkit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3500;

    ImageView imgLogo;
    LottieAnimationView lottieAnimationView;
    TextView txtSlogan;

    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this); // initialize Firebase

        if (FirebaseApp.getInstance() != null) {
            Log.d("MainActivity", "Firebase initialized successfully");
        } else {
            Log.d("MainActivity", "Firebase initialization failed");
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lottieAnimationView = findViewById(R.id.lottie);
        imgLogo = findViewById(R.id.logo);
        txtSlogan = findViewById(R.id.slogan);


        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        lottieAnimationView.setAnimation(topAnim);
        imgLogo.setAnimation(bottomAnim);
        txtSlogan.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, informationScreen.class);

                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View,String>(lottieAnimationView, "logo_image");
                pairs[1] = new Pair<View,String>(imgLogo, "logo_text");
                pairs[2] = new Pair<View,String>(txtSlogan, "logo_slogan");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, activityOptions.toBundle());

            }
        },SPLASH_SCREEN);
    }
}