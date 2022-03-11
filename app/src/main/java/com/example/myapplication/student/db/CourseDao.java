package com.example.myapplication.student.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Query("UPDATE courses SET year=:year, quarter=:quarter, course_code=:courseCode, course_size=:courseSize WHERE id=:id")
    void update(int id, String year, String quarter, String courseCode, String courseSize);

    @Insert
    void insertCourse(Course course);

    @Query("SELECT COUNT(*) FROM courses")
    int count();

    @Query("DELETE FROM courses")
    void clear();
}
