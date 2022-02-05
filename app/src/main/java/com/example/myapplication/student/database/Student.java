package com.example.myapplication.student.database;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/*
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Students")
 */

@Database(entities = {Student.class}, version = 1)

public class Student {
/*
    @PrimaryKey
    @ColumnInfo(name = "id"){
        public int studentId;
    }
    @ColumnInfo(name = "name"){
        public String firstName;
    }

 */
}
