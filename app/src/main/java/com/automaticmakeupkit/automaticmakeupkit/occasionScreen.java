package com.automaticmakeupkit.automaticmakeupkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class occasionScreen extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1;

    private String deviceAddress = "98:D3:31:F5:FA:37";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occasion_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(this,
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

        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            System.out.println("Bluetooth connecting");
            socket.connect();

            if (socket.isConnected()) {

                Toast.makeText(this, "Bluetooth Connected Succesfully", Toast.LENGTH_SHORT).show();

            }
            // Connection successful, manage input/output streams
        } catch (IOException e) {
            // Connection failed

            Toast.makeText(this, "Bluetooth connecting Failed", Toast.LENGTH_SHORT).show();
            System.out.println("Bluetooth connecting Failed");
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public void Button1(View view) throws IOException {
        if (socket != null) {
            OutputStream outputStream = socket.getOutputStream();
            String command = "1"; // Replace with the command you want to send
            outputStream.write(command.getBytes());
        }
    }

    public void Button2(View view) throws IOException {
        if (socket != null) {
            OutputStream outputStream = socket.getOutputStream();
            String command = "2"; // Replace with the command you want to send
            outputStream.write(command.getBytes());
        }
    }

    public void Button3(View view) throws IOException {
        if (socket != null) {
            OutputStream outputStream = socket.getOutputStream();
            String command = "3"; // Replace with the command you want to send
            outputStream.write(command.getBytes());
        }
    }

    public void Button4(View view) throws IOException {
        if (socket != null) {
            OutputStream outputStream = socket.getOutputStream();
            String command = "4"; // Replace with the command you want to send
            outputStream.write(command.getBytes());
        }
    }
}