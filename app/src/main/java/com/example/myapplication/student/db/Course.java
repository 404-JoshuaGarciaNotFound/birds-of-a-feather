package com.example.myapplication.student.db;

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

    // "CSE 127" <- expected format of course code
    @ColumnInfo(name = "course_code")
    private String courseCode;

    // course size can be:
    // tiny, small, medium, large, Huge, Gigantic
    // by default, set it to tiny
    @ColumnInfo(name = "course_size")
    private String courseSize = "Tiny";

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

    public String getCourseSize() {
        return courseSize;
    }

    public void setCourseSize(String courseSize) {
        this.courseSize = courseSize;
    }

    public Course() {
    }

    public Course(int id, String year, String quarter, String courseCode, String courseSize) {
        this.id = id;
        this.year = year;
        this.quarter = quarter;
        this.courseCode = courseCode;
        this.courseSize = courseSize;
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
        this.courseSize = other.getCourseSize();
    }

    public boolean equals(Course other) {
        return (this.getId() == other.getId() && this.year.equals(other.year)
                && this.quarter.equals(other.quarter) && this.courseCode.equals(other.courseCode));
    }

    // CompareTo is implemented in order to sort courses by chronological order in detail info
    @Override
    public int compareTo(Course otherCourse) {
        if (this.getYear().compareTo(otherCourse.getYear()) > 0) { // Compare year first
            return -1;
        } else if (this.getYear().compareTo(otherCourse.getYear()) < 0) {
            return 1;
        } else { // If years are the same then compare quarter
            String thisQuarter = this.getQuarter();
            String otherQuarter = otherCourse.getQuarter();

            if (getQuarterInd(thisQuarter) > getQuarterInd(otherQuarter)) {
                return -1;
            } else if (getQuarterInd(thisQuarter) < getQuarterInd(otherQuarter)) {
                return 1;
            } else { // If quarters are the same then compare course code
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

    // This is a helper function to get the integer representation of string quarter. The specific
    // representation is inside switch.
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