package com.example.restaurantfinder;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);


    @Test
    public void change_fragment() {
        Espresso.onView(ViewMatchers.withId(R.id.tab_Restaurant)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tab_StoredList)).perform(click());
    }
}
