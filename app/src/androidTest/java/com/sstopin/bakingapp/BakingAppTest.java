package com.sstopin.bakingapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class BakingAppTest {
    private static final int RECIPETOSELECT=1;
    private static final int STEPTOSELECT=6;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void test_isRecyclerViewVisable_onAppLaunch() {
        onView(withId(R.id.rv_recipe)).check(matches(isDisplayed()));
    }

    @Test
    public void test_clickOnBrownie() {
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(RECIPETOSELECT, click()));

        onView(withId(R.id.rv_recipe_steps)).check(matches(isDisplayed()));
    }

    @Test
    public void test_clickOnAddEggs() {
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(RECIPETOSELECT, click()));

        onView(withId(R.id.rv_recipe_steps)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_recipe_steps)).perform(scrollToPosition(STEPTOSELECT));
        onView(withRecyclerView(R.id.rv_recipe_steps).atPosition(STEPTOSELECT))
                .check(matches(hasDescendant(withText("Add eggs."))));
    }

    @Test
    public void test_stepsScreen() {
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition(RECIPETOSELECT, click()));

        onView(withId(R.id.rv_recipe_steps)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_recipe_steps))
                .perform(scrollToPosition(STEPTOSELECT));

        onView(withRecyclerView(R.id.rv_recipe_steps)
                .atPosition(STEPTOSELECT)).perform(click());

        onView(withId(R.id.sv_recipe_step)).check(matches(isDisplayed()));

        onView(withId(R.id.tv_recipe_step))
                .check(matches(withText(containsString("5. Crack 3 eggs"))));
    }

    public static RecyclerViewMatcher withRecyclerView(int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
