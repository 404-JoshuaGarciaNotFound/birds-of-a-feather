package com.example.myapplication;

import static com.example.myapplication.CreateBuilderAlert.buildBuilder;
import static com.example.myapplication.FirstTimeSetup.firstTimeSetupName;
import static com.example.myapplication.FormatUsersCourseInfo.formatUserCourses;
import static com.example.myapplication.OptionsMenuControls.buildFavoritesSection;
import static com.example.myapplication.OptionsMenuControls.buildListFilters;
import static com.example.myapplication.OptionsMenuControls.buildListSession;
import static com.example.myapplication.OptionsMenuControls.closeMenu;
import static com.example.myapplication.OptionsMenuControls.showMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.Set;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private boolean active = false;
    // keys for Shared Preference
    private final String IS_FIRST_TIME_SETUP_COMPLETE = "isFirstTimeSetUpComplete";
    private final String USER_NAME = "user_name";
    private final String HEAD_SHOT_URL = "head_shot_url";
    private final String USER_COURSE_ = "user_course_";
    private final String USER_SAVEDSESSIONS= "saved_session";
    // database variables
    private AppDatabaseStudent dbStudent;
    private AppDatabaseCourses dbCourse;
    private StudentDao studentDao;
    private CourseDao courseDao;
    // SharedPreference that store user info
    private SharedPreferences userInfo;
    private MessageListener searchingClassmate;
    private Message mMessage;
    private static final String TAG = "Turn on Search";
    // bluetooth permission tracking variable
    private BTPermission btPermission;
    private Date currentTime;
    private SavingSession savingSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        buildListSession(this, getLayoutInflater());
        buildListFilters(this, getLayoutInflater());
        buildFavoritesSection(this, getLayoutInflater());
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
                String studentName = arrayOfStudentInfo[1];
                String studentHeadShot = arrayOfStudentInfo[2];
                String studentCourses = arrayOfStudentInfo[3];
                Student newStudent = new Student(studentId, studentHeadShot, studentName, studentCourses, 0);
                dbStudent.studentDao().insertStudent(newStudent);
                Log.d("Student being added", newStudent.getName());

                // Refresh List Student Recycler
                refreshStudentList();
            }

            @Override
            public void onLost(@NonNull Message message){
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));

                // Delete Student from Database
                String studentInfo = new String(message.getContent());
                String[] arrayOfStudentInfo = studentInfo.split("\n");
                String studentId = arrayOfStudentInfo[0];

                try {
                    Student lostStudent = dbStudent.studentDao().getStudentByID(studentId);
                    Log.d("Deleting Student", lostStudent.getName());
                    dbStudent.studentDao().deleteStudent(lostStudent);
                } catch (Exception e){
                    Log.d(studentId, "Student not found");
                }

                // Refresh List Student Recycler
                refreshStudentList();
            }

        };

        //Format publish message
        String myId = UUID.randomUUID().toString();
        String myName = userInfo.getString("user_name", "default");
        String myHeadShot = userInfo.getString("head_shot_url", "default");

        /********************************FIX THIS BUG!*******************************/
        /** Whenever methods of courseDao is called, all unit tests after the first one will fail **/
        List<String> listOfMyCourses = formatUserCourses(dbCourse, userInfo);
        //List<String> listOfMyCourses = new ArrayList<String>();
        /****************************************************************************/

        StringBuilder coursesStr = new StringBuilder();
        for (String singleCourse : listOfMyCourses){
            coursesStr.append(singleCourse);
            coursesStr.append(" ");
        }
        String myCourses = coursesStr.toString();
        String myInfo = myId + "\n" + myName + "\n" + myHeadShot + "\n" + myCourses;
        mMessage = new Message(myInfo.getBytes());

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!active){
                    active = showMenu(userInfo, getLayoutInflater(), getResources(), FavoritesTab, ListSesh, FilterOptions);
                }
                else{
                    active = closeMenu(FavoritesTab, ListSesh, FilterOptions);
                }
            }
        });
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
            if (!btPermission.BTPermissionIsGranted()) {
                Log.d("Bluetooth permission", "Bluetooth permission is not granted, refuse to proceed");
                btPermission.promptPermissionRequiredMessage();
            } else {
                Log.d("Bluetooth permission", "Bluetooth permission granted, allow to proceed");
                startStop.setText("STOP");
                currentTime = Calendar.getInstance().getTime();
                Log.d("Nearby Messages Status", "ENABLED");
                //Red color code
                startStop.setBackgroundColor(0xFFFF0000);
                //Turn on Nearby Message
                Nearby.getMessagesClient(this).publish(mMessage);
                Nearby.getMessagesClient(this).subscribe(searchingClassmate);
                //Delete database before Searching
                dbStudent.studentDao().clear();

                Log.d("publish message", new String(mMessage.getContent()));
                Toast.makeText(this, "Start Searching", Toast.LENGTH_SHORT).show();

                // Set up recyclerView of list of students
                RecyclerView studentsRecyclerView = findViewById(R.id.list_of_students);
                RecyclerView.LayoutManager studentsLayoutManager = new LinearLayoutManager(this);
                studentsRecyclerView.setLayoutManager(studentsLayoutManager);
                studentsRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        if(current.equals("STOP")){
            startStop.setText("START");
            Log.d("Nearby Messages Status", "DISABLED");
            //Turn off Nearby Message
            Nearby.getMessagesClient(this).unpublish(mMessage);
            Nearby.getMessagesClient(this).unsubscribe(searchingClassmate);
            Toast.makeText(this, "Stop Searching", Toast.LENGTH_SHORT).show();
//            // Turn off recylerView of list of students
//            RecyclerView studentsRecylerView = findViewById(R.id.list_of_students);
//            studentsRecylerView.setVisibility(View.INVISIBLE);
            //Refresh the list after turning off search
            refreshStudentList();
            //Dialogue for saving the session
            CreateBuilderAlert.returningVals AD = buildBuilder(this, R.layout.savesession_uiscreen_prompt, getLayoutInflater(), false, "Save your Session");
            AlertDialog saveSesh = AD.alertDiag;
            saveSesh.show();
            Button b = saveSesh.findViewById(R.id.saveButtonForSessionName);
            b.setOnClickListener(v9 -> {
                final EditText seshName = (EditText) saveSesh.findViewById(R.id.editTextTextPersonName2);
                String SName = seshName.getText().toString();
                //Add if statement that checks DB if exists
                if(!SName.equals("")) {

                    SharedPreferences.Editor insertSavedSesh = userInfo.edit();
                    Set<String> strings = userInfo.getStringSet(USER_SAVEDSESSIONS, null);
                    boolean alreadyExists = false;
                    if(strings == null) {
                        strings = new HashSet<>(Arrays.asList(SName));
                    }else{
                        alreadyExists = strings.contains(SName);
                    }

                    if(!alreadyExists) {
                        savingSession = new SavingSession(userInfo, currentTime, studentDao, courseDao, SName);
                        savingSession.saveCurrentSession();
                        saveSesh.cancel();
                    }
                    else{
                        seshName.setError("No duplicate names allowed");
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
                    String idStr = UUID.randomUUID().toString();
                    String name = splitInfo[0]
                            .substring(0, splitInfo[0].length() - 3); // drop ,,,
                    String url = splitInfo[1]
                            .substring(0, splitInfo[1].length() - 3); // drop ,,,
                    StringBuilder courses = new StringBuilder();
                    for (int i = 2; i < splitInfo.length; i++) {
                        courses.append(splitInfo[i]);
                        if (i != splitInfo.length - 1) courses.append(" ");
                    }
                    String fakedMessageStr = idStr + "\n" +
                            name + "\n" +
                            url + "\n" +
                            courses;
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
     * This method shows a list of students who has taken the same course with the app's user, sorted by the
     * number of classes the student shared with the user
     */
    private void refreshStudentList() {
        // get user's taken courses
        List<String> listOfUserCourses = formatUserCourses(dbCourse, userInfo);

        // get list of students
        List<Student> listOfStudents = studentDao.getAll();

        // check whether the students' course match the user's
        // if so, add the user to the students to display
        for (Student student : listOfStudents) {
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

        for (Student student : listOfStudents) {
            if (student.getNumSharedCourses() == 0) {
                listOfStudents.remove(student);
            }
        }

        RecyclerView listOfStudentsView = findViewById(R.id.list_of_students);
        StudentAdapter listOfStudentsViewAdapter = new StudentAdapter(listOfStudents);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listOfStudentsView.setAdapter(listOfStudentsViewAdapter);
            }
        });
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
}