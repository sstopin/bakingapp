package com.sstopin.bakingapp;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.AccessibleObject;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {
    private static final int ITEMBELOWFINDINLIST=2;

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void test_isRecyclerViewVisable_onAppLaunch() {
        onView(withId(R.id.rv_recipe)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                isDisplayed();
            }
        });
    }

    @Test
    public void test_clickOnBrownie() {
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));

        onView(withId(R.id.rv_recipe_steps)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                isDisplayed();
            }
        });
    }

    @Test
    public void test_clickOnAddEggs() {
 //       onData(withId(R.id.tv_recipe_steps)).inAdapterView(withId(R.id.ll_recipe_steps))
 //               .atPosition(7).perform(click());

//        onView(withId(R.id.tv_recipe_name))
//                .perform(RecyclerViewActions.scrollToPosition(7));

//        RecyclerView recyclerView =  mMainActivityTestRule.getActivity().findViewById(R.id.rv_recipe);
//        int itemCount = recyclerView.getAdapter().getItemCount();
//        onView(withId(R.id.rv_recipe)).perform(RecyclerViewActions.scrollToPosition(itemCount-1));

        onView(ViewMatchers.withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEMBELOWFINDINLIST, click()));
        String itemVal = "text";
        onView(withText(itemVal)).check(matches(withText("text"));





//        onView(withId(R.id.tv_recipe_name))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
    }

//
//
//        onView(withId(R.id.tv_recipe_steps)).check(new ViewAssertion() {
//            @Override
//            public void check(View view, NoMatchingViewException noViewFoundException) {
//                matches(withText("Add eggs."));
//            }
//        });


//    onData(withId(R.id.rv_recipe))
//            .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
}
