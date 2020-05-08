package com.sstopin.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;



public class FragmentRecipeStep extends Fragment {

    private HashMap<String, String> mStepsHashMap;
    private ArrayList<HashMap> mIngredientsArray;
    View rootView;
    private Boolean mLargeScreen;

    public FragmentRecipeStep() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String description;
        TextView textView = null;
        String ingredients = "";

        mStepsHashMap = (HashMap<String, String>) getArguments().getSerializable("stepsHashMap");
        mIngredientsArray = (ArrayList<HashMap>) getArguments().getSerializable("ingredientsArray");
        mLargeScreen = getArguments().getBoolean("largeScreen");


        description = (String) mStepsHashMap.get("description");
        rootView = inflater.inflate(R.layout.recipe_step_fragment, container, false);
        textView = rootView.findViewById(R.id.tv_recipe_step);

        String tempId = (String) mStepsHashMap.get("id");
        if (tempId.equals("I")) {
            for (HashMap mIngredientsHashMap : mIngredientsArray) {
                ingredients = ingredients + mIngredientsHashMap.get("ingredient") + "\n";
                ingredients = ingredients + mIngredientsHashMap.get("quantity") + " " +
                        mIngredientsHashMap.get("measure") + "\n\n";
            }
            textView.setText(ingredients);
        } else {
            textView.setText(description);
        }

        return rootView;
    }


    public FragmentRecipeStep newInstance(HashMap stepsHashMap, ArrayList<HashMap> ingredientsArray,
                                          Boolean largeScreen) {
        FragmentRecipeStep frs = new FragmentRecipeStep();
        Bundle bundle = new Bundle();
        bundle.putSerializable("stepsHashMap", stepsHashMap);
        bundle.putSerializable("ingredientsArray", ingredientsArray);
        bundle.putBoolean("largeScreen", largeScreen);
        frs.setArguments(bundle);
        return frs;
    }
}
