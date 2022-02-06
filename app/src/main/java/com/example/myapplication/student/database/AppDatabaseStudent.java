package com.example.myapplication.student.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1)
public abstract class AppDatabaseStudent extends RoomDatabase {
    private static AppDatabaseStudent singletonInstanceStudent;

    public static AppDatabaseStudent singleton (Context context) {
        if  (singletonInstanceStudent == null) {
            singletonInstanceStudent = Room.databaseBuilder(context, AppDatabaseStudent.class, "students.db")
                    .createFromAsset("students.db")
                    .allowMainThreadQueries()
                    .build();
        }

        return singletonInstanceStudent;
    }

    public abstract StudentDao studentDao();
}