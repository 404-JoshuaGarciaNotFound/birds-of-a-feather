package com.example.myapplication;


import static com.example.myapplication.CreateBuilderAlert.buildBuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SessionDetailActivity extends AppCompatActivity {
    private RecyclerView RV;
    private RecyclerView.LayoutManager RVLM;
    private SessionAdapter SA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String key = (String) extras.get("name");
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        Set<String> vals = userInfo.getStringSet(key, null);
        //This creates an alert builder
        setContentView(R.layout.activity_liststudentsfrom_session);
        CreateBuilderAlert.returningVals ADcn = buildBuilder(this, R.layout.change_session_name,
                getLayoutInflater(), true, "New Name");
        AlertDialog renameSession = ADcn.alertDiag;
        TextView tv = findViewById(R.id.seshNamey);
        tv.setText(key);

        //this method is to change name of session
        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.changeName);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameSession.show();
                Button button = (Button) renameSession.findViewById(R.id.sabeButtonforRename);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText newName = (EditText) renameSession.findViewById(R.id.newNameBox);
                        String reNamed = newName.getText().toString();
                        Set<String> keys = userInfo.getStringSet("saved_session", null);
                        boolean alreadyExist = keys.contains(reNamed);
                        if(!alreadyExist){
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
                            tv.setText(reNamed);
                            renameSession.cancel();
                            Log.d("Renamed", String.valueOf(userInfo.getStringSet(reNamed, null)));
                        }
                        else{
                            newName.setError("No duplicate names allowed!");
                        }
                    }
                });

            }
        });

        //Here we regenerate our list of students
        AppDatabaseStudent dbStudent2 = AppDatabaseStudent.singleton(this);
        dbStudent2.studentDao().clear();
        ArrayList<String> Convert = new ArrayList<String>(vals);
        for(int i = 0; i < Convert.size(); i++){
            String fullWord = Convert.get(i);
            String[] words = fullWord.split(" ");
            Log.d("lengthOfCals", String.valueOf(fullWord));
            String studentCourseList = "";
            String studentId = words[0];
            String StudentName = words[1];
            String StudentURL = words[2];
            String numCourses = words[3];
            for(int b = 3; b < words.length; b++){
                studentCourseList = (studentCourseList + " " + words[b]);
            }
            studentCourseList = studentCourseList.trim();
            Student student = new Student(studentId, StudentURL, StudentName, studentCourseList, Integer.parseInt(numCourses));
            dbStudent2.studentDao().insertStudent(student);
        }
       // Log.d("Values", String.valueOf(Convert.get(0)));
        //Now dbStudent has all of the students parsed
        RecyclerView RV = (RecyclerView) findViewById(R.id.ListOfStudentsFromSession);
        RecyclerView.LayoutManager RVLM = new LinearLayoutManager(this);
        RV.setLayoutManager(RVLM);
        List<Student> LOS = dbStudent2.studentDao().getAll();
        LOS.sort(Comparator.comparing(Student::getNumSharedCourses).reversed());
        StudentAdapter SA = new StudentAdapter(userInfo, LOS);
        RV.setAdapter(SA);
        FloatingActionButton FABreturn = (FloatingActionButton) findViewById(R.id.ReturnHomey);
        //Finally we have a intent to return to home screen
        FABreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });



    }

}
