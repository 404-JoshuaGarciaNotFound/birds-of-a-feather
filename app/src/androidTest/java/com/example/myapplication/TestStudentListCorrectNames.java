package com.example.myapplication;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
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
public class TestStudentListCorrectNames {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testStudentListCorrectNames() {
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
5),
isDisplayed()));
        appCompatEditText3.perform(replaceText("CSE"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText4 = onView(
allOf(withId(R.id.editCourseNumber),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
6),
isDisplayed()));
        appCompatEditText4.perform(replaceText("110"), closeSoftKeyboard());
        
        ViewInteraction materialButton3 = onView(
allOf(withId(R.id.enterCourseInfo), withText("Enter"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
7),
isDisplayed()));
        materialButton3.perform(click());
        
        ViewInteraction materialButton4 = onView(
allOf(withId(R.id.doneAddingClasses), withText("Done"),
childAtPosition(
childAtPosition(
withId(R.id.custom),
0),
8),
isDisplayed()));
        materialButton4.perform(click());
        
        ViewInteraction materialButton5 = onView(
allOf(withId(R.id.nearByMockScreen), withText("MOCK"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()));
        materialButton5.perform(click());
        
        ViewInteraction appCompatEditText5 = onView(
allOf(withId(R.id.DemomockUserInput),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText5.perform(longClick());
        
        ViewInteraction linearLayout = onView(
allOf(withContentDescription("Paste"),
childAtPosition(
childAtPosition(
withClassName(is("android.widget.RelativeLayout")),
1),
0),
isDisplayed()));
        linearLayout.perform(click());
        
        ViewInteraction appCompatEditText6 = onView(
allOf(withId(R.id.DemomockUserInput),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText6.perform(replaceText("Bill,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n2022,SP,CSE,127\n2022,SP,CSE,141\n2022,SP,CSE,140\n2022,SP,CSE,123"), closeSoftKeyboard());
        
        ViewInteraction materialButton6 = onView(
allOf(withId(R.id.SubmitMockUser), withText("Enter"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()));
        materialButton6.perform(click());
        
        ViewInteraction appCompatEditText7 = onView(
allOf(withId(R.id.DemomockUserInput),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText7.perform(longClick());
        
        ViewInteraction linearLayout2 = onView(
allOf(withContentDescription("Paste"),
childAtPosition(
childAtPosition(
withClassName(is("android.widget.RelativeLayout")),
1),
0),
isDisplayed()));
        linearLayout2.perform(click());
        
        ViewInteraction appCompatEditText8 = onView(
allOf(withId(R.id.DemomockUserInput),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText8.perform(replaceText("Ted,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n2022,SP,CSE,127\n2022,SP,CSE,141\n2022,SP,CSE,140\n2022,SP,CSE,123"), closeSoftKeyboard());
        
        ViewInteraction appCompatEditText9 = onView(
allOf(withId(R.id.DemomockUserInput), withText("Ted,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,\n2022,SP,CSE,127\n2022,SP,CSE,141\n2022,SP,CSE,140\n2022,SP,CSE,123"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
5),
isDisplayed()));
        appCompatEditText9.perform(click());
        
        ViewInteraction materialButton7 = onView(
allOf(withId(R.id.SubmitMockUser), withText("Enter"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
6),
isDisplayed()));
        materialButton7.perform(click());
        
        ViewInteraction materialButton8 = onView(
allOf(withId(R.id.nearByMockScreen), withText("LIST"),
childAtPosition(
childAtPosition(
withId(android.R.id.content),
0),
3),
isDisplayed()));
        materialButton8.perform(click());
        
        ViewInteraction textView = onView(
allOf(withId(R.id.student_firstname), withText("Bill"),
withParent(allOf(withId(R.id.frameLayout),
withParent(withId(R.id.list_of_students)))),
isDisplayed()));
        textView.check(matches(withText("Bill")));
        
        ViewInteraction textView2 = onView(
allOf(withId(R.id.student_firstname), withText("Bill"),
withParent(allOf(withId(R.id.frameLayout),
withParent(withId(R.id.list_of_students)))),
isDisplayed()));
        textView2.check(matches(withText("Bill")));
        
        ViewInteraction textView3 = onView(
allOf(withId(R.id.student_firstname), withText("Ted"),
withParent(allOf(withId(R.id.frameLayout),
withParent(withId(R.id.list_of_students)))),
isDisplayed()));
        textView3.check(matches(withText("Ted")));


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
