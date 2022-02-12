package com.example.myapplication.student.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserInfoDao {
    @Query("SELECT * FROM usersDat")
    public List<UserInfo> getAll();

    @Query("SELECT * FROM usersDat WHERE id=:id")
    public UserInfo getUserByID(int id);

    @Insert
    public void insertUser(UserInfo userInfo);

    @Query("SELECT COUNT(*) FROM usersDat")
    int count();

    @Query("DELETE FROM usersDat")
    public void clear();
}
