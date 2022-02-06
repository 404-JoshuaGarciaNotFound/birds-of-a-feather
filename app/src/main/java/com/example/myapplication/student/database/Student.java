package com.example.myapplication.student.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "head_shot_url")
    private String headShotURL;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "courses")
    private String courses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadShotURL() {
        return headShotURL;
    }

    public void setHeadShotURL(String headShotURL) {
        this.headShotURL = headShotURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public Student() {
    }

    public Student(int id, String headShotURL, String name, String courses) {
        this.id = id;
        this.headShotURL = headShotURL;
        this.name = name;
        this.courses = courses;
    }
}
