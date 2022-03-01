package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class BTPermission {
    private final int BLUETOOTH_SCAN_ADVERTISE_CONNECT = 0;

    Context context;

    public BTPermission(Context context) {
        this.context = context;
    }

    public boolean BTPermissionIsGranted() {
        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    public void promptPermissionRequiredMessage() {
        Toast.makeText(this.context, "Required Bluetooth permission to proceed", Toast.LENGTH_SHORT).show();
    }

    public void requestBTPermission() {
        ActivityCompat.requestPermissions(
                (Activity) this.context,
                new String[]{
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_ADVERTISE,
                        Manifest.permission.BLUETOOTH_CONNECT
                },
                BLUETOOTH_SCAN_ADVERTISE_CONNECT
        );
    }
}
