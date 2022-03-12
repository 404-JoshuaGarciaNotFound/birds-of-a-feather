package com.example.myapplication;

import static com.example.myapplication.ArrangeStudentList.arrangeStudentList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;
import com.google.android.gms.nearby.Nearby;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SessionScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.listsessions);
        super.onCreate(savedInstanceState);
        //This is the button that allows to go back to home screen
        FloatingActionButton returnBack = findViewById(R.id.returnToHome);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Bundle extras = getIntent().getExtras();
        ArrayList<String> keys = extras.getStringArrayList("ListStr");
        Log.d("Extras", keys.get(0));
        RecyclerView rv = (RecyclerView) findViewById(R.id.List_Of_Sessions);
        RecyclerView.LayoutManager RVLM = new LinearLayoutManager(this);
        rv.setLayoutManager(RVLM);

        SessionAdapter SA = new SessionAdapter(keys);

        rv.setAdapter(SA);
        rv.setVisibility(View.VISIBLE);

    }
}
