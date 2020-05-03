package com.sstopin.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeSteps extends AppCompatActivity
        implements RecipeStepsAdapter.RecyclerViewClickListener{

    private RecyclerView stepsRecyclerView;
    private RecipeStepsAdapter mRecipeStepsAdapter;

    private int mId;
    private String mName;
    private ArrayList<HashMap> mIngredientsArray;
    private ArrayList<HashMap> mStepsArray;
    private String mServings;
    private String mImage;
    private RecipeInfo mRecipeInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_main);

        stepsRecyclerView = findViewById(R.id.rv_recipe_steps);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setHasFixedSize(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        mRecipeInfo = intent.getParcelableExtra("RecipeSteps");
        getIntent().getParcelableExtra("RecipeSteps");

        mId = mRecipeInfo.getId();
        mName = mRecipeInfo.getName();
        mIngredientsArray = mRecipeInfo.getIngredientsArray();
        mStepsArray = mRecipeInfo.getStepsArray();
        mServings = mRecipeInfo.getServings();
        mImage = mRecipeInfo.getImage();

        this.setTitle(mName);

        mRecipeStepsAdapter = new RecipeStepsAdapter(getApplicationContext(), mStepsArray, this);
        stepsRecyclerView.setAdapter(mRecipeStepsAdapter);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Context context = this;
        Class destinationClass = RecipeDetails.class;
        Intent startActivityIntent = new Intent(context, destinationClass);

        HashMap hashMap = new HashMap(mStepsArray.get(position));
        int stepId = (int) hashMap.get("id");
        startActivityIntent.putExtra("stepId", stepId);
        startActivityIntent.putExtra("recipeInfo", mRecipeInfo);
        startActivity(startActivityIntent);

    }
}