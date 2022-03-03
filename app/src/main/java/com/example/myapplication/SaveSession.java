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

public class SaveSession {
    private SharedPreferences userInfo;
    private Date currentTime;
    private StudentDao studentDao;
    private CourseDao courseDao;

    private final String USER_SAVEDSESSIONS= "saved_session";

    SaveSession(SharedPreferences userInfo, Date currentTime, StudentDao studentDao, CourseDao courseDao) {
        this.userInfo = userInfo;
        this.currentTime = currentTime;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    private void saveCurrentSession() {
        SharedPreferences.Editor insertSavedSesh = userInfo.edit();
        String SName = currentTime.toString();
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

        Log.d("testingsessionblah", String.valueOf(session.getSessionContent()));
    }
}
