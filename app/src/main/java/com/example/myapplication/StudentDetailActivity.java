package com.example.myapplication;

import static com.example.myapplication.ImageLoadTask.getBitmapFromURL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;
import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail_info);
        Log.d("student_detail_info", "onCreate: start ");

        //Set up database
        dbStudent = AppDatabaseStudent.singleton(this);
        dbCourse = AppDatabaseCourses.singleton(this);
        studentDao = dbStudent.studentDao();
        courseDao = dbCourse.courseDao();

        Intent intent = getIntent();
        String studentId = intent.getStringExtra("student_id");
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

        //Set CourseViewAdapter and bind to recylerView
        coursesRecyclerView = findViewById(R.id.course_list);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesViewAdapter = new CourseViewAdapter(listOfCourse);
        coursesRecyclerView.setAdapter(coursesViewAdapter);
    }

    //Function to find sharedCourse
    //@param CourseList from both students
    public static List<Course> findSharedCourse(String otherCourses, List<Course> myCourses){
        String[] arrayofCourses = otherCourses.split(" ");
        List<Course> listOfCourse = new ArrayList<>();
        for (Course mySingleCourse : myCourses){
            for (String otherSingleCourse : arrayofCourses){
                String[] otherCourseInfo = otherSingleCourse.split(",");
                String otherYear = otherCourseInfo[0];
                String otherQuarter = otherCourseInfo[1];
                String otherCourse = otherCourseInfo[2] + "," + otherCourseInfo[3];
                if (mySingleCourse.getYear().equals(otherYear) &&
                        mySingleCourse.getQuarter().equals(otherQuarter) &&
                        mySingleCourse.getCourseCode().equals(otherCourse)){
                    listOfCourse.add(mySingleCourse);
                }
            }
        }
        return listOfCourse;

    }
}
