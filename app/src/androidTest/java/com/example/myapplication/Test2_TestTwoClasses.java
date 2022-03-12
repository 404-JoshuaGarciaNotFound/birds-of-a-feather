package com.example.myapplication;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Test2_TestTwoClasses {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.BLUETOOTH_ADVERTISE",
                    "android.permission.BLUETOOTH_SCAN",
                    "android.permission.BLUETOOTH_CONNECT");

    @Test
    public void test2_TestTwoClasses() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.personName),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Josh"), closeSoftKeyboard());

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
        appCompatEditText2.perform(replaceText("WWW.go"), closeSoftKeyboard());

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

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.doneAddingClasses), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                10),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.StartStopBttn), withText("START"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.nearByMockScreen), withText("MOCK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.DemomockUserInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("5d812697-e3fe-46ad-a7d5-b14c0031f1b9,,,,\nLiu,,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,,\n2022,FA,CSE,127,tiny\n2022,FA,CSE,110,tiny\n2021,FA,CSE,210,tiny\n1eaf98d5-9bca-413c-a8b1-8ba494f9da67,wave,,, "), closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.SubmitMockUser), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.nearByMockScreen), withText("BACK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.nearByMockScreen), withText("MOCK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.DemomockUserInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText8.perform(longClick());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.DemomockUserInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText9.perform(longClick());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.DemomockUserInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("5d812697-e3fe-46ad-a7d5-b14c0031f1b9,,,,\nLiu,,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,,\n2022,FA,CSE,127,tiny\n2022,FA,CSE,110,tiny\n2021,FA,CSE,210,tiny\n1eaf98d5-9bca-413c-a8b1-8ba494f9da67,wave,,, "), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("5d812697-e3fe-46ad-a7d5-b14c0031f1b9,,,,\nLiu,,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,,\n2022,FA,CSE,127,tiny\n2022,FA,CSE,110,tiny\n2021,FA,CSE,210,tiny\n1eaf98d5-9bca-413c-a8b1-8ba494f9da67,wave,,, "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText11.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("5d812697-e3fe-46ad-a7d5-b14c0031f1b9,,,,\nLiu,,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,,\n2022,FA,CSE,127,tiny\n2022,FA,CSE,110,tiny\n2021,FA,CSE,210,tiny\n1eaf98d5-9bca-413c-a8b1-8ba494f9da67,wave,,, "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("5d812697-e3fe-46ad-a7d5-a14c0031f1b9,,,,\nLiu,,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,,\n2022,FA,CSE,127,tiny\n2022,FA,CSE,110,tiny\n2022,FA,CSE,111,tiny\n1eaf98d5-9bca-413c-a8b1-8ba494f9da67,wave,,, "));

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("5d812697-e3fe-46ad-a7d5-a14c0031f1b9,,,,\nLiu,,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,,\n2022,FA,CSE,127,tiny\n2022,FA,CSE,110,tiny\n2022,FA,CSE,111,tiny\n1eaf98d5-9bca-413c-a8b1-8ba494f9da67,wave,,, "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText13.perform(closeSoftKeyboard());

        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.SubmitMockUser), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
                        isDisplayed()));
        materialButton11.perform(click());

        ViewInteraction materialButton12 = onView(
                allOf(withId(R.id.nearByMockScreen), withText("BACK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton12.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.number_matches), withText("Number of Shared Courses: 2"),
                        withParent(allOf(withId(R.id.frameLayout),
                                withParent(withId(R.id.list_of_students)))),
                        isDisplayed()));
        textView.check(matches(withText("Number of Shared Courses: 2")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.number_matches), withText("Number of Shared Courses: 1"),
                        withParent(allOf(withId(R.id.frameLayout),
                                withParent(withId(R.id.list_of_students)))),
                        isDisplayed()));
        textView2.check(matches(withText("Number of Shared Courses: 1")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.number_matches), withText("Number of Shared Courses: 1"),
                        withParent(allOf(withId(R.id.frameLayout),
                                withParent(withId(R.id.list_of_students)))),
                        isDisplayed()));
        textView3.check(matches(withText("Number of Shared Courses: 1")));
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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
