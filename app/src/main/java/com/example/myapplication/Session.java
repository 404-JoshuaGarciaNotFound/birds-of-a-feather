package com.example.myapplication;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class manage sessions
 * Each session is a set of string.
 * Each session match with a string key.
 * Each session is stored in sharedPreference
 */
public class Session {

    private String sessionName;
    private String sessionCourse;
    private Set<String> sessionContent;
    private final String TAG = this.getClass().getName();

    public Session(String sessionName, String sessionCourse) {
        Log.d(TAG, "creating session: " + sessionName + " with course: " + sessionCourse);
        this.sessionName = sessionName;
        this.sessionCourse = sessionCourse;
        this.sessionContent = new HashSet<>();
    }

    /**
     * Below are getters and setters
     */
    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Set<String> getSessionContent() {
        return sessionContent;
    }

    public void setSessionContent(Set<String> sessionContent) {
        this.sessionContent = sessionContent;
    }

    /**
     * This method populate the session's content
     * the content is made by sequences of student info
     * each sequence (string) is in the form: name url number-of-shared-course course1 course2
     */
    public void populateSessionContent(StudentDao studentDao) {
        List<Student> allStudents = studentDao.getAll();
        Set<String> content = new HashSet<>();
        for (Student student :
             allStudents) {
            if (student.getCourses().contains(this.sessionCourse)) {
                String sequence = student.getName() + " "
                        + student.getHeadShotURL() + " "
                        + student.getNumSharedCourses() + " "
                        + student.getCourses();
                content.add(sequence);
            }
        }
    }

    /**
     * This method save the session to sharedPreference
     * @param sharedPreferences
     */
    public void saveSession(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(this.sessionName, this.sessionContent);
        editor.apply();
    }
}
