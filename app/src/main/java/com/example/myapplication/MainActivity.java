package com.example.myapplication;

import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import com.example.myapplication.student.studentInfo;
import com.example.myapplication.student.student_placeholder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //This variable should be saved to database.
    private boolean setup = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //here we add a check to see if first time setup is done.
        if(setup != true) {
            //Calls alert popup!
            firstTimeSetup();
        }
        //DEMO MODE UI stuff,
        EditText DemoMock = findViewById(R.id.DemomockUserInput);
        DemoMock.setVisibility(View.INVISIBLE);
        Button mockEnter = findViewById(R.id.SubmitMockUser);
        mockEnter.setVisibility(View.INVISIBLE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Load images example Use this for opening image.
        //String URLY = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
        //ImageView imgView =(ImageView)findViewById(R.id.imageView2);
        //imgView.setImageBitmap(getBitmapFromURL(URLY));

    }

    public void firstTimeSetup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        // layout activity_first_time_setup.xml
        builder.setView(inflater.inflate(R.layout.activity_first_time_setup, null));
        AlertDialog FTSetup = builder.create();
        FTSetup.setTitle("First Time Setup");
        //Note this alert can be dismissed if someone clicks out. Have to find a way to prevent that
        FTSetup.show();
        //Collects name.
        Button submitted = (Button) FTSetup.findViewById(R.id.SubmitFirstName);
        submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v8) {
                final EditText name = (EditText) FTSetup.findViewById(R.id.personName);
                String userName = name.getText().toString();
                Log.d("Name that was typed in ", userName);
                FTSetup.dismiss();

            }
        });

        //Add method to save name to csv file on db ie. name,,,
        //Next add step to input URL
        //Save URL to database

        //Next step add classes
        //Call add classes interface
        //Once First time setup is done set setup boolean to true.

    }



    public void StartStopButton(View view){
        //Add check if setup complete

        Button startStop = findViewById(R.id.StartStopBttn);

        String current = startStop.getText().toString();
        Log.d("CurrentState", current);
        if(current.equals("START")) {
            startStop.setText("STOP");
            //Red color code
            startStop.setBackgroundColor(0xFFFF0000);

        }
        if(current.equals("STOP")){
            startStop.setText("START");
            //Green color code
            startStop.setBackgroundColor(0Xff99cc00);
        }
    }


    public void switchMocktoList(View view) {
        Button mockSwitch = findViewById(R.id.nearByMockScreen);
        String current = mockSwitch.getText().toString();
        if(current.equals("MOCK")) {
            //DEMO MOCK USER MODE

            //Sets the recycler view invisible and input text visible for the mock user
            mockSwitch.setText("LIST");
            RecyclerView studentList = findViewById(R.id.recyclerView2);
            studentList.setVisibility(View.INVISIBLE);
            EditText DemoMock = findViewById(R.id.DemomockUserInput);
            DemoMock.setVisibility(View.VISIBLE);
            Button mockEnter = findViewById(R.id.SubmitMockUser);
            mockEnter.setVisibility(View.VISIBLE);
            mockEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    EditText newUser = (EditText) findViewById(R.id.DemomockUserInput);
                    String MockUserInfo = newUser.getText().toString();
                    Log.d("Mock User info: ", MockUserInfo);
                    //Store the information to the database here.

                    DemoMock.setText("");
                }
            });
            //Blue color code
            mockSwitch.setBackgroundColor(0xff0099cc);
        }
        if(current.equals("LIST")){
            //LIST STUDENT MODE
            //For demo mode maybe when user clicks list it should update mocked users
            mockSwitch.setText("MOCK");
            //Sets the recycler view visible and input text invisible for the mock user
            EditText DemoMock = findViewById(R.id.DemomockUserInput);
            DemoMock.setVisibility(View.INVISIBLE);
            Button mockEnter = findViewById(R.id.SubmitMockUser);
            mockEnter.setVisibility(View.INVISIBLE);
            RecyclerView studentList = findViewById(R.id.recyclerView2);
            studentList.setVisibility(View.VISIBLE);
            //Green color code
            mockSwitch.setBackgroundColor(0Xff99cc00);

        }
    }



}