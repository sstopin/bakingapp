package com.sstopin.bakingapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class FragmentNoVideo extends Fragment {

    private boolean mLargeScreen;

    public FragmentNoVideo() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLargeScreen = getArguments().getBoolean("largeScreen");

        View rootView = inflater.inflate(R.layout.no_video_fragment, container, false);

        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) &&
                !(mLargeScreen)){
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }


        return rootView;
    }

    public FragmentNoVideo newInstance(Boolean largeScreen) {
        Bundle args = new Bundle();
        args.putBoolean("largeScreen" ,largeScreen);
        FragmentNoVideo fm = new FragmentNoVideo();
        fm.setArguments(args);
        return fm;
    }
}
