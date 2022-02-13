package com.example.myapplication.student.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course implements Comparable<Course> {
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

    @Override
    public int compareTo(Course otherCourse) {
        if (this.getYear().compareTo(otherCourse.getYear()) > 0) {
            return -1;
        } else if (this.getYear().compareTo(otherCourse.getYear()) < 0) {
            return 1;
        } else {
            String thisQuarter = this.getQuarter();
            String otherQuarter = otherCourse.getQuarter();

            if (getQuarterInd(thisQuarter) > getQuarterInd(otherQuarter)) {
                return -1;
            } else if (getQuarterInd(thisQuarter) < getQuarterInd(otherQuarter)) {
                return 1;
            } else {
                if (this.getCourseCode().compareTo(otherCourse.getCourseCode()) > 0) {
                    return -1;
                } else if (this.getCourseCode().compareTo(otherCourse.getCourseCode()) < 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public static int getQuarterInd(String quarter) {
        int quarterInd;
        switch (quarter) {
            case "WI":
                quarterInd = 0;
                break;
            case "SP":
                quarterInd = 1;
                break;
            case "SSS":
                quarterInd = 2;
                break;
            case "SS1":
                quarterInd = 3;
                break;
            case "SS2":
                quarterInd = 4;
                break;
            case "FA":
                quarterInd = 5;
                break;
            default:
                quarterInd = -1;
                break;
        }

        return quarterInd;
    }
}