package com.example.myapplication.student.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courses")
    public List<Course> getAllCourses();

    @Insert
    public void insertCourse(Course course);
}
