package com.example.myapplication;

import static com.example.myapplication.CreateBuilderAlert.buildBuilder;
import static com.example.myapplication.FirstTimeSetup.firstTimeSetupName;
import static com.example.myapplication.FormatUsersCourseInfo.formatUserCourses;
import static com.example.myapplication.OptionsMenuControls.closeMenu;
import static com.example.myapplication.OptionsMenuControls.showMenu;
import static com.example.myapplication.ArrangeStudentList.arrangeStudentList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;
import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    public boolean active = false;
    // keys for Shared Preference
    public final String IS_FIRST_TIME_SETUP_COMPLETE = "isFirstTimeSetUpComplete";
    public final String USER_NAME = "user_name";
    public final String HEAD_SHOT_URL = "head_shot_url";
    public final String USER_COURSE_ = "user_course_";
    public final String USER_SAVEDSESSIONS= "saved_session";
    public final String USER_FAVORITES = "favorites";
    // database variables
    public AppDatabaseStudent dbStudent;
     AppDatabaseCourses dbCourse;
    public StudentDao studentDao;
    public CourseDao courseDao;
    // SharedPreference that store user info
    public static SharedPreferences userInfo;
    public MessageListener searchingClassmate;
    public Message mMessage;
    public static final String TAG = "Turn on Search";
    private static final int TTL_IN_SECONDS = 20; // Three minutes.
    private static final Strategy PUB_SUB_STRATEGY = new Strategy.Builder()
            .setTtlSeconds(TTL_IN_SECONDS).build();
    // bluetooth permission tracking variable
    public BTPermission btPermission;
    public SavingSession savingSession;
    public Date currentTime;
    public Date newTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Google login stuff. Move to own class //
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

        // check and ask for bluetooth permission
        btPermission = new BTPermission(MainActivity.this);
        if (!btPermission.BTPermissionIsGranted()) {
            Log.d("Bluetooth permission", "Bluetooth permission is not granted, ask for permission");
            btPermission.requestBTPermission();
        } else {
            Log.d("Bluetooth permission", "Bluetooth permission already granted");
        }
        // check for first time setup
        if(!userInfo.getBoolean(IS_FIRST_TIME_SETUP_COMPLETE, false)) {
            Log.d("SETUPLOG", "First time setup not complete... Running now!");
            //Run First time setup
            firstTimeSetupName(dbCourse, this, getLayoutInflater(), userInfo);
        }else{
            Log.d("SETUPLOG", "First time setup has been completed... skipping");
        }
        //DEMO MODE UI stuff,
        EditText DemoMock = findViewById(R.id.DemomockUserInput);
        DemoMock.setVisibility(View.INVISIBLE);
        Button mockEnter = findViewById(R.id.SubmitMockUser);
        mockEnter.setVisibility(View.INVISIBLE);
        //UI buttons
        FloatingActionButton options = findViewById(R.id.MoreOpts);
        FloatingActionButton FavoritesTab = findViewById(R.id.floatingActionButton2);
        FloatingActionButton ListSesh = findViewById(R.id.floatingActionButton3);
        FloatingActionButton FilterOptions = findViewById(R.id.floatingActionButton4);
        //This toggles it on or off and opens window
        // Set up nearby Message
        searchingClassmate = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.d(TAG, "Found message: " + new String(message.getContent()));
                // Connect to database
                String studentInfo = new String(message.getContent());
                String[] arrayOfStudentInfo = studentInfo.split("\n");
                String studentId = arrayOfStudentInfo[0];
                if (dbStudent.studentDao().isInserted(studentId)) {
                    Log.d(dbStudent.studentDao().getStudentByID(studentId).getName(), "Already in Database");
                }
                else {
                    String studentName = arrayOfStudentInfo[1];
                    String studentHeadShot = arrayOfStudentInfo[2];
                    String studentCourses = arrayOfStudentInfo[3];
                    Student newStudent = new Student(studentId, studentHeadShot, studentName, studentCourses, 0);
                    dbStudent.studentDao().insertStudent(newStudent);
                    Log.d("Student being added", newStudent.getName());
                }

                // Display HandView when found wave
                if (arrayOfStudentInfo.length == 5){
                    String[] waveInfo = arrayOfStudentInfo[4].split(",");
                    Log.d("myId", userInfo.getString("user_ID", "default"));
                    if (waveInfo[0].equals(userInfo.getString("user_ID", "default"))) {
                        dbStudent.studentDao().setWaveReceived(studentId, true);
                        Log.d("Wave sent from", dbStudent.studentDao().getStudentByID(studentId).getName());
                        Toast.makeText(MainActivity.this, "Wave Received from " + studentDao.getStudentByID(studentId).getName(), Toast.LENGTH_SHORT).show();
                    }
                }
                // Refresh List Student Recycler
                List<Student> studentList = arrangeStudentList(dbCourse, dbStudent, userInfo);
                refreshStudentList(studentList);

            }
            @Override
            public void onLost(@NonNull Message message){
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
                // Delete Student from Database
                String studentInfo = new String(message.getContent());
                String[] arrayOfStudentInfo = studentInfo.split("\n");
                String studentId = arrayOfStudentInfo[0];

                try {
                   // Student lostStudent = dbStudent.studentDao().getStudentByID(studentId);
                   // Log.d("Deleting Student", lostStudent.getName());
                  //  dbStudent.studentDao().deleteStudent(lostStudent);
                } catch (Exception e){
                   // Log.d(studentId, "Student not found");
                }
                // Refresh List Student Recycler
               // List<Student> studentList = arrangeStudentList(dbCourse, dbStudent, userInfo);
               // refreshStudentList(studentList);
            }
        };

        //Format publish message
        String myId = userInfo.getString("user_ID", "default");
        String myName = userInfo.getString("user_name", "default");
        String myHeadShot = userInfo.getString("head_shot_url", "default");
        List<String> listOfMyCourses = formatUserCourses(dbCourse, userInfo);
        StringBuilder coursesStr = new StringBuilder();
        for (String singleCourse : listOfMyCourses){
            coursesStr.append(singleCourse);
            coursesStr.append(" ");
        }
        String myCourses = coursesStr.toString();
        String myInfo = myId + "\n" + myName + "\n" + myHeadShot + "\n" + myCourses;
        mMessage = new Message(myInfo.getBytes());
        SharedPreferences.Editor userInfoEditor = userInfo.edit();
        userInfoEditor.putString("nearby_message", new String(mMessage.getContent()));
        userInfoEditor.apply();

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!active){
                    active = showMenu(userInfo,getLayoutInflater(),getResources(), FavoritesTab, ListSesh);
                }
                else{
                    active = closeMenu(FavoritesTab, ListSesh);
                }
            }
        });
        MainActivity contexty = this;
        ListSesh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(contexty, SessionScreen.class);
                //LS = list sessions0
                Set<String> LS = userInfo.getStringSet(USER_SAVEDSESSIONS, null);
                if(LS != null) {
                    intent.putExtra("ListStr", new ArrayList<>(LS));
                    startActivity(intent);
                }
                else{
                    Log.d("Alert", "User trying to view empty session");
                    Toast.makeText(contexty, "No sessions to display", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FavoritesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexty, FavoriteScreen.class);
                Set<String> LF = userInfo.getStringSet(USER_FAVORITES, null);
                Log.d("vals", String.valueOf(LF));

                if(LF != null){
                    intent.putExtra("ListFav", new ArrayList<>(LF));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        List<Student> studentList = arrangeStudentList(dbCourse, dbStudent, userInfo);
        Nearby.getMessagesClient(this).publish(mMessage);
        Nearby.getMessagesClient(this).subscribe(searchingClassmate);
        refreshStudentList(studentList);

    }

    public static SharedPreferences returnSP(){
        return userInfo;
    }
    //TODO: Move this to its own class.
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
    /**
     * This method is responsible for enabling and disabling the search feature.
     * It is also responsible for calling saving session to database method.
    **/



    public AlertDialog saveSesh;
    public void StartStopButton(View view) {
        //Add check if setup complete
        Button startStop = findViewById(R.id.StartStopBttn);
        String current = startStop.getText().toString();
        Log.d("CurrentState", current);
        if(current.equals("START")) {
//            if (!btPermission.BTPermissionIsGranted()) {
//                Log.d("Bluetooth permission", "Bluetooth permission is not granted, refuse to proceed");
//                btPermission.promptPermissionRequiredMessage();
//            } else {
                // Log.d("Bluetooth permission", "Bluetooth permission granted, allow to proceed");
                startStop.setText("STOP");
                currentTime = Calendar.getInstance().getTime();

                studentDao.clear();

                // Create initial session that will be modified whenever new mock occurs
//                refreshStudentList();
                String SName = currentTime.toString();
                //Add if statement that checks DB if exists
                SharedPreferences.Editor insertSavedSesh = userInfo.edit();
                Set<String> strings = userInfo.getStringSet(USER_SAVEDSESSIONS, null);
                boolean alreadyExists = false;
                if (strings == null) {
                    strings = new HashSet<>(Arrays.asList(SName));
                } else {
                    alreadyExists = strings.contains(SName);
                }
                if (!alreadyExists) {
                    Session session = new Session(SName);
                    session.populateSessionContentWithSameCourse(studentDao, courseDao);
                    session.saveSession(userInfo);
                }

                Log.d("Nearby Messages Status", "ENABLED");
                //Red color code
                startStop.setBackgroundColor(0xFFFF0000);
                //Turn on Nearby Message
                PublishOptions pubOptions = new PublishOptions.Builder()
                    .setStrategy(PUB_SUB_STRATEGY)
                    .setCallback(new PublishCallback() {
                        @Override
                        public void onExpired() {
                            super.onExpired();
                            Log.i("Nearby", "No longer publishing");
                        }
                    }).build();
                Nearby.getMessagesClient(this).publish(mMessage, pubOptions).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Publishing!", Toast.LENGTH_SHORT).show();
                    }
                });

                SubscribeOptions subOptions = new SubscribeOptions.Builder()
                    .setStrategy(PUB_SUB_STRATEGY)
                    .setCallback(new SubscribeCallback() {
                        @Override
                        public void onExpired() {
                            super.onExpired();
                            Log.i("Nearby", "No longer subscribing");
                        }
                    }).build();
                Nearby.getMessagesClient(this).subscribe(searchingClassmate, subOptions).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(MainActivity.this, "Subscribing!", Toast.LENGTH_SHORT).show();
                }
            });
