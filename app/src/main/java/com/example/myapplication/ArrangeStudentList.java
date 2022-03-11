package com.example.myapplication;

import static com.example.myapplication.FormatUsersCourseInfo.formatUserCourses;

import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.AppDatabaseStudent;
import com.example.myapplication.student.db.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ArrangeStudentList {

    /**
     * This method shows a list of students who has taken the same course with the app's user, sorted by the
     * number of classes the student shared with the user
     */
    public static List<Student> arrangeStudentList(AppDatabaseCourses dbCourse, AppDatabaseStudent dbStudent, SharedPreferences userInfo) {
        // get user's taken courses
        List<String> listOfUserCourses = formatUserCourses(dbCourse, userInfo);

        // get list of students
        List<Student> listOfStudents = dbStudent.studentDao().getAll();

        // check whether the students' course match the user's
        // if so, add the user to the students to display
        // FIXME: for those who implement messenger: this block calculate the
        //  numSharedCourse before a student is entered into database. So you need to
        //  pass this information to the messenger as well to keep database updated.
        //  Please delete the identical code in refreshStudentList() because that will
        //  cause numSharedCourse to increase multiple times.
        for (Student student : listOfStudents) {
            int numSharedCourses = 0;
            for (String courseStr : listOfUserCourses) {
                if (student.getCourses().contains(courseStr)) {
                    // the str of courses of a student contains a substring representing one of the
                    // user's taken courses, the student is a match.
                    numSharedCourses++;
                }
            }
            student.setNumSharedCourses(numSharedCourses);
            dbStudent.studentDao().SetSharedCourse(student.getId(), numSharedCourses);
        }

        listOfStudents.sort(Comparator.comparing(Student::getNumSharedCourses));

        // Remove student who does not have shared courses
        for (Iterator<Student> it = listOfStudents.iterator(); it.hasNext();) {
            Student student = it.next();
            if (student.getNumSharedCourses() == 0) {
                it.remove();
            }
        }

        List<Student> finalListOfStudents = new ArrayList<>();
        for (int i = listOfStudents.size() - 1; i >= 0; i--){
            if (listOfStudents.get(i).isWaveReceived()){
                finalListOfStudents.add(0, listOfStudents.get(i));
            }
            else{
                finalListOfStudents.add(listOfStudents.get(i));
            }
        }

        return finalListOfStudents;

//        RecyclerView listOfStudentsView = findViewById(R.id.list_of_students);
//        StudentAdapter listOfStudentsViewAdapter = new StudentAdapter(finalListOfStudents);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                listOfStudentsView.setAdapter(listOfStudentsViewAdapter);
//            }
//        });
    }
}
