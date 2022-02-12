package com.example.myapplication.student.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "usersDat")
public class UserInfo {
        @PrimaryKey
        @ColumnInfo(name = "id")
        private int id;

        @ColumnInfo(name = "head_shot_url")
        private String headShotURL;

        @ColumnInfo(name = "name")
        private String name;

        public UserInfo(int id, String headShotURL, String name) {
            this.id = id;
            this.headShotURL = headShotURL;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHeadShotURL() {
            return headShotURL;
        }

        public void setHeadShotURL(String headShotURL) {
            this.headShotURL = headShotURL;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

}
