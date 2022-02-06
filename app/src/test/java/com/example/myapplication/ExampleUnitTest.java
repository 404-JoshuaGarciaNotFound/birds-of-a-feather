package com.example.myapplication;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.myapplication.student.database.AppDatabaseCourses;
import com.example.myapplication.student.database.Course;
import com.example.myapplication.student.database.CourseDao;

import java.util.ArrayList;
import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private AppDatabaseCourses dbCourse;

    @Before
    public void setUp() {
        dbCourse = AppDatabaseCourses.singleton(getApplicationContext());
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_addClasses() {
        CourseDao courseDao = dbCourse.courseDao();
        courseDao.insertCourse(
                new Course(
                        1,
                        String.valueOf("2020"),
                        String.valueOf("FA"),
                        "110"
                ));

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
    }
}
