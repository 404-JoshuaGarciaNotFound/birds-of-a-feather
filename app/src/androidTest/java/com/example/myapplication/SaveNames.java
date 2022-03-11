package com.example.myapplication;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
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
public class SaveNames {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.BLUETOOTH_ADVERTISE",
                    "android.permission.BLUETOOTH_SCAN",
                    "android.permission.BLUETOOTH_CONNECT");

    @Test
    public void saveNames() {
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
        appCompatEditText11.perform(replaceText("uuid,,,\nTed,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"), closeSoftKeyboard());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("uuid,,,\nTed,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText12.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("uuid,,,\nTed,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("uuid,,,\nHarry,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"));

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("uuid,,,\nHarry,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText14.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("uuid,,,\nHarry,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText15.perform(click());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("uuid,,,\nHarry,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("uuid3,,,\nHarry,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"));

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.DemomockUserInput), withText("uuid3,,,\nHarry,,,\nhttps://i.ibb.co/N7MGG27/download.png,,,\n2021,FA,CSE,210\n2022,FA,CSE,110\n2022,FA,CSE,11\n2022,FA,CSE,112"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText17.perform(closeSoftKeyboard());

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

        ViewInteraction materialButton12 = onView(
                allOf(withId(R.id.StartStopBttn), withText("STOP"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton12.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.editTextTextPersonName2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText18.perform(replaceText("session"), closeSoftKeyboard());

        ViewInteraction materialButton13 = onView(
                allOf(withId(R.id.saveButtonForSessionName), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        materialButton13.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.MoreOpts),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                12),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.floatingActionButton3),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                10),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.List_Of_Sessions),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.student_firstname), withText("Harry"),
                        withParent(allOf(withId(R.id.frameLayout),
                                withParent(withId(R.id.ListOfStudentsFromSession)))),
                        isDisplayed()));
        textView.check(matches(withText("Harry")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.student_firstname), withText("Bill"),
                        withParent(allOf(withId(R.id.frameLayout),
                                withParent(withId(R.id.ListOfStudentsFromSession)))),
                        isDisplayed()));
        textView2.check(matches(withText("Bill")));
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
