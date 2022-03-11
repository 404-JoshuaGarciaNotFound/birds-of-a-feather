package com.example.myapplication.student.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students")
    List<Student> getAll();

    @Query("SELECT * FROM students WHERE id=:id")
    Student getStudentByID(String id);

    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT COUNT(*) FROM students")
    int count();

    @Query("DELETE FROM students")
    void clear();

    @Query("UPDATE students SET head_shot_url=:headShotURL, name=:name, courses=:courses, num_shared_courses=:numSharedCourses, wave_received=:waveReceived WHERE id=:id")
    void update(String id, String headShotURL, String name, String courses, int numSharedCourses, boolean waveReceived);

    @Query("UPDATE students set wave_received=:waveReceived WHERE id=:id")
    void setWaveReceived(String id, boolean waveReceived);

    @Query("SELECT EXISTS(SELECT * FROM students WHERE id = :id)")
    boolean isInserted(String id);

}
