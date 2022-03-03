package com.example.myapplication.student.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students")
    List<Student> getAll();

    @Query("SELECT * FROM students WHERE id=:id")
    Student getStudentByID(int id);

    @Insert
    void insertStudent(Student student);

    @Query("SELECT COUNT(*) FROM students")
    int count();

    @Query("DELETE FROM students")
    void clear();
}
