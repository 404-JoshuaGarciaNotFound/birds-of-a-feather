package com.example.myapplication;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.student.db.Course;
import com.example.myapplication.student.db.Student;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is designed to handle sort students by class size and recency functionality
 */
public class FilterUtil {

    // These are finite structures that denote weight for class sizes ad recency, for comparison
    private final HashMap<String, Integer> CLASS_SIZE_WEIGHT = new HashMap<>();
    private final HashMap<String, Integer> CLASS_RECENCY_WEIGHT = new HashMap<>();

    // data structures
    private List<Course> courseList;
    private List<Student> studentsList;
    private SharedPreferences sp;
    // views
    private Button classSortButton;
    private Button recencySortButton;
    private RecyclerView studentListRCV;

    public FilterUtil(SharedPreferences SP, List<Course> courseList, List<Student> studentsList, Button classSortButton, Button recencySortButton, RecyclerView studentListRCV) {
        this.sp = SP;
        this.courseList = courseList;
        this.studentsList = studentsList;
        this.classSortButton = classSortButton;
        this.recencySortButton = recencySortButton;
        this.studentListRCV = studentListRCV;

        CLASS_SIZE_WEIGHT.put("Tiny", 0);
        CLASS_SIZE_WEIGHT.put("Small", 1);
        CLASS_SIZE_WEIGHT.put("Medium", 2);
        CLASS_SIZE_WEIGHT.put("Large", 3);
        CLASS_SIZE_WEIGHT.put("Huge", 4);
        CLASS_SIZE_WEIGHT.put("Gigantic", 5);

        CLASS_RECENCY_WEIGHT.put("SP", 0);
        CLASS_RECENCY_WEIGHT.put("SS1", 1);
        CLASS_RECENCY_WEIGHT.put("SS2", 2);
        CLASS_RECENCY_WEIGHT.put("SS3", 3);
        CLASS_RECENCY_WEIGHT.put("FA", 4);
        CLASS_RECENCY_WEIGHT.put("WI", 5);
    }

    public FilterUtil() {
    }

    /**
     * This method set listener to the buttons that sort students by class size / by recency
     */
    void setupButtons() {
        this.classSortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClassSortClick();
            }
        });
        this.recencySortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecencyClick();
            }
        });
    }

    /**
     * private helper method that implement the actual on click event for sort by class size button
     */
    private void onClassSortClick() {
        // use this structure to store pairs: {student - student and user's share course}
        // populate the structure to use a single .sort() method to sort the entire list according to size of share course
        HashMap<Student, Course> matchedCourses = new HashMap<>();
        for (Student student :
                studentsList) {
            for (Course course :
                    courseList) {
                if (student.getCourses().contains(course.getCourseCode())) {
                    matchedCourses.put(student, course);
                }
            }
        }

        // invoke .sort()
        studentsList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                Course sharedCourse1 = matchedCourses.get(student1);
                int weight1 = CLASS_SIZE_WEIGHT.get(sharedCourse1.getCourseSize());

                Course sharedCourse2 = matchedCourses.get(student2);
                int weight2 = CLASS_SIZE_WEIGHT.get(sharedCourse2.getCourseSize());
                return weight1 - weight2;
            }
        });
        StudentAdapter studentAdapter = new StudentAdapter(null, studentsList);
        studentListRCV.setAdapter(studentAdapter);
    }

    /**
     * private helper method that implement the actual on click event for sort by recency button
     */
    private void onRecencyClick() {
        // use this structure to store pairs: {student - student and user's share course}
        // populate the structure to use a single .sort() method to sort the entire list according to recency of share course
        HashMap<Student, Course> matchedCourses = new HashMap<>();
        for (Student student :
                studentsList) {
            for (Course course :
                    courseList) {
                if (student.getCourses().contains(course.getCourseCode())) {
                    matchedCourses.put(student, course);
                }
            }
        }

        // invoke .sort()
        studentsList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                Course sharedCourse1 = matchedCourses.get(student1);
                Integer course1Year = Integer.valueOf(sharedCourse1.getYear());

                Course sharedCourse2 = matchedCourses.get(student2);
                Integer course2Year = Integer.valueOf(sharedCourse2.getYear());

                if (!course1Year.equals(course2Year)) {
                    return course1Year - course2Year;
                } else {
                    int weight1 = CLASS_RECENCY_WEIGHT.get(sharedCourse1.getQuarter());
                    int weight2 = CLASS_RECENCY_WEIGHT.get(sharedCourse2.getQuarter());
                    return weight1 - weight2;
                }
            }
        });
        StudentAdapter studentAdapter = new StudentAdapter(sp , studentsList);
        studentListRCV.setAdapter(studentAdapter);
    }
}
