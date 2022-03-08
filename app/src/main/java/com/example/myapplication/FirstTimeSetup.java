package com.example.myapplication;

import static com.example.myapplication.CreateBuilderAlert.buildBuilder;
import static com.example.myapplication.CreateSpinner.newSpinnerSpinny;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.student.db.AppDatabaseCourses;
import com.example.myapplication.student.db.Course;

import java.util.UUID;
import java.util.zip.Inflater;

public class FirstTimeSetup {
    public static Boolean done = false;

    //This method opens an alert window that records the preferred name of the user.
    public static void firstTimeSetupName(AppDatabaseCourses DB, Context context, LayoutInflater inflater, SharedPreferences userInfo) {
        //This method creates the builder
        String USER_ID = "user_ID";
        String USER_NAME = "user_name";
        String IS_FIRST_TIME_SETUP_COMPLETE = "isFirstTimeSetUpComplete";
        CreateBuilderAlert.returningVals AD = buildBuilder(context, R.layout.activity_first_time_setup,
                inflater, false, "First Time Setup");
        AlertDialog FTSetup = AD.alertDiag;
        FTSetup.show();
        //Collects name.
        Button submitted = (Button) FTSetup.findViewById(R.id.SubmitFirstName);
        submitted.setOnClickListener(v8 -> {
            final EditText name = (EditText) FTSetup.findViewById(R.id.personName);
            String userName = name.getText().toString();
            //Require users to type name no blanks name should be saved to database
            if (!userName.equals("")) {
                FTSetup.dismiss();
                //Save name to userTextFile in assets. Not sure how to get the file stream going
                SharedPreferences.Editor userInfoEditor = userInfo.edit();
                userInfoEditor.putString(USER_NAME, userName);
                userInfoEditor.apply();

                // Saves to shared preferences
                userInfoEditor.putBoolean(IS_FIRST_TIME_SETUP_COMPLETE, true);
                userInfoEditor.apply();

                //Generate UUID and save to sharedpreference
                String myUUID = UUID.randomUUID().toString();
                userInfoEditor.putString(USER_ID, myUUID);
                userInfoEditor.apply();

                Log.d("Name that was typed in ", userName);
                firstTimeSetupURL(DB, context, inflater, userInfo);
            } else {
                name.setError("Name cannot be empty!");

            }


        });

    }

    public static void firstTimeSetupURL(AppDatabaseCourses DB, Context context, LayoutInflater inflater, SharedPreferences userInfo) {
        String HEAD_SHOT_URL = "head_shot_url";
        //This method creates the builder
        CreateBuilderAlert.returningVals AD = buildBuilder(context, R.layout.activity_first_time_setup_geturl,
                inflater, false, "First Time Setup");
        AlertDialog FTSetup2 = AD.alertDiag;
        FTSetup2.show();

        Button submitted = (Button) FTSetup2.findViewById(R.id.SubmitURL);
        submitted.setOnClickListener(v3 -> {
            final EditText URLy = (EditText) FTSetup2.findViewById(R.id.personURL);
            String headshotURL = URLy.getText().toString();
            //Require users to type name no blanks name should be saved to database
            if (!headshotURL.equals("")) {
                //
                // store user head shot url to the
                // shared preference
                SharedPreferences.Editor userInfoEditor = userInfo.edit();
                userInfoEditor.putString(HEAD_SHOT_URL, headshotURL);
                userInfoEditor.apply();
                done = true;
                FTSetup2.dismiss();
                firstTimeAddClasses(DB, context, inflater);
            } else {
                URLy.setError("URL cannot be empty!");
            }
            Log.d("URL that was typed in ", headshotURL);
        });
    }


