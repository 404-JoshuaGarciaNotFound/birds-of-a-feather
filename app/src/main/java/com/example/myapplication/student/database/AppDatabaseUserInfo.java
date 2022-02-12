package com.example.myapplication.student.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserInfo.class}, version = 1)
public abstract class AppDatabaseUserInfo extends RoomDatabase {
    private static AppDatabaseUserInfo singletonInstanceUserDat;

    public static AppDatabaseUserInfo singleton (Context context) {
        if  (singletonInstanceUserDat== null) {
            singletonInstanceUserDat = Room.databaseBuilder(context, AppDatabaseUserInfo.class, "UserData.db")
                    .createFromAsset("UserData.db")
                    .allowMainThreadQueries()
                    .build();
        }

        return singletonInstanceUserDat;
    }

    public abstract UserInfoDao userInfoDao();
}
