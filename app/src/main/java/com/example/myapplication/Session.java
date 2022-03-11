package com.example.myapplication;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;
import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class manage sessions
 * Each session is a set of string.
 * Each session match with a string key.
 * Each session is stored in sharedPreference
 */
public class Session {

    private final String USER_SAVED_SESSIONS = "saved_session";

    private String sessionName;
    private Set<String> sessionContent;
    private final String TAG = this.getClass().getName();

    public Session(String sessionName) {
        Log.d(TAG, "creating session: " + sessionName);
        this.sessionName = sessionName;
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
     * This method populate the session's content with students who share the same courses
     * the content is made by sequences of student info
     * each sequence (string) is in the form: name url number-of-shared-course course1 course2
     */
    public void populateSessionContentWithSameCourse(
            @NonNull StudentDao studentDao, @NonNull CourseDao courseDao) {
        List<Student> allStudents = studentDao.getAll();
        List<Course> allCourses = courseDao.getAllCourses();
        for (Student student : allStudents) {
            for (Course course : allCourses) {
                if (student.getCourses().contains(course.getCourseCode())) {
                    String sequence = student.getId() + " "
                            + student.getName() + " "
                            + student.getHeadShotURL() + " "
                            + student.getNumSharedCourses() + " "
                            + student.getCourses() + " ";
                    sessionContent.add(sequence);
                    break;
                }
            }
        }
    }

    /**
     * This method populate the session's content
     * the content is made by sequences of student info
     * each sequence (string) is in the form: name url number-of-shared-course course1 course2
     */
    public void populateSessionContent(StudentDao studentDao) {
        List<Student> allStudents = studentDao.getAll();
        for (int i = 0; i < allStudents.size(); i++) {
            Student student = allStudents.get(i);
            String sequence = student.getName() + " "
                    + student.getHeadShotURL() + " "
                    + student.getNumSharedCourses() + " "
                    + student.getCourses();
            sessionContent.add(sequence);
        }
    }

    /**
     * This method convert the content set to a list sorted by number of shared courses
     * @return
     */
    public List<String> getListSortedByNumSharedCourses() {
        List<String> output = new LinkedList<>();
        for (String sequence:
             this.sessionContent) {
            output.add(sequence);
        }

        output.sort((s1, s2) -> {
            String[] s1Split = s1.split(" ");
            String[] s2Split = s2.split(" ");
            Integer numSharedCourseS1 = Integer.valueOf(s1Split[2]);
            Integer numSharedCourseS2 = Integer.valueOf(s2Split[2]);
            return numSharedCourseS1.compareTo(numSharedCourseS2);
        });

        return output;
    }

    /**
     * This method save the session to sharedPreference
     * @param sharedPreferences
     */
    public void saveSession(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(this.sessionName, this.sessionContent);

        Set<String> sessionSet = sharedPreferences.getStringSet(USER_SAVED_SESSIONS, null);
        if (sessionSet == null) {
            sessionSet = new HashSet<>();
        }
        sessionSet.add(this.sessionName);
        editor.putStringSet(USER_SAVED_SESSIONS, sessionSet);
        editor.apply();
    }
}
