package com.example.myapplication.student.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Insert
    void insertCourse(Course course);

    @Query("SELECT COUNT(*) FROM courses")
    int count();

    @Query("DELETE FROM courses")
    void clear();
}
