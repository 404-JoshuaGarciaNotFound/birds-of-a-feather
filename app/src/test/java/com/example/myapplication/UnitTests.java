package com.example.myapplication;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class UnitTests {
    //First test for single course
    private boolean CourseArrayEquals(Course[] c1, Course[] c2) {
        if (c1.length != c2.length)
            return false;

        for (int i = 0; i < c1.length; i++) {
            if (!c1[i].equals(c2[i]))
                return false;
        }
        return true;
    }

    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    public AppDatabaseCourses dbCourse;


    @Before
    public void setUp() {
        dbCourse = AppDatabaseCourses.singleton(getApplicationContext());
    }
    @After
    public void resetTest(){
        dbCourse.close();
    }
    @Test
    public void test_addOneClass() {
        CourseDao courseDao = dbCourse.courseDao();
        courseDao.insertCourse(
                new Course(
                        1,
                        String.valueOf("2020"),
                        String.valueOf("FA"),
                        "110"
                ));

        // TypeCast crucial
        Course actual = (Course) courseDao.getAllCourses().toArray()[0];
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {

            Course expected = new Course(
                    1,
                    String.valueOf("2020"),
                    String.valueOf("FA"),
                    "110"
            );
            assertTrue(actual.equals(expected));
        });
        courseDao.clear();
    }

    private CourseDao courseDaoa;
    public AppDatabaseCourses dbCourses;
    @Before
    //Note this method (restart) was obtained from the android developer page
    //https://developer.android.com/training/data-storage/room/testing-db
    public void restart(){
        Context context = ApplicationProvider.getApplicationContext();
        dbCourses = Room.inMemoryDatabaseBuilder(context, AppDatabaseCourses.class).allowMainThreadQueries().build();
        courseDaoa = dbCourses.courseDao();

    }
    @Test
    public void test_addMultipleClass() {

        Course[] courses = {
                new Course(
                        1,
                        String.valueOf("2020"),
                        String.valueOf("FA"),
                        "CSE 110"
                ),
                new Course(
                        2,
                        String.valueOf("2021"),
                        String.valueOf("WI"),
                        "CSE 101"
                ),
                new Course(
                        3,
                        String.valueOf("2022"),
                        String.valueOf("SP"),
                        "ECE 109"
                ),
                new Course(
                        4,
                        String.valueOf("2020"),
                        String.valueOf("FA"),
                        "CSE 100"
                )
        };

        for (Course c : courses) {
            courseDaoa.insertCourse(new Course(c)); // creates a copies
        }
        // TypeCast and manual array population crucial
        Course[] actual = new Course[courses.length];

        for (int i = 0; i < courses.length; i++) {
            actual[i] = (Course) courseDaoa.getAllCourses().toArray()[i];
        }

        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            assertTrue(CourseArrayEquals(actual, courses));
        });

    }
}