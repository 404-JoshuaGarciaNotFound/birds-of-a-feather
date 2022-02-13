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

import com.example.myapplication.student.database.AppDatabaseCourses;
import com.example.myapplication.student.database.AppDatabaseStudent;
import com.example.myapplication.student.database.Course;
import com.example.myapplication.student.database.CourseDao;
import com.example.myapplication.student.database.Student;
import com.example.myapplication.student.database.StudentDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentDetailActivity extends AppCompatActivity {

    private AppDatabaseStudent dbStudent;
    private AppDatabaseCourses dbCourse;
    private Student student;
    private StudentDao studentDao;
    private CourseDao courseDao;

    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private CourseViewAdapter coursesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail_info);
        Log.d("student_detail_info", "onCreate: start ");

        dbStudent = AppDatabaseStudent.singleton(this);
        dbCourse = AppDatabaseCourses.singleton(this);
        studentDao = dbStudent.studentDao();
        courseDao = dbCourse.courseDao();

        Intent intent = getIntent();
        int studentId = intent.getIntExtra("student_id", 0);
        dbStudent = AppDatabaseStudent.singleton(this);
        student = dbStudent.studentDao().getStudentByID(studentId);

        List<Course> myCourses = dbCourse.courseDao().getAllCourses();
//        Log.d("myYear", listOfCourse.get(0).getYear());
//        Log.d("myQuarter", listOfCourse.get(0).getQuarter());
//        Log.d("myCourse", listOfCourse.get(0).getCourseCode());
        String otherCourses = student.getCourses();
        Log.d("Student Course", otherCourses);
        List<Course> listOfCourse = findSharedCourse(otherCourses, myCourses);
        Collections.sort(listOfCourse);
//        //Test
//        String testCourse = student.getCourses();
//        Log.d("Student Course", testCourse);
//        String[] arrayOfCourses = testCourse.split(" ");
//        for (String singleCourse : arrayOfCourses) {
//            Log.d("Course One", singleCourse);
//        }

        ImageView studentHeadShot = findViewById(R.id.student_headshot);
        TextView studentName = findViewById(R.id.student_name);
        studentName.setText(student.getName());
        studentHeadShot.setImageBitmap(getBitmapFromURL(student.getHeadShotURL()));

        coursesRecyclerView = findViewById(R.id.course_list);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesViewAdapter = new CourseViewAdapter(listOfCourse);
        coursesRecyclerView.setAdapter(coursesViewAdapter);
    }

    //TODO: Need a function to find out shared course for display
    public List<Course> findSharedCourse(String otherCourses, List<Course> myCourses){
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
        Log.d("Courses", listOfCourse.get(0).getCourseCode());
        return listOfCourse;

    }
}
