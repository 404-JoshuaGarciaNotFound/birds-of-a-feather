package com.example.myapplication;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.myapplication.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListMultStudentClass {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.BLUETOOTH_ADVERTISE",
"android.permission.BLUETOOTH_SCAN",
"android.permission.BLUETOOTH_CONNECT");

    @Test
    public void listMultStudentClass() {
        ViewInteraction appCompatEditText = onView(
allOf(withId(R.id.personName),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
2),
isDisplayed()));
        appCompatEditText.perform(replaceText("Guy"), closeSoftKeyboard());
        
        ViewInteraction materialButton = onView(
allOf(withId(R.id.SubmitFirstName), withText("Submit"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
1),
isDisplayed()));
        materialButton.perform(click());
        
        ViewInteraction appCompatEditText2 = onView(
allOf(withId(R.id.personURL),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
2),
isDisplayed()));
        appCompatEditText2.perform(replaceText("www.google.com"), closeSoftKeyboard());
        
        ViewInteraction materialButton2 = onView(
allOf(withId(R.id.SubmitURL), withText("Submit"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
1),
isDisplayed()));
        materialButton2.perform(click());
        
        ViewInteraction appCompatEditText3 = onView(
allOf(withId(R.id.editSubject),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
7),
isDisplayed()));
        appCompatEditText3.perform(replaceText("CSE"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText4 = onView(
allOf(withId(R.id.editCourseNumber),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
8),
isDisplayed()));
        appCompatEditText4.perform(replaceText("110"), closeSoftKeyboard());
        
        ViewInteraction materialButton3 = onView(
allOf(withId(R.id.enterCourseInfo), withText("Enter"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
9),
isDisplayed()));
        materialButton3.perform(click());
        
        ViewInteraction appCompatEditText5 = onView(
allOf(withId(R.id.editCourseNumber), withText("110"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
8),
isDisplayed()));
        appCompatEditText5.perform(replaceText("111"));
        
        ViewInteraction appCompatEditText6 = onView(
allOf(withId(R.id.editCourseNumber), withText("111"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
8),
isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());
        
        ViewInteraction materialButton4 = onView(
allOf(withId(R.id.enterCourseInfo), withText("Enter"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
9),
isDisplayed()));
        materialButton4.perform(click());
        
        ViewInteraction appCompatEditText7 = onView(
allOf(withId(R.id.editCourseNumber), withText("111"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
8),
isDisplayed()));
        appCompatEditText7.perform(replaceText("112"));
        
        ViewInteraction appCompatEditText8 = onView(
allOf(withId(R.id.editCourseNumber), withText("112"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
8),
isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());
        
        ViewInteraction materialButton5 = onView(
allOf(withId(R.id.enterCourseInfo), withText("Enter"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
9),
isDisplayed()));
        materialButton5.perform(click());
        
        ViewInteraction materialButton6 = onView(
allOf(withId(R.id.doneAddingClasses), withText("Done"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
10),
isDisplayed()));
        materialButton6.perform(click());
        
        ViewInteraction materialButton7 = onView(
allOf(withId(R.id.StartStopBttn), withText("START"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
2),
isDisplayed()));
        materialButton7.perform(click());
        
        ViewInteraction materialButton8 = onView(
allOf(withId(R.id.nearByMockScreen), withText("MOCK"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()));
        materialButton8.perform(click());
        
        ViewInteraction appCompatEditText9 = onView(
allOf(withId(R.id.DemomockUserInput),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText9.perform(replaceText("uuid,,,\nBill,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,111\n2022,FA,CSE,112"), closeSoftKeyboard());
        
        ViewInteraction materialButton9 = onView(
allOf(withId(R.id.SubmitMockUser), withText("Enter"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
8),
isDisplayed()));
        materialButton9.perform(click());
        
        ViewInteraction appCompatEditText10 = onView(
allOf(withId(R.id.DemomockUserInput),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText10.perform(click());
        
        ViewInteraction appCompatEditText11 = onView(
allOf(withId(R.id.DemomockUserInput),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText11.perform(replaceText("uuid2,,,\nTed,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,115\n2022,FA,CSE,112"), closeSoftKeyboard());
        
        ViewInteraction materialButton10 = onView(
allOf(withId(R.id.SubmitMockUser), withText("Enter"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
8),
isDisplayed()));
        materialButton10.perform(click());
        
        ViewInteraction materialButton11 = onView(
allOf(withId(R.id.nearByMockScreen), withText("BACK"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()));
        materialButton11.perform(click());
        
        ViewInteraction textView = onView(
allOf(withId(R.id.number_matches), withText("Number of Shared Courses: 3"),
withParent(allOf(withId(R.id.frameLayout),
withParent(withId(R.id.list_of_students)))),
isDisplayed()));
        textView.check(matches(withText("Number of Shared Courses: 3")));
        
        ViewInteraction textView2 = onView(
allOf(withId(R.id.number_matches), withText("Number of Shared Courses: 2"),
withParent(allOf(withId(R.id.frameLayout),
withParent(withId(R.id.list_of_students)))),
isDisplayed()));
        textView2.check(matches(withText("Number of Shared Courses: 2")));
        }
    
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
