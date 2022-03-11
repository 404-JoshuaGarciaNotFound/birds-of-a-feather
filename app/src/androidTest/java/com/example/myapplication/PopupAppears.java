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
public class PopupAppears {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.BLUETOOTH_ADVERTISE",
"android.permission.BLUETOOTH_SCAN",
"android.permission.BLUETOOTH_CONNECT");

    @Test
    public void popupAppears() {
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
        
        ViewInteraction materialButton4 = onView(
allOf(withId(R.id.doneAddingClasses), withText("Done"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
10),
isDisplayed()));
        materialButton4.perform(click());
        
        ViewInteraction floatingActionButton = onView(
allOf(withId(R.id.MoreOpts),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
12),
isDisplayed()));
        floatingActionButton.perform(click());
        
        ViewInteraction imageButton = onView(
allOf(withId(R.id.floatingActionButton3),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()));
        imageButton.check(matches(isDisplayed()));
        
        ViewInteraction imageButton2 = onView(
allOf(withId(R.id.floatingActionButton3),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()));
        imageButton2.check(matches(isDisplayed()));
        
        ViewInteraction imageButton3 = onView(
allOf(withId(R.id.floatingActionButton2),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()));
        imageButton3.check(matches(isDisplayed()));
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
