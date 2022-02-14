package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;
import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    private boolean setupComplete = false;

    // keys for Shared Preference
    private final String IS_FIRST_TIME_SETUP_COMPLETE = "isFirstTimeSetUpComplete";
    private final String USER_NAME = "user_name";
    private final String HEAD_SHOT_URL = "head_shot_url";
    private final String USER_COURSE_ = "user_course_";

    // database variables
    private AppDatabaseStudent dbStudent;
    private AppDatabaseCourses dbCourse;
    private StudentDao studentDao;
    private CourseDao courseDao;

    // SharedPreference that store user info
    private SharedPreferences userInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Configure sign-in to request the user's ID, email address, and basic
//        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//
//        // Set the dimensions of the sign-in button.
//        SignInButton signInButton = findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//
//        signInButton.setOnClickListener(view -> signIn());

        // initialize database-relevant
        dbStudent = AppDatabaseStudent.singleton(this);
        dbCourse = AppDatabaseCourses.singleton(this);
        studentDao = dbStudent.studentDao();
        courseDao = dbCourse.courseDao();

        // initialize the shared preference that store user info
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);

        // check for first time setup
        if(!userInfo.getBoolean(IS_FIRST_TIME_SETUP_COMPLETE, false)) {
            firstTimeSetup();
        }

        //DEMO MODE UI stuff,
        EditText DemoMock = findViewById(R.id.DemomockUserInput);
        DemoMock.setVisibility(View.INVISIBLE);
        Button mockEnter = findViewById(R.id.SubmitMockUser);
        mockEnter.setVisibility(View.INVISIBLE);
    }

    /*Google auth code here. Since it is difficult to test and run on an emulator we have left it
    commented out but the code is functional on a physical android device.

//    private void signIn() {
//        Toast.makeText(this, "in signIn", Toast.LENGTH_SHORT).show();
//
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//            if (acct != null) {
//                String personName = acct.getDisplayName();
//                String personGivenName = acct.getGivenName();
//                String personFamilyName = acct.getFamilyName();
//                String personEmail = acct.getEmail();
//                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();
//
//                // TODO: Change
//                Toast.makeText(this, "User name:" + personName, Toast.LENGTH_SHORT).show();
//            }
//
//            // Signed in successfully, show authenticated UI.
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.d("Message", e.toString());
//        }
//    }


    */
    //This method opens an alert window that records the preferred name of the user.
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
        submitted.setOnClickListener(v8 -> {
            final EditText name = (EditText) FTSetup.findViewById(R.id.personName);
            String userName = name.getText().toString();
            //Require users to type name no blanks name should be saved to database
            if(!userName.equals("")){

                FTSetup.dismiss();
                //Save name to userTextFile in assets. Not sure how to get the file stream going
                SetURL(userName);

            } else {
                name.setError("Name cannot be empty!");
            }
            Log.d("Name that was typed in ", userName);

            // store user name to the SharedPreference
            SharedPreferences.Editor userInfoEditor = userInfo.edit();
            userInfoEditor.putString(USER_NAME, userName);
            userInfoEditor.apply();
        });

        // avoid second time set-up
        SharedPreferences.Editor userInfoEditor = userInfo.edit();

        userInfoEditor.putBoolean(IS_FIRST_TIME_SETUP_COMPLETE, true);
        userInfoEditor.apply();
    }

    public void SetURL(String Uname) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_first_time_setup_geturl, null));
        builder.setCancelable(false);
        AlertDialog FTSetup2 = builder.create();
        FTSetup2.setTitle("First Time Setup");
        FTSetup2.show();
        Button submitted = (Button) FTSetup2.findViewById(R.id.SubmitURL);
        submitted.setOnClickListener(v3 -> {
            final EditText URLy = (EditText) FTSetup2.findViewById(R.id.personURL);
            String headshotURL = URLy.getText().toString();
            //Require users to type name no blanks name should be saved to database
            if(!headshotURL.equals("")){
                FTSetup2.dismiss();
                firstTimeAddClasses();
            } else {
                URLy.setError("URL cannot be empty!");
            }
            Log.d("URL that was typed in ", headshotURL);

            // store user head shot url to the
            // shared preference
            SharedPreferences.Editor userInfoEditor = userInfo.edit();
            userInfoEditor.putString(HEAD_SHOT_URL, headshotURL);
            userInfoEditor.apply();
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
                 String year = getResources().getStringArray(R.array.year)[yearInd];
                 String quarter = getResources().getStringArray(R.array.quarter)[quarterInd];

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
//                    Log.d("str format: ", year + " " + quarter + " " + subject + " " + courseNumber);
                    addCourse(year, quarter, subject, courseNumber);
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

                String year = getResources().getStringArray(R.array.year)[yearInd];
                String quarter = getResources().getStringArray(R.array.quarter)[quarterInd];
                if (exit) {
                    addCourse(year, quarter, subject, courseNumber);
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
                    addStudent(mockUserInfo);
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


            onClickList();

            //executorService.submit(this::onClickList);
        }
    }

    /**
     * This method handles the click event for the button list
     * It shows a list of students who has taken the same course with the app's user, sorted by the
     * number of classes the student shared with the user
     */
    private void onClickList() {
        // get user's taken courses
        List<String> listOfUserCourses = formatUserCourses();

        // get list of students
        List<Student> listOfStudents = studentDao.getAll();

        // check whether the students' course match the user's
        // if so, add the user to the students to display
        for (Student student: listOfStudents) {
            int numSharedCourses = 0;
            for (String courseStr : listOfUserCourses) {
                if (student.getCourses().contains(courseStr)) {
                    // the str of courses of a student contains a substring representing one of the
                    // user's taken courses, the student is a match.
                    numSharedCourses++;
                }
            }
            student.setNumSharedCourses(numSharedCourses);
        }
        listOfStudents.sort(Comparator.comparing(Student::getNumSharedCourses).reversed());

        RecyclerView listOfStudentsView = findViewById(R.id.list_of_students);
        StudentAdapter listOfStudentsViewAdapter = new StudentAdapter(listOfStudents);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listOfStudentsView.setAdapter(listOfStudentsViewAdapter);
            }
        });
    }

    /**
     * This method is a helper method that format the user courses
     * i.e.
         * the user's courses are stored in a database where each column represents an attribute
         * of that course
     *
         * We want to format each course into a string "year,quarter,course_code"
     *
     * @return a list of the formatted strings
     */
    @NonNull
    private List<String> formatUserCourses() {
        List<Course> listOfUserCourses = courseDao.getAllCourses();
        List<String> listOfFormattedCourses = new ArrayList<>();

        // for comparison purpose, create a str in format : year,quarter,subject,number
        int count = 0; // used to maintain the key for Shared Preference
        for (Course course :
                listOfUserCourses) {
            StringBuilder courseStr = new StringBuilder();
            courseStr.append(course.getYear());
            courseStr.append(",");
            courseStr.append(course.getQuarter().toUpperCase());
            courseStr.append(",");
            courseStr.append(course.getCourseCode());

            listOfFormattedCourses.add(courseStr.toString());

            SharedPreferences.Editor userInfoEditor = userInfo.edit();
            userInfoEditor.putString(USER_COURSE_ + count, courseStr.toString());
            userInfoEditor.apply();
            count++;
        }

        return listOfFormattedCourses;
    }

    /**
     * This method is the click event for the enter button.
     * It will take in a string of format:
         * {name},,,
         * {url},,,
         * 2021,FA,CSE,210
         * 2022,WI,CSE,110
         * 2022,SP,CSE,110
     * , populate the mock students (the students who is, in the future, detected by the bluetooth)
     * information with the given name, url, course1, course2,...
     * @param mockUserInfo the input string consisting of the mock user's info
     */
    private void addStudent(String mockUserInfo) {
        String[] splitInfo = mockUserInfo.split("\n");
        int id = studentDao.count() + 1;
        String name = splitInfo[0]
                .substring(0, splitInfo[0].length() - 3); // drop ,,,
        String url = splitInfo[1]
                .substring(0, splitInfo[1].length() - 3); // drop ,,,
        StringBuilder courses = new StringBuilder();
        for (int i = 2; i < splitInfo.length; i++) {
            courses.append(splitInfo[i]);
            if (i != splitInfo.length - 1) courses.append(" ");
        }
        Log.d("courses", courses.toString());

        Student toAddStudent = new Student(id, url, name, courses.toString(), 0);

        // add the student to the database
        studentDao.insertStudent(toAddStudent);
    }

    /**
     * This method add courses to the course database.
     * These courses are courses of the current user
     * (the one student who is using the app, in contrast to those who are searched by the bluetooth
     * @param year
     * @param quarter
     * @param subject
     * @param courseNumber
     */
    private void addCourse(String year, String quarter, String subject, String courseNumber) {
        String courseCode = subject + "," + courseNumber;
        courseDao.insertCourse(
                new Course(courseDao.count() + 1, year, quarter, courseCode));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbStudent.close();
    }

}