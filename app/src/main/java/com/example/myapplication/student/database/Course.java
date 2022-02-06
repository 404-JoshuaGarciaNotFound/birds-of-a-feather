package com.example.myapplication.student.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "month")
    private String month;

    @ColumnInfo(name = "course_code")
    private String courseCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Course() {
    }

    public Course(int id, String year, String month, String courseCode) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.courseCode = courseCode;
    }

    public boolean equals(Course other) {
        return (this.getId() == other.getId() && this.year.equals(other.year)
                && this.month.equals(other.month) && this.courseCode.equals(other.courseCode));
    }
}
