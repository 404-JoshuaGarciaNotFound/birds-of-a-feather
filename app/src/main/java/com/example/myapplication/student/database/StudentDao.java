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

    @Insert
    public void insertStudent(Student student);
}
