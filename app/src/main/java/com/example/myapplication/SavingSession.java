package com.example.myapplication;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;
import com.example.myapplication.student.db.StudentDao;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SavingSession {
    private SharedPreferences userInfo;
    private Date currentTime;
    private StudentDao studentDao;
    private CourseDao courseDao;
    private String SName;

    private final String USER_SAVEDSESSIONS= "saved_session";

    SavingSession(SharedPreferences userInfo, Date currentTime, StudentDao studentDao, CourseDao courseDao, String SName) {
        this.userInfo = userInfo;
        this.currentTime = currentTime;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        if(SName == "" || SName == null)
            this.SName = currentTime.toString();
        else
            this.SName = SName;
    }

    public void saveCurrentSession() {
        SharedPreferences.Editor insertSavedSesh = userInfo.edit();
        Set<String> strings = userInfo.getStringSet(USER_SAVEDSESSIONS, null);
        if(strings == null) {
            strings = new HashSet<>(Arrays.asList(SName));
        }

        strings.add(SName);
        insertSavedSesh.putStringSet(USER_SAVEDSESSIONS, strings);
        insertSavedSesh.apply();
        Log.d("ListOfSessions", String.valueOf(strings));
        // creating session here
        Session session = new Session(SName);

        session.populateSessionContentWithSameCourse(studentDao, courseDao);
        session.saveSession(userInfo);
    }
}
