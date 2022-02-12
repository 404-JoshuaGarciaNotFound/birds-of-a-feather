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
        List<Course> listOfCourse = dbCourse.courseDao().getAllCourses();

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
}
