package com.sstopin.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentRecipeDescription extends Fragment {

    private HashMap<String, String> mStepsHashMap;
    View rootView;

    public FragmentRecipeDescription() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String description;
        TextView textView = null;

        mStepsHashMap = (HashMap<String, String>) getArguments().getSerializable("descHashMap");

        description = (String) mStepsHashMap.get("description");
        rootView = inflater.inflate(R.layout.recipe_step_fragment, container, false);
        textView = rootView.findViewById(R.id.tv_recipe_step);

        textView.setText(description);

        return rootView;
    }


    public FragmentRecipeDescription newInstance(HashMap descHashMap) {
        FragmentRecipeDescription frs = new FragmentRecipeDescription();
        Bundle bundle = new Bundle();
        bundle.putSerializable("descHashMap", descHashMap);
        frs.setArguments(bundle);
        return frs;
    }
}
