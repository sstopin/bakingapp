package com.sstopin.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;


public class RecipeDetails extends AppCompatActivity {

    private int mId;
    private String mName;
    private ArrayList<HashMap> mIngredientsArray;
    private ArrayList<HashMap> mStepsArray;
    private HashMap hashMap;
    private int stepId;
    private FragmentTransaction transaction;
    FragmentManager fragmentManager;

    private String mServings;
    private String mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details);

        Intent intent = getIntent();
        stepId = intent.getIntExtra("stepId", 0);
        RecipeInfo mRecipeInfo = intent.getParcelableExtra("recipeInfo");
        getIntent().getParcelableExtra("recipeInfo");
        mStepsArray = mRecipeInfo.getStepsArray();

        fragmentManager = getSupportFragmentManager();

        loadFragments();
        transaction.commit();
    }

    private void loadFragments() {
        hashMap = new HashMap(mStepsArray.get(stepId));

        FrameLayout videoContainer = findViewById(R.id.fl_video_container);

        transaction = fragmentManager.beginTransaction();

        FragmentMedia workFragment = new FragmentMedia((String) hashMap.get("videoURL"));

        String videoUrl = (String) hashMap.get("videoURL");
        if (!(videoUrl.equals(""))) {
            transaction.detach(workFragment).attach(workFragment);
            transaction.replace(R.id.fl_video_container, workFragment);
            videoContainer.setVisibility(View.VISIBLE);
        } else {
            videoContainer.setVisibility(View.INVISIBLE);
        }

        FragmentRecipeStep fragmentStep = new FragmentRecipeStep(hashMap);
        transaction.replace(R.id.fl_recipe_step, fragmentStep);

        FragmentButtons fragmentButtons = new FragmentButtons();
        transaction.replace(R.id.fl_buttons, fragmentButtons);
    }

    public void prevButtonClicked(View view) {
        if (stepId < 1) {
            stepId = 0;
        } else {
            stepId -= 1;
        }
        loadFragments();
        transaction.commit();
    }

    public void nextButtonClicked(View view) {
        if (stepId >= mStepsArray.size() - 1) {
            stepId = mStepsArray.size() - 1;
        } else {
            stepId += 1;
        }
        loadFragments();
        transaction.commit();
    }
}