    public static void firstTimeAddClasses(AppDatabaseCourses DB, Context context, LayoutInflater inflater) {
        //This method creates the builder
        CreateBuilderAlert.returningVals AD = buildBuilder(context, R.layout.activity_first_time_add_classes,
                inflater, false, "Add Classes");
        AlertDialog addClasses = AD.alertDiag;
        View addClassesView = AD.viewy;
        addClasses.show();



        // Spinner for selecting year
        Spinner spinner1 = newSpinnerSpinny(context, addClassesView, R.id.selectYear, R.array.year,
                android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item);

        // Spinner for selecting quarter
        // Spinner for selecting year
        Spinner spinner2 = newSpinnerSpinny(context, addClassesView, R.id.selectQuarter,  R.array.quarter,
                android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item);


        // Edit text field for subject
        EditText editSubject = (EditText) addClassesView.findViewById(R.id.editSubject);

        // Edit text field for course number
        EditText editCourseNumber = (EditText) addClassesView.findViewById(R.id.editCourseNumber);

        // Enter button
        Button enterCourseInfo = (Button) addClassesView.findViewById(R.id.enterCourseInfo);
        enterCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yearInd = spinner1.getSelectedItemPosition();
                int quarterInd = spinner2.getSelectedItemPosition();
                String subject = editSubject.getText().toString().trim();
                String courseNumber = editCourseNumber.getText().toString().trim();

                // To get string of year and quarter use the following two lines
                String year = context.getResources().getStringArray(R.array.year)[yearInd];
                String quarter = context.getResources().getStringArray(R.array.quarter)[quarterInd];

                // Course number can also be converted to int when saving to db

                boolean exit = true;

                if (subject.equalsIgnoreCase("")) {
                    editSubject.setError("You didn't input a subject!");
                    exit = false;
                }

                if (courseNumber.equalsIgnoreCase("")) {
                    editCourseNumber.setError("You didn't input a course number!");
                    exit = false;
                }

                if (exit) {
//                    Log.d("str format: ", year + " " + quarter + " " + subject + " " + courseNumber);
                    addCourse(DB, year, quarter, subject, courseNumber);
                    addClasses.cancel();
                    repeatAddClasses(DB, context, inflater, yearInd, quarterInd, subject, courseNumber);
                }
            }
        });
    }

    public static void repeatAddClasses(AppDatabaseCourses DB, Context context, LayoutInflater inflater, int previousYearInd, int previousQuarterInd, String previousSubject, String previousCourseNumber) {

        //This method creates the UI window
        CreateBuilderAlert.returningVals AD = buildBuilder(context, R.layout.activity_repeat_add_classes,
                inflater, false, "Add Classes");
        AlertDialog addClasses = AD.alertDiag;
        View addClassesView = AD.viewy;

        addClasses.show();

        // Spinner for selecting year
        Spinner spinner1 = newSpinnerSpinny(context, addClassesView, R.id.selectYear, R.array.year,
                android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item,previousYearInd);

        // Spinner for selecting quarter
        Spinner spinner2 = newSpinnerSpinny(context, addClassesView, R.id.selectQuarter,  R.array.quarter,
                android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item,previousQuarterInd);

        // Edit text field for subject
        EditText editSubject = (EditText) addClassesView.findViewById(R.id.editSubject);
        editSubject.setText(previousSubject);

        // Edit text field for course number
        EditText editCourseNumber = (EditText) addClassesView.findViewById(R.id.editCourseNumber);
        editCourseNumber.setText(previousCourseNumber);

        // Enter button
        Button enterCourseInfo = (Button) addClassesView.findViewById(R.id.enterCourseInfo);
        enterCourseInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yearInd = spinner1.getSelectedItemPosition();
                int quarterInd = spinner2.getSelectedItemPosition();
                String subject = editSubject.getText().toString().trim();
                String courseNumber = editCourseNumber.getText().toString().trim();

                // To get string of year and quarter use the following two lines
                // String year = getResources().getStringArray(R.array.year)[yearInd];
                // String quarter = getResources().getStringArray(R.array.quarter)[quarterInd];

                // Course number can also be converted to int when saving to db

                boolean exit = true;

                if (subject.equalsIgnoreCase("")) {
                    editSubject.setError("You didn't input a subject!");
                    exit = false;
                }

                if (courseNumber.equalsIgnoreCase("")) {
                    editCourseNumber.setError("You didn't input a course number!");
                    exit = false;
                }

                // Make sure the user has changed some fields
                if (yearInd == previousYearInd
                        && quarterInd == previousQuarterInd
                        && subject.equalsIgnoreCase(previousSubject)
                        && courseNumber.equalsIgnoreCase(previousCourseNumber)
                ) {
                    editCourseNumber.setError("Please add a different class!");
                    exit = false;
                }

                String year = context.getResources().getStringArray(R.array.year)[yearInd];
                String quarter = context.getResources().getStringArray(R.array.quarter)[quarterInd];
                if (exit) {
                    addCourse(DB, year, quarter, subject, courseNumber);
                    addClasses.cancel();
                    repeatAddClasses(DB, context, inflater, yearInd, quarterInd, subject, courseNumber);
                }
            }
        });
        // Done button
        Button doneAddingClasses = (Button) addClassesView.findViewById(R.id.doneAddingClasses);
        doneAddingClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addClasses.cancel();
            }
        });
    }

    /**
     * This method add courses to the course database.
     * These courses are courses of the current user
     * (the one student who is using the app, in contrast to those who are searched by the bluetooth
     *
     * @param year
     * @param quarter
     * @param subject
     * @param courseNumber
     */
    private static void addCourse(AppDatabaseCourses DB, String year, String quarter, String subject, String courseNumber) {
        String courseCode = subject + "," + courseNumber;
        DB.courseDao().insertCourse(
                new Course(DB.courseDao().count() + 1, year, quarter, courseCode));
    }
}

