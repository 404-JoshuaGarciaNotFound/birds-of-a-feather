package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.student.database.AppDatabaseCourses;
import com.example.myapplication.student.database.AppDatabaseStudent;
import com.example.myapplication.student.database.Course;
import com.example.myapplication.student.database.CourseDao;
import com.example.myapplication.student.database.Student;
import com.example.myapplication.student.database.StudentDao;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    //This variable should be saved to database.
    private boolean setup = false;
    private AppDatabaseStudent dbStudent;
    private AppDatabaseCourses dbCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        //This method doesnt work yet.
        try {
            File dbFile = new File("app/src/main/assets/students.db");
            if (dbFile.exists()) {
                dbFile.delete();
            }
            dbFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        */

        //here we add a check to see if first time setup is done.
        if(setup != true) {
            //Calls alert popup!
            firstTimeSetup();
            Log.d("myTag", "This is my message"); //Test
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

    public void firstTimeSetup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        // layout activity_first_time_setup.xml
        builder.setView(inflater.inflate(R.layout.activity_first_time_setup, null));
        builder.setCancelable(false);
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
                    //Save name to userTextFile in assets. Not sure how to get the file stream going
                    SetURL();
                } else {
                    name.setError("Name cannot be empty!");
                }
                Log.d("Name that was typed in ", userName);

            }
        });

        //Next step add classes
        //Call add classes interface
        //Once First time setup is done set setup boolean to true.

    }

    public void SetURL() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_first_time_setup_geturl, null));
        builder.setCancelable(false);
        AlertDialog FTSetup2 = builder.create();
        FTSetup2.setTitle("First Time Setup");
        FTSetup2.show();
        Button submitted = (Button) FTSetup2.findViewById(R.id.SubmitURL);
        submitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {

                final EditText URLy = (EditText) FTSetup2.findViewById(R.id.personURL);
                String headshotURL = URLy.getText().toString();
                //Require users to type name no blanks name should be saved to database
                if(!headshotURL.equals("")){



                    //Save name to database
                    FTSetup2.dismiss();
                    firstTimeAddClasses();
                }
                Log.d("URL that was typed in ", headshotURL);


            }
        });
    }

    public void firstTimeAddClasses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View addClassesView = inflater.inflate(R.layout.activity_first_time_add_classes, null);
        builder.setView(addClassesView);
        builder.setCancelable(false);
        AlertDialog addClasses = builder.create();
        addClasses.setTitle("Add Classes");
        addClasses.show();

        // Spinner for selecting year
        Spinner spinner1 = (Spinner) addClassesView.findViewById(R.id.selectYear);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        // Spinner for selecting quarter
        Spinner spinner2 = (Spinner) addClassesView.findViewById(R.id.selectQuarter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.quarter, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        // Edit text field for subject
        EditText editSubject = (EditText) addClassesView.findViewById(R.id.editSubject);

        // Edit text field for course number
        EditText editCourseNumber = (EditText) addClassesView.findViewById(R.id.editCourseNumber);

        // Enter button
        Button enterCourseInfo = (Button) addClassesView.findViewById(R.id.enterCourseInfo);
        enterCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yearInd = spinner1.getSelectedItemPosition();
                int quarterInd = spinner2.getSelectedItemPosition();
                String subject = editSubject.getText().toString().trim();
                String courseNumber = editCourseNumber.getText().toString().trim();

                // To get string of year and quarter use the following two lines
                // String year = getResources().getStringArray(R.array.year)[yearInd];
                // String quarter = getResources().getStringArray(R.array.quarter)[quarterInd];

                // Course number can also be converted to int when saving to db

                boolean exit = true;

                if (subject.equalsIgnoreCase("")) {
                    editSubject.setError("You didn't input a subject!");
                    exit = false;
                }

                if (courseNumber.equalsIgnoreCase("")) {
                    editCourseNumber.setError("You didn't input a course number!");
                    exit = false;
                }

                if (exit) {
                    // TODO: save info to database
                    String courseCode = subject+courseNumber;
                    int courseId = dbCourse.courseDao().count() + 1;
                    String courseYear = String.valueOf(yearInd);
                    String courseQuarter = String.valueOf(quarterInd);
                    Course addedCourse = new Course(courseId, courseYear, courseQuarter, courseCode);
                    CourseDao CourseDao = dbCourse.courseDao();
                    CourseDao.insertCourse(addedCourse);
                    addClasses.cancel();
                    repeatAddClasses(yearInd, quarterInd, subject, courseNumber);
                }
            }
        });
    }

    public void repeatAddClasses(int previousYearInd, int previousQuarterInd, String previousSubject, String previousCourseNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View addClassesView = inflater.inflate(R.layout.activity_repeat_add_classes, null);
        builder.setView(addClassesView);
        builder.setCancelable(false);
        AlertDialog addClasses = builder.create();
        addClasses.setTitle("Add Classes");
        addClasses.show();

        // Spinner for selecting year
        Spinner spinner1 = (Spinner) addClassesView.findViewById(R.id.selectYear);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setSelection(previousYearInd);

        // Spinner for selecting quarter
        Spinner spinner2 = (Spinner) addClassesView.findViewById(R.id.selectQuarter);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.quarter, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(previousQuarterInd);

        // Edit text field for subject
        EditText editSubject = (EditText) addClassesView.findViewById(R.id.editSubject);
        editSubject.setText(previousSubject);

        // Edit text field for course number
        EditText editCourseNumber = (EditText) addClassesView.findViewById(R.id.editCourseNumber);
        editCourseNumber.setText(previousCourseNumber);

        // Enter button
        Button enterCourseInfo = (Button) addClassesView.findViewById(R.id.enterCourseInfo);
        enterCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yearInd = spinner1.getSelectedItemPosition();
                int quarterInd = spinner2.getSelectedItemPosition();
                String subject = editSubject.getText().toString().trim();
                String courseNumber = editCourseNumber.getText().toString().trim();

                // To get string of year and quarter use the following two lines
                // String year = getResources().getStringArray(R.array.year)[yearInd];
                // String quarter = getResources().getStringArray(R.array.quarter)[quarterInd];

                // Course number can also be converted to int when saving to db

                boolean exit = true;

                if (subject.equalsIgnoreCase("")) {
                    editSubject.setError("You didn't input a subject!");
                    exit = false;
                }

                if (courseNumber.equalsIgnoreCase("")) {
                    editCourseNumber.setError("You didn't input a course number!");
                    exit = false;
                }

                // Make sure the user has changed some fields
                if (yearInd == previousYearInd
                        && quarterInd == previousQuarterInd
                        && subject.equalsIgnoreCase(previousSubject)
                        && courseNumber.equalsIgnoreCase(previousCourseNumber)
                ) {
                    editCourseNumber.setError("Please add a different class!");
                    exit = false;
                }

                if (exit) {
                    // TODO: save info to database
                    String courseCode = subject+courseNumber;
                    int courseId = dbCourse.courseDao().count() + 1;
                    String courseYear = String.valueOf(yearInd);
                    String courseQuarter = String.valueOf(quarterInd);
                    Course addedCourse = new Course(courseId, courseYear, courseQuarter, courseCode);
                    CourseDao CourseDao = dbCourse.courseDao();
                    CourseDao.insertCourse(addedCourse);
                    addClasses.cancel();
                    repeatAddClasses(yearInd, quarterInd, subject, courseNumber);
                }
            }
        });

        // Done button
        Button doneAddingClasses = (Button) addClassesView.findViewById(R.id.doneAddingClasses);
        doneAddingClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set first time setup to true when adding classes is finished
                setup = true;
                addClasses.cancel();
            }
        });
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
        if(current.equals("STOP")){
            startStop.setText("START");
            //Green color code
            startStop.setBackgroundColor(0Xff99cc00);
        }
    }


    public void switchMocktoList(View view) {
        dbStudent = AppDatabaseStudent.singleton(this);
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
                    int idOfNewStudent = dbStudent.studentDao().count() + 1;
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

                    StudentDao studentDao = dbStudent.studentDao();
                    studentDao.insertStudent(toAddStudent);
                    DemoMock.setText("");
                }

            });
            //Blue color code
            mockSwitch.setBackgroundColor(0xff0099cc);
        }
        if (current.equals("LIST")) {
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
            StudentDao studentDao = dbStudent.studentDao();
            List<Student> listOfStudent = studentDao.getAll();
            RecyclerView listOfStudentsView = findViewById(R.id.list_of_students);
            StudentAdapter listOfStudentsViewAdapter = new StudentAdapter(listOfStudent);
            listOfStudentsView.setAdapter(listOfStudentsViewAdapter);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        dbStudent.studentDao().clear();
        dbCourse.courseDao().clear();
    }

}