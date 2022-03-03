package com.example.myapplication;

import android.util.Log;

import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Student;

public class AddStudent {
    /**
     * This method is the click event for the enter button.
     * It will take in a string of format:
     * {name},,,
     * {url},,,
     * 2021,FA,CSE,210
     * 2022,WI,CSE,110
     * 2022,SP,CSE,110
     * , populate the mock students (the students who is, in the future, detected by the bluetooth)
     * information with the given name, url, course1, course2,...
     * @param mockUserInfo the input string consisting of the mock user's info
     */
    public static void addStudent(AppDatabaseStudent DBS, String mockUserInfo) {
        String[] splitInfo = mockUserInfo.split("\n");
        int id = DBS.studentDao().count() + 1;
        String name = splitInfo[0]
                .substring(0, splitInfo[0].length() - 3); // drop ,,,
        String url = splitInfo[1]
                .substring(0, splitInfo[1].length() - 3); // drop ,,,
        StringBuilder courses = new StringBuilder();
        for (int i = 2; i < splitInfo.length; i++) {
            courses.append(splitInfo[i]);
            if (i != splitInfo.length - 1) courses.append(" ");
        }
        Log.d("courses", courses.toString());

        Student toAddStudent = new Student(id, url, name, courses.toString(), 0);

        // add the student to the database
        DBS.studentDao().insertStudent(toAddStudent);
    }
}
