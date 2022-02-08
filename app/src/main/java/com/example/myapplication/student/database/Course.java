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

    @ColumnInfo(name = "quarter")
    private String quarter;

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

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Course() {
    }

    public Course(int id, String year, String quarter, String courseCode) {
        this.id = id;
        this.year = year;
        this.quarter = quarter;
        this.courseCode = courseCode;
    }

    public Course(Course other) {
        this.id = other.getId();
        this.year = other.getYear();
        this.quarter = other.getQuarter();
        this.courseCode = other.getCourseCode();
    }

    public boolean equals(Course other) {
        return (this.getId() == other.getId() && this.year.equals(other.year)
                && this.quarter.equals(other.quarter) && this.courseCode.equals(other.courseCode));
    }
}