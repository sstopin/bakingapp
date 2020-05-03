package com.sstopin.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.HashMap;


public class FragmentRecipeStep extends Fragment {

    private HashMap mHashmap;

    public FragmentRecipeStep(HashMap hashMap) {
        mHashmap = hashMap;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String description;

        description = (String) mHashmap.get("description");

        View rootView = inflater.inflate(R.layout.recipe_step_fragment, container, false);

        TextView textView = rootView.findViewById(R.id.tv_recipe_step);

        textView.setText(description);

        return rootView;
    }
}
