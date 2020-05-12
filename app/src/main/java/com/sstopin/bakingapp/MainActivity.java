package com.sstopin.bakingapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter mAdapter;

    private ArrayList<RecipeInfo> mBakingInfoArray = new ArrayList<>();
    private ArrayList<HashMap> mIngredientsArray = new ArrayList<HashMap>();
    private ArrayList<HashMap> mStepsArray = new ArrayList<HashMap>();

    private boolean mLargeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fl_largeScreen) != null) {
            mLargeScreen = true;
            recyclerView = findViewById(R.id.rv_recipe);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            mAdapter = new RecipeAdapter(getApplicationContext(), mBakingInfoArray, this);
            recyclerView.setAdapter(mAdapter);

            new GetBakingInfo().execute(NetworkUtils.getBakingUrl());
        } else {
            mLargeScreen = false;
            recyclerView = findViewById(R.id.rv_recipe);

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            mAdapter = new RecipeAdapter(getApplicationContext(), mBakingInfoArray, this);
            recyclerView.setAdapter(mAdapter);

            new GetBakingInfo().execute(NetworkUtils.getBakingUrl());
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Context context = this;
        Class destinationClass = RecipeSteps.class;
        Intent startActivityIntent = new Intent(context, destinationClass);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("currentRecipeId",
                String.valueOf(mBakingInfoArray.get(position).getId())).apply();


        startActivityIntent.putExtra("RecipeSteps", mBakingInfoArray.get(position));
        startActivity(startActivityIntent);
    }


    public class GetBakingInfo extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String bakingResults = null;
            try {
                bakingResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bakingResults;
        }

        @Override
        protected void onPostExecute(String bakingResults) {
            if (bakingResults != null && !bakingResults.equals("")) {
                    ProcessBakingInfo(bakingResults);
            }
        }

        public void ProcessBakingInfo(String bakingJSON) {
            try {
                bakingJSON = bakingJSON.replaceAll("\n","");
                mBakingInfoArray.clear();
                JSONArray bakingArrayJSON = new JSONArray(bakingJSON);
                if (bakingArrayJSON.length() > 0) {
                    int numOfItems = bakingArrayJSON.length();
                    for (int i = 0; i < numOfItems; i++) {
                        int id = bakingArrayJSON.getJSONObject(i).optInt("id");
                        String name = bakingArrayJSON.getJSONObject(i).optString("name");
                        String ingredients = bakingArrayJSON.getJSONObject(i).optString("ingredients");
                        JSONArray ingredientsArrayJSON = new JSONArray(ingredients);
                        mIngredientsArray = new ArrayList<>();
                        HashMap ingredientsMap = new HashMap();
                        for (int y = 0; y < ingredientsArrayJSON.length(); y++){
                            ingredientsMap.put("quantity", ingredientsArrayJSON.getJSONObject(y).optString("quantity"));
                            ingredientsMap.put("measure", ingredientsArrayJSON.getJSONObject(y).optString("measure"));
                            ingredientsMap.put("ingredient", ingredientsArrayJSON.getJSONObject(y).optString("ingredient"));
                            mIngredientsArray.add(ingredientsMap);
                            ingredientsMap = new HashMap();
                        }

                        Gson gson = new Gson();
                        String arrayListString = gson.toJson(mIngredientsArray);
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(String.valueOf(id), arrayListString).apply();

                        String steps = bakingArrayJSON.getJSONObject(i).optString("steps");
                        JSONArray stepsArrayJSON = new JSONArray(steps);
                        mStepsArray = new ArrayList<>();
                        HashMap stepsMap = new HashMap();
                        for (int y = 0; y < stepsArrayJSON.length(); y++){
                            if (y == 0) {
                                stepsMap.put("id", "I");
                                stepsMap.put("shortDescription", "Recipe Ingredients");
                                stepsMap.put("description", "");
                                stepsMap.put("videoURL", "");
                                stepsMap.put("thumbnailURL", "");
                                mStepsArray.add(stepsMap);
                                stepsMap = new HashMap();
                            }
                            int tempId = stepsArrayJSON.getJSONObject(y).getInt("id");
                            stepsMap.put("id", String.valueOf(tempId));
                            stepsMap.put("shortDescription", stepsArrayJSON.getJSONObject(y).optString("shortDescription"));
                            stepsMap.put("description", stepsArrayJSON.getJSONObject(y).optString("description"));
                            stepsMap.put("videoURL", stepsArrayJSON.getJSONObject(y).optString("videoURL"));
                            stepsMap.put("thumbnailURL", stepsArrayJSON.getJSONObject(y).optString("thumbnailURL"));
                            mStepsArray.add(stepsMap);
                            stepsMap = new HashMap();
                        }
                        String servings = bakingArrayJSON.getJSONObject(i).optString("servings");
                        String image = bakingArrayJSON.getJSONObject(i).optString("image");

                        mBakingInfoArray.add(new RecipeInfo(id, name, mIngredientsArray, mStepsArray,
                                servings, image));
                    }
                }
                recyclerView.setAdapter(mAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
