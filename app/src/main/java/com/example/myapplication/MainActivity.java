package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.student.database.AppDatabase;
import com.example.myapplication.student.database.Student;
import com.example.myapplication.student.database.StudentDao;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    //This variable should be saved to database.
    private boolean setup = true;

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

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        //Load images example Use this for opening image.
        //String URLY = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
        //ImageView imgView =(ImageView)findViewById(R.id.imageView);
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
                //Require users to type name no blanks name should be saved to database
                if(!userName.equals("")){
                    FTSetup.dismiss();
                    //Save name to database

                }
                Log.d("Name that was typed in ", userName);
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
        AppDatabase db = AppDatabase.singleton(this);
        RecyclerView studentsRecyclerView = findViewById(R.id.list_of_students);
        RecyclerView.LayoutManager studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);

        Button mockSwitch = findViewById(R.id.nearByMockScreen);
        String current = mockSwitch.getText().toString();
        if(current.equals("MOCK")) {
            //DEMO MOCK USER MODE

            //Sets the recycler view invisible and input text visible for the mock user
            mockSwitch.setText("LIST");
            RecyclerView studentList = findViewById(R.id.list_of_students);
            studentList.setVisibility(View.INVISIBLE);
            EditText DemoMock = findViewById(R.id.DemomockUserInput);
            DemoMock.setVisibility(View.VISIBLE);
            Button mockEnter = findViewById(R.id.SubmitMockUser);
            mockEnter.setVisibility(View.VISIBLE);
            mockEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v2) {
                    EditText newUser = (EditText) findViewById(R.id.DemomockUserInput);
                    String mockUserInfo = newUser.getText().toString();
                    Log.d("Mock User info: ", mockUserInfo);

                    // FIXME: missing type checking. try regex
                    // parse input for create new student instance
                    // input is in form: {name,,,}\n{url,,,}\n{course1}\n{course2}\n...
                    String[] parsedUserInfo = mockUserInfo.split("\n");
                    int idOfNewStudent = db.studentDao().count() + 1;
                    String nameOfNewStudent = parsedUserInfo[0]
                            .substring(0,
                                    parsedUserInfo[0].length() - 3); // drop ,,,
                    String headShotURLOfNewStudent = parsedUserInfo[1]
                            .substring(0,
                                    parsedUserInfo[1].length() - 3); // drop ,,,
                    StringBuilder coursesOfNewStudent = new StringBuilder();
                    for (int i = 1; i < parsedUserInfo.length; i++) {
                        coursesOfNewStudent.append(parsedUserInfo[i]);
                        if (i != parsedUserInfo.length - 1) {
                            coursesOfNewStudent.append(" ");
                        }
                    }
                    Student toAddStudent = new Student(
                            idOfNewStudent, nameOfNewStudent, headShotURLOfNewStudent,
                            coursesOfNewStudent.toString()
                    );

                    StudentDao studentDao = db.studentDao();
                    studentDao.insertStudent(toAddStudent);
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
            RecyclerView studentList = findViewById(R.id.list_of_students);
            studentList.setVisibility(View.VISIBLE);
            //Green color code
            mockSwitch.setBackgroundColor(0Xff99cc00);

            // get list of students from database, set recycler view according to the list
            StudentDao studentDao = db.studentDao();
            List<Student> listOfStudent = studentDao.getAll();
            RecyclerView listOfStudentsView = findViewById(R.id.list_of_students);
            StudentAdapter listOfStudentsViewAdapter = new StudentAdapter(listOfStudent);
            listOfStudentsView.setAdapter(listOfStudentsViewAdapter);
        }
    }

}