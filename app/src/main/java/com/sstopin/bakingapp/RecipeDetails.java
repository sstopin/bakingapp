package com.sstopin.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class RecipeDetails extends AppCompatActivity {

    private String mName;
    private ArrayList<HashMap> mIngredientsArray;
    private ArrayList<HashMap> mStepsArray;
    private HashMap stepsHashMap;
    private String stepId;
    private FragmentTransaction transaction;
    FragmentManager fragmentManager;
    private int idx;

    private RecyclerView stepsRecyclerView;

    private boolean mLargeScreen;


    private static final String IDX = "idx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        if (findViewById(R.id.ll_recipe_steps_large_Screen) != null) {
            mLargeScreen = true;
        } else {
            mLargeScreen = false;
        }

        Intent intent = getIntent();
        stepId = intent.getStringExtra("stepId");

        boolean digits = TextUtils.isDigitsOnly(stepId);
        if (digits) {
            idx = Integer.parseInt(stepId) + 1;
        } else {
            idx = 0;
        }

        RecipeInfo mRecipeInfo = intent.getParcelableExtra("recipeInfo");
        getIntent().getParcelableExtra("recipeInfo");
        mName = mRecipeInfo.getName();
        mStepsArray = mRecipeInfo.getStepsArray();
        mIngredientsArray = mRecipeInfo.getIngredientsArray();

        fragmentManager = getSupportFragmentManager();

        loadFragments();
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(IDX, idx);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void loadFragments() {
        stepsHashMap = new HashMap(mStepsArray.get(idx));

        FrameLayout stepContainer = findViewById(R.id.fl_recipe_step);
        FrameLayout buttonsContainer = findViewById(R.id.fl_buttons);
        LinearLayout largeScreenStepsContainer = findViewById(R.id.ll_recipe_steps_large_Screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transaction = fragmentManager.beginTransaction();

        this.setTitle(mName);

        String videoUrl = (String) stepsHashMap.get("videoURL");
        if (!(videoUrl.equals(""))) {
            FragmentMedia workFragment = new FragmentMedia()
                    .newInstance((String) stepsHashMap.get("videoURL"), mLargeScreen);
            workFragment.setRetainInstance(true);
            transaction.detach(workFragment).attach(workFragment);
            transaction.replace(R.id.fl_video_container, workFragment);
        } else {
            FragmentNoVideo workFragment = new FragmentNoVideo().newInstance(mLargeScreen);
            workFragment.setRetainInstance(true);
            transaction.detach(workFragment).attach(workFragment);
            transaction.replace(R.id.fl_video_container, workFragment);
        }


        FragmentRecipeStep fragmentStep = new FragmentRecipeStep()
                .newInstance(stepsHashMap, mIngredientsArray, mLargeScreen);
        transaction.detach(fragmentStep).attach(fragmentStep);
        transaction.replace(R.id.fl_recipe_step, fragmentStep);
        fragmentStep.setRetainInstance(true);
        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) &&
                !(mLargeScreen)) {
            stepContainer.setVisibility(View.GONE);
        } else {
            stepContainer.setVisibility(View.VISIBLE);
        }


        if (!(mLargeScreen)) {
            FragmentButtons fragmentButtons = new FragmentButtons();
            transaction.replace(R.id.fl_buttons, fragmentButtons);
            fragmentButtons.setRetainInstance(true);
            if ((getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) && !(mLargeScreen)) {
                buttonsContainer.setVisibility(View.GONE);
            } else {
                buttonsContainer.setVisibility(View.VISIBLE);
            }
        } else {
            FragmentRecipeStepLS fragmentStepLS = new FragmentRecipeStepLS()
                    .newInstance(mStepsArray, stepsHashMap);
            transaction.detach(fragmentStepLS).attach(fragmentStepLS);
            transaction.replace(R.id.fl_recipe_list, fragmentStepLS);
            fragmentStepLS.setRetainInstance(true);
        }
    }

    public void prevButtonClicked(View view) {
        if (idx < 1) {
            idx = 0;
        } else {
            idx -= 1;
        }
        loadFragments();
        transaction.commit();
    }

    public void nextButtonClicked(View view) {
        if (idx >= mStepsArray.size() - 1) {
            idx = mStepsArray.size() - 1;
        } else {
            idx += 1;
        }
        loadFragments();
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
