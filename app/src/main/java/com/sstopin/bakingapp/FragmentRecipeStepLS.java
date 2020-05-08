package com.sstopin.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;


public class FragmentRecipeStepLS extends Fragment implements
        RecipeAdapter.RecyclerViewClickListener {

    private ArrayList<HashMap> mStepsArray;
    private HashMap mHashMap;
    RecyclerView recyclerView;
    private Boolean mLargeScreen;
    private RecipeStepsAdapterLS mAdapter;
    private RecipeAdapter.RecyclerViewClickListener mOnClickListener;
    private RecipeStepsAdapterLS mMyAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mStepsArray = (ArrayList<HashMap>) getArguments().getSerializable("stepsArray");
        mHashMap = (HashMap) getArguments().getSerializable("hashMap");

        return inflater.inflate(R.layout.recipe_steps_main, container, false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_recipe_steps);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new RecipeStepsAdapterLS(getActivity(), mStepsArray, mOnClickListener);
        recyclerView.setAdapter(mAdapter);
    }



    public FragmentRecipeStepLS newInstance(ArrayList<HashMap> stepsArray, HashMap hashMap) {
        FragmentRecipeStepLS frs = new FragmentRecipeStepLS();
        Bundle bundle = new Bundle();
        bundle.putSerializable("stepsArray", stepsArray);
        bundle.putSerializable("hashMap", hashMap);
        frs.setArguments(bundle);
        return frs;
    }

    @Override
    public void recyclerViewListClicked(View view, int position) {
        mOnClickListener.recyclerViewListClicked(view, position);
    }
}
