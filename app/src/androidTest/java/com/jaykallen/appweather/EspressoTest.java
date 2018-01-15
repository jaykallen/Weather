package com.jaykallen.appweather;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jaykallen.appweather.views.WeatherActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {
    @Rule
    public ActivityTestRule<WeatherActivity> mActivityRule = new ActivityTestRule<>(WeatherActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.jaykallen.appweather", appContext.getPackageName());
    }
    @Test
    public void testRecycler() {
        // Basic test of city entry and button click
        onView(withId(R.id.city_edittext)).perform(clearText(), typeText("New York,NY,US"));
        onView(withId(R.id.weather_button)).perform(click());
    }



}
