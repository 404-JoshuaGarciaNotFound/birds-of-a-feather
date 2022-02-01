package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void StartStopButton(View view){
        //Add check if setup complete


        Button startStop = findViewById(R.id.StartStopBttn);
        String current = startStop.getText().toString();
        //Log.d("Abc", "called");
        //System.out.println("Ran!");
        System.out.println(current);
        if(current.equals("START")) {
            startStop.setText("STOP");
            startStop.setBackgroundColor(0xFFFF0000);

        }
        if(current.equals("STOP")){
            startStop.setText("START");
            startStop.setBackgroundColor(0Xff99cc00);
        }
    }


}