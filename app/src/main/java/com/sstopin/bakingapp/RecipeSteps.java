package com.sstopin.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import butterknife.OnClick;

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
    private WidgetService myWidgetRemoteViewsFactory;

    private boolean mLargeScreen;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_main);

        if (findViewById(R.id.fl_steps_large_screen) != null) {
            mLargeScreen = true;

            stepsRecyclerView = findViewById(R.id.rv_recipe_steps);

            Intent intent = getIntent();
            mRecipeInfo = intent.getParcelableExtra("RecipeSteps");
            getIntent().getParcelableExtra("RecipeSteps");

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
            stepsRecyclerView.setLayoutManager(layoutManager);
            stepsRecyclerView.setHasFixedSize(true);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            mLargeScreen = false;
            stepsRecyclerView = findViewById(R.id.rv_recipe_steps);

            if (savedInstanceState != null) {
                mRecipeInfo = savedInstanceState.getParcelable("RecipeStepsSaved");
            } else {
                Intent intent = getIntent();
                mRecipeInfo = intent.getParcelableExtra("RecipeSteps");
                getIntent().getParcelableExtra("RecipeSteps");
            }
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
            stepsRecyclerView.setLayoutManager(layoutManager);
            stepsRecyclerView.setHasFixedSize(true);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        mId = mRecipeInfo.getId();
        mName = mRecipeInfo.getName();
        mIngredientsArray = mRecipeInfo.getIngredientsArray();

        ArrayList<String> sharedIngredients = new ArrayList<String>();
        for (int i = 0;i < mIngredientsArray.size();i++){
            HashMap tempHashMap = mIngredientsArray.get(i);
            sharedIngredients.add((String) tempHashMap.get("ingredient"));
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(sharedIngredients);
        editor.putString("IngredientsList", json);
        editor.apply();

        Intent intent = new Intent(this, WidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplicationContext())
                .getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ids);
        sendBroadcast(intent);

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
        String stepId = (String) hashMap.get("id");
        startActivityIntent.putExtra("stepId", stepId);
        startActivityIntent.putExtra("recipeInfo", mRecipeInfo);
        startActivity(startActivityIntent);

    }

    @OnClick(android.R.id.home)
    public void endProcess (){
        finish();
    }
}
