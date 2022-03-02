package com.example.myapplication;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.CourseDao;
import com.example.myapplication.student.db.Student;
import com.example.myapplication.student.db.StudentDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


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
    public AppDatabaseStudent dbStudent;

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
    @After
    public void resetTest2(){
        dbCourse.close();
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

    private StudentDao studentDaoa;

    @Before
    //Note this method (restart) was obtained from the android developer page
    //https://developer.android.com/training/data-storage/room/testing-db
    public void Restart(){
        Context context = ApplicationProvider.getApplicationContext();
        dbStudent = Room.inMemoryDatabaseBuilder(context, AppDatabaseStudent.class).allowMainThreadQueries().build();
        studentDaoa = dbStudent.studentDao();
    }
    @After
    public void ResetTest2(){
        dbCourse.close();
    }
    //    public Student(int id, String headShotURL, String name, String courses, int numSharedCourses) {
    //This test tests our student database
    @Test
    public void TestAppDatabaseStudent(){

        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 1);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 2);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);

        //First we check if the correct number of students have been added
        assertEquals(2, studentDaoa.count());
        //Next we test if the correct student information was saved for each student
        assertEquals("John smith", studentDaoa.getStudentByID(0).getName());
        assertEquals("2022,FA,CSE,127", studentDaoa.getStudentByID(0).getCourses());
        assertEquals("www.headshoturlLink1.com", studentDaoa.getStudentByID(0).getHeadShotURL());
        assertEquals(1, studentDaoa.getStudentByID(0).getNumSharedCourses());
        //Student 2
        assertEquals("Jane Doe", studentDaoa.getStudentByID(1).getName());
        assertEquals("2022,FA,CSE,127 2022,WI,CSE,110", studentDaoa.getStudentByID(1).getCourses());
        assertEquals("www.headshoturlLink2.com", studentDaoa.getStudentByID(1).getHeadShotURL());
        assertEquals(2, studentDaoa.getStudentByID(1).getNumSharedCourses());
        //Finally we test if our clear function works
        studentDaoa.clear();
        assertEquals(0, studentDaoa.count());
    }
    //Should add a url test to make sure bitmap function works
    //Should add a test on making sure the list is in the correct order.

    @Test
    public void TestFindSharedCoursesEmpty(){
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,128", 0);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 0);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);

        String otherCourses = "2022,FA,CSE,127 2022,WI,CSE,110";
        ArrayList<Course> courseList = new ArrayList<>();
        Course course = new Course(128, "2022", "FA", "CSE 128");
        courseList.add(course);
        List<Course> sharedCourses = StudentDetailActivity.findSharedCourse(otherCourses,courseList);

        assertEquals(sharedCourses.size(),0);
    }

    @Test
    public void TestFindOneSharedCourse(){
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 0);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 0);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);

        String otherCourses = "2022,FA,CSE,127 2022,WI,CSE,110";
        ArrayList<Course> courseList = new ArrayList<>();
        Course course = new Course(127, "2022", "FA", "CSE,127");
        courseList.add(course);
        List<Course> sharedCourses = StudentDetailActivity.findSharedCourse(otherCourses,courseList);

        assertEquals(sharedCourses.size(),1);
    }

    @Test
    public void TestFindSharedCourseName(){
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 0);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 0);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);

        String otherCourses = "2022,FA,CSE,127 2022,WI,CSE,110";
        ArrayList<Course> courseList = new ArrayList<>();
        Course course = new Course(127, "2022", "FA", "CSE,127");
        courseList.add(course);
        List<Course> sharedCourses = StudentDetailActivity.findSharedCourse(otherCourses,courseList);

        Course retrievedCourse = sharedCourses.get(0);

        assertEquals(retrievedCourse.getId(),127);
        assertTrue(retrievedCourse.getYear().equals("2022"));
        assertTrue(retrievedCourse.getQuarter().equals("FA"));
        assertTrue(retrievedCourse.getCourseCode().equals("CSE,127"));
    }

    @Test
    public void TestFindMultiSharedCourse(){
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 0);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 0);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);

        String otherCourses = "2022,FA,CSE,127 2022,WI,CSE,110";
        ArrayList<Course> courseList = new ArrayList<>();
        Course course0 = new Course(127, "2022", "FA", "CSE,127");
        Course course1 = new Course(110, "2022", "WI", "CSE,110");
        courseList.add(course0);
        courseList.add(course1);
        List<Course> sharedCourses = StudentDetailActivity.findSharedCourse(otherCourses,courseList);

        assertEquals(sharedCourses.size(),2);
    }

    @Test
    public void TestFindMultiSharedCourseName(){
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 0);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 0);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);

        String otherCourses = "2022,FA,CSE,127 2022,WI,CSE,110";
        ArrayList<Course> courseList = new ArrayList<>();
        Course course0 = new Course(127, "2022", "FA", "CSE,127");
        Course course1 = new Course(110, "2022", "WI", "CSE,110");
        courseList.add(course0);
        courseList.add(course1);
        List<Course> sharedCourses = StudentDetailActivity.findSharedCourse(otherCourses,courseList);

        Course retrievedCourse0 = sharedCourses.get(0);
        Course retrievedCourse1 = sharedCourses.get(1);

        assertEquals(retrievedCourse0.getId(),127);
        assertEquals("2022", retrievedCourse0.getYear());
        assertEquals("FA", retrievedCourse0.getQuarter());
        assertEquals("CSE,127", retrievedCourse0.getCourseCode());

        assertEquals(retrievedCourse1.getId(),110);
        assertEquals("2022", retrievedCourse1.getYear());
        assertEquals("WI", retrievedCourse1.getQuarter());
        assertEquals("CSE,110", retrievedCourse1.getCourseCode());
    }
    //Test save single session

    @Test
    public void testSingleSavedSession(){
        /**
         * Work in progress
         */
        SharedPreferences sp;
        Context context = getApplicationContext();
        sp = context.getSharedPreferences("userInfo", 0);
        Session session = new Session("CSE 110");
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 1);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 0);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);

        session.populateSessionContent(studentDaoa);
        session.saveSession(sp);
        Set<String> set = sp.getStringSet("CSE 110", null);
        Iterator<String> it = set.iterator();
        String student1 = it.next();
        String student2 = it.next();
        String expected1 = "John smith www.headshoturlLink1.com 1 2022,FA,CSE,127";
        String expected2 = "Jane Doe www.headshoturlLink2.com 0 2022,FA,CSE,127 2022,WI,CSE,110";
        assertEquals(expected1, student1);
        assertEquals(expected2, student2);
    }

    //Test save multiple session
    @Test
    public void testMultipleSavedSession(){
        SharedPreferences sp;
        Context context = getApplicationContext();
        sp = context.getSharedPreferences("userInfo", 0);
        Session session = new Session("CSE 110");
        Session session2 = new Session("CSE 127");
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 1);
        Student secondStudent = new Student(1, "www.headshoturlLink2.com", "Jane Doe", "2022,FA,CSE,127 2022,WI,CSE,110", 0);
        Student thirdStudent = new Student(2, "www.headshoturlLink3.com", "Jason Foo", "2021,WI,CSE,130 2022,WI,CSE,120", 3);
        studentDaoa.insertStudent(sampleStudent);
        studentDaoa.insertStudent(secondStudent);
        session.populateSessionContent(studentDaoa);
        studentDaoa.insertStudent(thirdStudent);
        session2.populateSessionContent(studentDaoa);
        session.saveSession(sp);
        session2.saveSession(sp);
        Set<String> set = sp.getStringSet("CSE 110", null);
        Set<String> set2 = sp.getStringSet("CSE 127", null);
        Iterator<String> it = set.iterator();
        //Gets expected elements from Session 1
        String student1 = it.next();
        String student2 = it.next();
        String expected1 = "John smith www.headshoturlLink1.com 1 2022,FA,CSE,127";
        String expected2 = "Jane Doe www.headshoturlLink2.com 0 2022,FA,CSE,127 2022,WI,CSE,110";
        String expected3 = "Jason Foo www.headshoturlLink3.com 3 2021,WI,CSE,130 2022,WI,CSE,120";
        assertEquals(expected1, student1);
        assertEquals(expected2, student2);
        //Gets expected elements from Session 2
        System.out.println(set2);
        Iterator<String> it2 = set2.iterator();
        //Reminder it sorts by number of classes in common
        String student1Session2 = it2.next();
        String student2Session2 = it2.next();
        String student3Session3 = it2.next();
        assertEquals(expected3, student1Session2);
        assertEquals(expected1, student2Session2);
        assertEquals(expected2, student3Session3);
    }
    //Test saved correct session name
    @Test
    public void testSessionNames(){
        Session session = new Session("CSE 101");
        System.out.println("TESTING testSessionNames Result was->" +  session.getSessionName());
        assertEquals("CSE 101",session.getSessionName());
    }
    //Test correct session retrieved
    @Test
    public void testIncorrectSession(){
        Context context = getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences("userInfo", 0);
        Session session = new Session("CSE 110");
        Student sampleStudent = new Student(0, "www.headshoturlLink1.com", "John smith", "2022,FA,CSE,127", 1);
        studentDaoa.insertStudent(sampleStudent);
        session.populateSessionContent(studentDaoa);
        session.saveSession(sp);
        Set<String> set = sp.getStringSet("CSE 120", null);
        //Should be null since cse 120 session does not exist
        assertNull(set);

    }



}