//                //Delete database before Searching
//                dbStudent.studentDao().clear();
                // Clear Database for a new search

                Log.d("In db", userInfo.getString(USER_NAME, "no name"));
                Log.d("In db", userInfo.getString(USER_COURSE_, "no name"));
                Log.d("In db", userInfo.getString(HEAD_SHOT_URL, "no name"));
                Log.d("publish message", new String(mMessage.getContent()));

                Toast.makeText(this, "Start Searching", Toast.LENGTH_SHORT).show();

                // Set up recyclerView of list of students
                RecyclerView studentsRecyclerView = findViewById(R.id.list_of_students);
                RecyclerView.LayoutManager studentsLayoutManager = new LinearLayoutManager(this);
                studentsRecyclerView.setLayoutManager(studentsLayoutManager);
                studentsRecyclerView.setVisibility(View.VISIBLE);

                //Refresh List
                List<Student> studentList = arrangeStudentList(dbCourse, dbStudent, userInfo);
                refreshStudentList(studentList);
//            }
        }
        if(current.equals("STOP")){
            startStop.setText("START");
            Log.d("Nearby Messages Status", "DISABLED");
            //Turn off Nearby Message
            Nearby.getMessagesClient(this).unpublish(mMessage);
            Nearby.getMessagesClient(this).unsubscribe(searchingClassmate);
            Toast.makeText(this, "Stop Searching", Toast.LENGTH_SHORT).show();
            //Refresh the list after turning off search
            List<Student> studentList = arrangeStudentList(dbCourse, dbStudent, userInfo);
            refreshStudentList(studentList);

            //Dialogue for saving the session
            CreateBuilderAlert.returningVals AD = buildBuilder(this, R.layout.savesession_uiscreen_prompt, getLayoutInflater(), false, "Save your Session");
            AlertDialog saveSesh = AD.alertDiag;
            saveSesh.show();
            Button b = saveSesh.findViewById(R.id.saveButtonForSessionName);
            b.setOnClickListener(v9 -> {
                final EditText seshName = (EditText) saveSesh.findViewById(R.id.editTextTextPersonName2);
                String reNamed = seshName.getText().toString();
                //Add if statement that checks DB if exists
                if(!reNamed.equals("")) {
//                    SharedPreferences.Editor insertSavedSesh = userInfo.edit();
//                    Set<String> strings = userInfo.getStringSet(USER_SAVEDSESSIONS, null);
//                    boolean alreadyExists = false;
//                    if(strings == null) {
//                        strings = new HashSet<>(Arrays.asList(SName));
//                    }else{
//                        alreadyExists = strings.contains(SName);
//                    }
//                    if(!alreadyExists) {
//                        Session session = new Session(SName);
//                        session.populateSessionContentWithSameCourse(studentDao, courseDao);
//                        session.saveSession(userInfo);
//                        saveSesh.cancel();

                    Set<String> keys = userInfo.getStringSet("saved_session", null);
                    String key = currentTime.toString();
                    SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                    Set<String> vals = userInfo.getStringSet(key, null);

                    boolean alreadyExist = keys.contains(reNamed);
                    if (!alreadyExist) {
                        //Removes entry from string set
                        SharedPreferences.Editor UIEdit = userInfo.edit();
                        UIEdit.remove(key);
                        UIEdit.apply();
                        UIEdit.putStringSet(reNamed, vals);
                        UIEdit.apply();
                        Log.d("Old keys", String.valueOf(keys));
                        //Now will remove old session name from keys list
                        UIEdit.remove("saved_session");
                        UIEdit.apply();
                        ArrayList<String> temp = new ArrayList<String>(keys);
                        int renamevaL = temp.indexOf(key);
                        temp.set(renamevaL, reNamed);
                        Set<String> newKeys = new HashSet<String>(temp);
                        Log.d("newKeys", String.valueOf(newKeys));
                        UIEdit.putStringSet("saved_session", newKeys);
                        UIEdit.apply();
                        Log.d("Renamed", String.valueOf(userInfo.getStringSet(reNamed, null)));
                        saveSesh.cancel();
                    } else {
                        seshName.setError("No duplicate names allowed!");
                    }
                }

                else{
                    seshName.setError("Your Session name can not be blank.");
                }
            });
            //Green color code
            startStop.setBackgroundColor(0Xff99cc00);
        }
    }
    //This is for mocking messages
    public void mockMessage(View view) {
        Button startStopBtn = findViewById(R.id.StartStopBttn);
        if (startStopBtn.getText().equals("START")) { // Only allow mocking during searching
            Toast.makeText(this, "Can only mock during searching!", Toast.LENGTH_SHORT).show();
            return;
        }
        RecyclerView studentsRecyclerView = findViewById(R.id.list_of_students);
        RecyclerView.LayoutManager studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
        Button mockSwitch = findViewById(R.id.nearByMockScreen);
        String current = mockSwitch.getText().toString();
        if(current.equals("MOCK")) {
            //DEMO MOCK USER MODE
            //Sets the recycler view invisible and input text visible for the mock user
            mockSwitch.setText("BACK");
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
                    String[] splitInfo = mockUserInfo.split("\n");
                    String idStr = splitInfo[0]
                            .substring(0, splitInfo[0].length() - 4); //drop ,,,,
                    String name = splitInfo[1]
                            .substring(0, splitInfo[1].length() - 4); // drop ,,,,
                    String url = splitInfo[2]
                            .substring(0, splitInfo[2].length() - 4); // drop ,,,,
                    StringBuilder courses = new StringBuilder();
                    StringBuilder waveInfo = new StringBuilder();
                    for (int i = 3; i < splitInfo.length; i++) {
                        String[] checkWave = splitInfo[i].split(",");
                        if (checkWave[1].equals("wave")){
                            waveInfo.append(checkWave[0] + ",wave");
                        }
                        else {
                            courses.append(splitInfo[i]);
                            courses.append(" ");
                        }
                    }
                    courses.deleteCharAt(courses.length() - 1);

                    int numSharedCourses = getNumSharedCourses(courses.toString());

                    String newVal = idStr + " " + name + " " + url + " " + numSharedCourses + " " + courses.toString() + " ";

                    // Modify session here
                    newTime = Calendar.getInstance().getTime();
                    String reNamed = newTime.toString();
                    String key = currentTime.toString();
                    SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                    Set<String> vals = userInfo.getStringSet(key, null);
                    vals.add(newVal);
                    Set<String> keys = userInfo.getStringSet("saved_session", null);
                    //Removes entry from string set
                    SharedPreferences.Editor UIEdit = userInfo.edit();
                    UIEdit.remove(key);
                    UIEdit.apply();
                    UIEdit.putStringSet(reNamed, vals);
                    UIEdit.apply();
                    Log.d("Old keys", String.valueOf(keys));
                    //Now will remove old session name from keys list
                    UIEdit.remove("saved_session");
                    UIEdit.apply();
                    ArrayList<String> temp = new ArrayList<String>(keys);
                    int renamevaL = temp.indexOf(key);
                    temp.set(renamevaL, reNamed);
                    Set<String> newKeys = new HashSet<String>(temp);
                    Log.d("newKeys", String.valueOf(newKeys));
                    UIEdit.putStringSet("saved_session", newKeys);
                    UIEdit.apply();
                    Log.d("Renamed", String.valueOf(userInfo.getStringSet(reNamed, null)));

                    currentTime = newTime;

                    String fakedMessageStr = idStr + "\n" +
                            name + "\n" +
                            url + "\n" +
                            courses + "\n" +
                            waveInfo;
                    Message fakedMessage = new Message(fakedMessageStr.getBytes(StandardCharsets.UTF_8));
                    searchingClassmate.onFound((fakedMessage));
                    DemoMock.setText("");
                }
            });
            //Blue color code
            mockSwitch.setBackgroundColor(0xff0099cc);
        }
        if (current.equals("BACK")) {
            //LIST STUDENT MODE
            //For demo mode maybe when user clicks list it should update mocked users
            mockSwitch.setText("MOCK");
            //Sets the recycler view visible and input text invisible for the mock user
            EditText DemoMock = findViewById(R.id.DemomockUserInput);
            DemoMock.setVisibility(View.INVISIBLE);
            Button mockEnter = findViewById(R.id.SubmitMockUser);
            mockEnter.setVisibility(View.INVISIBLE);
            //Green color code
            mockSwitch.setBackgroundColor(0Xff99cc00);
            RecyclerView studentList = findViewById(R.id.list_of_students);
            studentList.setVisibility(View.VISIBLE);

        }
    }

    /**
     * This method helps to refresh the recyclerView in Main Page
     */
    private void refreshStudentList(List<Student> studentList) {
        try {
            RecyclerView listOfStudentsView = findViewById(R.id.list_of_students);
            StudentAdapter listOfStudentsViewAdapter = new StudentAdapter(userInfo, studentList);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listOfStudentsView.setAdapter(listOfStudentsViewAdapter);
                    // This block handles sort-by-class-size / recency buttons on main
                    Button classSortButtonMain = findViewById(R.id.class_size_sort_button_main);
                    classSortButtonMain.setVisibility(View.VISIBLE);
                    Button recencySortButtonMain = findViewById(R.id.recency_sort_button_main);
                    recencySortButtonMain.setVisibility(View.VISIBLE);

                    List<Course> courseList = dbCourse.courseDao().getAllCourses();
//                    SortUtil sortUtil = new SortUtil(userInfo, courseList, listOfStudents, classSortButtonMain, recencySortButtonMain, listOfStudentsView);
//                    sortUtil.setupButtons();
                }
            });

        } catch (Exception e) {
            Log.d("Error", String.valueOf(e));
        }
    }


    //Cleans up program for shut down.
    @Override
    protected void onDestroy() {
        savingSession = new SavingSession(userInfo, currentTime, studentDao, courseDao, "");
        savingSession.saveCurrentSession();
        super.onDestroy();
        dbStudent.close();
    }



