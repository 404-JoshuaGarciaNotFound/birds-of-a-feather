package com.example.myapplication;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.Course;

import java.util.ArrayList;
import java.util.List;

public class FormatUsersCourseInfo {
    /**
     * This method is a helper method that format the user courses
     * i.e.
     * the user's courses are stored in a database where each column represents an attribute
     * of that course
     *
     * We want to format each course into a string "year,quarter,course_code"
     *
     * @return a list of the formatted strings
     */
    @NonNull
    public static List<String> formatUserCourses(AppDatabaseCourses DB, SharedPreferences userInfo) {
        String USER_COURSE_ = "user_course_";
        List<String> listOfFormattedCourses = new ArrayList<>();
        try {
            List<Course> listOfUserCourses = DB.courseDao().getAllCourses();

            // for comparison purpose, create a str in format : year,quarter,subject,number
            int count = 0; // used to maintain the key for Shared Preference
            for (Course course :
                    listOfUserCourses) {
                StringBuilder courseStr = new StringBuilder();
                courseStr.append(course.getYear());
                courseStr.append(",");
                courseStr.append(course.getQuarter().toUpperCase());
                courseStr.append(",");
                courseStr.append(course.getCourseCode());
                listOfFormattedCourses.add(courseStr.toString());
                SharedPreferences.Editor userInfoEditor = userInfo.edit();
                userInfoEditor.putString(USER_COURSE_ + count, courseStr.toString());
                userInfoEditor.apply();
                count++;
            }
        }
        catch (Exception e){
            Log.d("failure", String.valueOf(e));
        }
        return listOfFormattedCourses;
    }

}
