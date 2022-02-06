package com.example.myapplication.student.database;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students")
    public List<Student> getAll();

    @Query("SELECT * FROM students WHERE id=:id")
    public Student getStudentByID(int id);

    @Insert
    public void insertStudent(Student student);

    @Query("SELECT COUNT(*) FROM students")
    int count();

    @Query("DELETE FROM students")
    public void clear();
}
