package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //private boolean setup = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //here we add a check to see if first time setup is done.
        //if(setup != true) {
        //Calls alert popup!
        firstTimeSetup();
       // }
    }
    public void firstTimeSetup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        // layout activity_first_time_setup.xml
        builder.setView(inflater.inflate(R.layout.activity_first_time_setup, null));
        AlertDialog FTSetup = builder.create();
        FTSetup.setTitle("First Time Setup");

        // TODO
        //Note this alert can be dismissed if someone clicks out. Have to find a way to prevent it
        FTSetup.show();
    }

    public void StartStopButton(View view) {
        //Add check if setup complete
        Button startStop = findViewById(R.id.StartStopBttn);
        String current = startStop.getText().toString();
        Log.d("CurrentState", current);
        if(current.equals("START")) {
            startStop.setText("STOP");
            //Red color code
            startStop.setBackgroundColor(0xFFFF0000);

        }
        if(current.equals("STOP")) {
            startStop.setText("START");
            //Green color code
            startStop.setBackgroundColor(0Xff99cc00);
        }
    }
}