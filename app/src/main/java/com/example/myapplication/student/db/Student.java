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

    @ColumnInfo(name = "wave_received")
    private boolean waveReceived;

    public Student() {
    }

    public Student(String id, String headShotURL, String name, String courses, int numSharedCourses) {
        this.id = id;
        this.headShotURL = headShotURL;
        this.name = name;
        this.courses = courses;
        this.numSharedCourses = numSharedCourses;
        this.waveReceived = false;
    }

    public Student(String id, String headShotURL, String name, String courses, int numSharedCourses, boolean waveReceived){
        this.id = id;
        this.headShotURL = headShotURL;
        this.name = name;
        this.courses = courses;
        this.numSharedCourses = numSharedCourses;
        this.waveReceived = true;
    }

    public String getId() {
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

    public void setWaveReceived(boolean waveReceived) {
        this.waveReceived = waveReceived;
    }

    public boolean isWaveReceived() {
        return waveReceived;
    }
}