//    // Deprecated in MS2
//    //This is for mocking database
//    public void switchMocktoList(View view) {
//        RecyclerView studentsRecyclerView = findViewById(R.id.list_of_students);
//        RecyclerView.LayoutManager studentsLayoutManager = new LinearLayoutManager(this);
//        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
//        Button mockSwitch = findViewById(R.id.nearByMockScreen);
//        String current = mockSwitch.getText().toString();
//        if(current.equals("MOCK")) {
//            //DEMO MOCK USER MODE
//            //Sets the recycler view invisible and input text visible for the mock user
//            mockSwitch.setText("LIST");
//            RecyclerView studentList = findViewById(R.id.list_of_students);
//            studentList.setVisibility(View.INVISIBLE);
//            EditText DemoMock = findViewById(R.id.DemomockUserInput);
//            DemoMock.setVisibility(View.VISIBLE);
//            Button mockEnter = findViewById(R.id.SubmitMockUser);
//            mockEnter.setVisibility(View.VISIBLE);
//            mockEnter.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v2) {
//                    EditText newUser = (EditText) findViewById(R.id.DemomockUserInput);
//                    String mockUserInfo = newUser.getText().toString();
//                    addStudent(dbStudent, mockUserInfo);
//                    DemoMock.setText("");
//                }
//            });
//            //Blue color code
//            mockSwitch.setBackgroundColor(0xff0099cc);
//        }
//        if (current.equals("LIST")) {
//            //LIST STUDENT MODE
//            //For demo mode maybe when user clicks list it should update mocked users
//            mockSwitch.setText("MOCK");
//            //Sets the recycler view visible and input text invisible for the mock user
//            EditText DemoMock = findViewById(R.id.DemomockUserInput);
//            DemoMock.setVisibility(View.INVISIBLE);
//            Button mockEnter = findViewById(R.id.SubmitMockUser);
//            mockEnter.setVisibility(View.INVISIBLE);
//            RecyclerView studentList = findViewById(R.id.list_of_students);
//            studentList.setVisibility(View.VISIBLE);
//            //Green color code
//            mockSwitch.setBackgroundColor(0Xff99cc00);
//            refreshStudentList();
//        }
//    }

    public int getNumSharedCourses(String courses) {

        int numSharedCourses = 0;
        int i = 0;

        while(true) {
            StringBuilder course = new StringBuilder();
            course.append("user_course_");
            course.append(i++);
            String checkCourse = userInfo.getString(course.toString(), "default");
            if(checkCourse.equals("default"))
                break;
            if(courses.contains(checkCourse))
                numSharedCourses++;
        }

        return numSharedCourses;
    }

    public void publishWave(Message waveMessage){
        Nearby.getMessagesClient(this).publish(waveMessage);
        Log.d("Publish Message", new String(waveMessage.getContent()));
    }
}