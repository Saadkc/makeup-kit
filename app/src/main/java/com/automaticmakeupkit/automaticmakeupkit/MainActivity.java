package com.automaticmakeupkit.automaticmakeupkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityOptions;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private static int SPLASH_SCREEN = 3500;

    ImageView imgLogo;
    LottieAnimationView lottieAnimationView;
    TextView txtSlogan;

    Animation topAnim, bottomAnim;

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;

    private String deviceAddress = "D8:B0:53:86:E8:E3";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lottieAnimationView = findViewById(R.id.lottie);
        imgLogo = findViewById(R.id.logo);
        txtSlogan = findViewById(R.id.slogan);


        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        lottieAnimationView.setAnimation(topAnim);
        imgLogo.setAnimation(bottomAnim);
        txtSlogan.setAnimation(bottomAnim);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, informationScreen.class);
//
//                Pair[] pairs = new Pair[3];
//                pairs[0] = new Pair<View, String>(lottieAnimationView, "logo_image");
//                pairs[1] = new Pair<View, String>(imgLogo, "logo_text");
//                pairs[2] = new Pair<View, String>(txtSlogan, "logo_slogan");
//
//                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
//                startActivity(intent, activityOptions.toBundle());
//
//            }
//        }, SPLASH_SCREEN);


        Button connectButton = findViewById(R.id.connect_button);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a reference to the Bluetooth adapter
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                // Check if Bluetooth is supported on the device
                if (bluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(), "Bluetooth not supported on this device", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check if Bluetooth is enabled
                if (!bluetoothAdapter.isEnabled()) {

                    // Prompt the user to enable Bluetooth
                    Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        // Permission is not granted, request it
//                        ActivityCompat.requestPermissions(this,
//                                new String[]{Manifest.permission.BLUETOOTH},
//                                REQUEST_ENABLE_BLUETOOTH);
//                    } else {
//                        startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
//                        return;
//                    }

                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted, request it
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.BLUETOOTH},
                                REQUEST_ENABLE_BLUETOOTH);
                    } else {
                        // Permission is already granted, start the Bluetooth request
                        startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH);
                        return;
                    }

                }

                // Get a reference to the remote Bluetooth device
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);

                // Create a socket and connect to the remote device
                BluetoothSocket socket;
                try {
                    socket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    System.out.println("Bluetooth connecting");
                    socket.connect();

                    if(socket.isConnected()){
                        System.out.println("Bluetooth Connected");
                    }
                    // Connection successful, manage input/output streams
                } catch (IOException e) {
                    // Connection failed
                    System.out.println("Bluetooth connecting Failed");
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        });
    }
}