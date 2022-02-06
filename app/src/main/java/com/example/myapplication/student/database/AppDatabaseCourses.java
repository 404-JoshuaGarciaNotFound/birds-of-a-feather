package com.example.myapplication.student.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 1)
public abstract class AppDatabaseCourses extends RoomDatabase {
    private static AppDatabaseCourses singletonInstanceCourse;

    public static AppDatabaseCourses singleton (Context context) {
        if  (singletonInstanceCourse == null) {
            singletonInstanceCourse = Room.databaseBuilder(context, AppDatabaseCourses.class, "courses.db")
                    .createFromAsset("courses.db")
                    .allowMainThreadQueries()
                    .build();
        }

        return singletonInstanceCourse;
    }

    public abstract CourseDao courseDao();
}
