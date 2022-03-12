package com.example.myapplication;

import static com.example.myapplication.ArrangeStudentList.arrangeStudentList;
import static com.example.myapplication.FormatUsersCourseInfo.formatUserCourses;
import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;
import static com.example.myapplication.MainActivity.returnSP;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;
import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

public class StudentDetailActivity extends AppCompatActivity {

    //Database variables
    private AppDatabaseStudent dbStudent;
    private AppDatabaseCourses dbCourse;
    private Student student;
    private StudentDao studentDao;
    private CourseDao courseDao;

    //RecylerView Variables
    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CourseViewAdapter coursesViewAdapter;

    //SharePreference
    private SharedPreferences userInfo;

    //ImageButton
    private ImageButton waveButton;

    //Message to send when waving
    private Message waveMessage;

    //Student Info
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail_info);
        Log.d("student_detail_info", "onCreate: start ");
        waveMessage = null;

        //Set up database
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);

        dbStudent = AppDatabaseStudent.singleton(this);
        dbCourse = AppDatabaseCourses.singleton(this);
        studentDao = dbStudent.studentDao();
        courseDao = dbCourse.courseDao();

        Intent intent = getIntent();
        studentId = intent.getStringExtra("student_id");
        dbStudent = AppDatabaseStudent.singleton(this);
        student = dbStudent.studentDao().getStudentByID(studentId);

        //Get Course list from both students and find shared course
        List<Course> myCourses = dbCourse.courseDao().getAllCourses();
        String otherCourses = student.getCourses();
        Log.d("Student Course", otherCourses);

        List<Course> listOfCourse = findSharedCourse(otherCourses, myCourses);
        //Sort chronologically
        Collections.sort(listOfCourse);

        //Set activity page
        ImageView studentHeadShot = findViewById(R.id.student_headshot);
        TextView studentName = findViewById(R.id.student_name);
        studentName.setText(student.getName());
        studentHeadShot.setImageBitmap(getBitmapFromURL(student.getHeadShotURL()));
        waveButton = findViewById(R.id.sendWaveButton);
        waveButton.setImageDrawable(getDrawable(R.drawable.ic_wave_empty_hand));

        //Set CourseViewAdapter and bind to recylerView
        coursesRecyclerView = findViewById(R.id.course_list);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesViewAdapter = new CourseViewAdapter(listOfCourse);
        coursesRecyclerView.setAdapter(coursesViewAdapter);

        //This method updates the star shape if a student is a favorite.
        ImageButton favoritesStar = findViewById(R.id.favoriteStarStudentDetails);
        Set<String> favoritesList = userInfo.getStringSet("favorites", null);

        Log.d("Info", student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
        if (favoritesList != null) {
            if (favoritesList.size() != 0) {
                if (favoritesList.contains(student.getId() + " " + student.getName() + " " + student.getHeadShotURL() )) {
                    favoritesStar.setBackgroundResource(R.drawable.ic_star);
                    favoritesStar.setTag("ON");
                }
            }
        }
    }

    //Function to find sharedCourse
    //@param CourseList from both students
    @NonNull
    public static List<Course> findSharedCourse(String otherCourses, List<Course> myCourses){
        String[] arrayofCourses = otherCourses.split(" ");
        List<Course> listOfCourse = new ArrayList<>();
        for (Course mySingleCourse : myCourses) {
            for (String otherSingleCourse : arrayofCourses) {
                if(otherSingleCourse.length() != 1) {
                    String[] otherCourseInfo = otherSingleCourse.split(",");
                    String otherYear = otherCourseInfo[0];
                    String otherQuarter = otherCourseInfo[1];
                    String otherCourse = otherCourseInfo[2] + "," + otherCourseInfo[3];
                    if (mySingleCourse.getYear().equals(otherYear) &&
                            mySingleCourse.getQuarter().equals(otherQuarter) &&
                            mySingleCourse.getCourseCode().equals(otherCourse)) {
                        listOfCourse.add(mySingleCourse);
                    }
                }
            }
        }
        return listOfCourse;

    }
    public void FavoriteSomeone(View view){
        ImageButton favoritesStar = view.findViewById(R.id.favoriteStarStudentDetails);
        Set<String> favoritesList = userInfo.getStringSet("favorites", null);
        Log.d("list", String.valueOf(favoritesList));
        SharedPreferences.Editor insertStudentFav =  userInfo.edit();
        Log.d("ACTION", String.valueOf(favoritesStar.getTag()));

        if(favoritesList == null || favoritesList.size() == 0){
            Log.d("Empty List", "No items");
            Set<String> newSet = new HashSet<String>();
            Toast.makeText(favoritesStar.getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
            newSet.add(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
            insertStudentFav.putStringSet("favorites", newSet);
            insertStudentFav.apply();
            favoritesStar.setTag("ON");
            favoritesStar.setBackgroundResource(R.drawable.ic_star);
        }
        else{
            if(favoritesStar.getTag() != null) {
                if (favoritesStar.getTag().equals("ON")) {
                    favoritesStar.setBackgroundResource(R.drawable.ic_star_empty);
                    favoritesStar.setTag("OFF");
                    //Remove from favorites
                    Log.d("b4", String.valueOf(favoritesList));
                    favoritesList.remove(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                    insertStudentFav.remove("favorites");
                    insertStudentFav.apply();
                    insertStudentFav.putStringSet("favorites", favoritesList);
                    insertStudentFav.apply();
                    Log.d("after", String.valueOf(favoritesList));
                    Toast.makeText(favoritesStar.getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                }
                else {
                    favoritesStar.setBackgroundResource(R.drawable.ic_star);
                    favoritesStar.setTag("ON");
                    Toast.makeText(favoritesStar.getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                    favoritesList.remove(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                    insertStudentFav.remove("favorites");
                    insertStudentFav.apply();
                    insertStudentFav.putStringSet("favorites", favoritesList);
                    insertStudentFav.apply();
                    favoritesList.add(student.getId() + " " + student.getName() + " " + student.getHeadShotURL());
                    insertStudentFav.remove("favorites");
                    insertStudentFav.putStringSet("favorites", favoritesList);
                    insertStudentFav.apply();
                    Log.d("inserted", String.valueOf(favoritesList));

                }


            }else{
                favoritesStar.setBackgroundResource(R.drawable.ic_star);
                favoritesStar.setTag("ON");
                Log.d("prev", String.valueOf(favoritesList));

            }
        }
    }

    // onClick function for sending wave
    public void sendWave(View view){
        // initialize the shared preference that store user info
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        //Generate wave message
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
        String otherId = student.getId();
        String myWave = myId + "\n" +
                myName + "\n" +
                myHeadShot + "\n" +
                myCourses + "\n" +
                otherId + ",wave";
        waveMessage = new Message(myWave.getBytes());
        Nearby.getMessagesClient(this).publish(waveMessage);
       // Nearby.getMessagesClient(this).unpublish(waveMessage);

        //Change icon
        waveButton.setImageDrawable(getDrawable(R.drawable.ic_wave_hand));

        //Check waveMessage
        Log.d("Wave Message", new String(waveMessage.getContent()));

        //Display toast
        Toast.makeText(this, "Wave send", Toast.LENGTH_SHORT).show();

    }
    public void refresymemeplz(View view){


    }

    // onClick function for backButton
    public void goBack(View view){
        //Change waveReceived to false

        dbStudent.studentDao().setWaveReceived(studentId, false);
        if (waveMessage != null){
          //  Nearby.getMessagesClient(this).unpublish(waveMessage);
        }
        finish();

        Log.d("CurV", String.valueOf(view.getId()));
    }
}
