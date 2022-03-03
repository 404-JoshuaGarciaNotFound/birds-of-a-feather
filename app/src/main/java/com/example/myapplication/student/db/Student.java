package com.example.myapplication.student.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Set;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "head_shot_url")
    private String headShotURL;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "courses")
    private String courses;

    @ColumnInfo(name = "num_shared_courses")
    private int numSharedCourses;

    public Student() {
    }

    public Student(String id, String headShotURL, String name, String courses, int numSharedCourses) {
        this.id = id;
        this.headShotURL = headShotURL;
        this.name = name;
        this.courses = courses;
        this.numSharedCourses = numSharedCourses;
    }

    public String getId() {
    /**
     * Getters and setters
     */

    public int getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getNumSharedCourses() {
        return numSharedCourses;
    }

    public void setNumSharedCourses(int numSharedCourses) {
        this.numSharedCourses = numSharedCourses;
    }
}
