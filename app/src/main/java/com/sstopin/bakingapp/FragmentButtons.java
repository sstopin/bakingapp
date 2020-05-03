package com.sstopin.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.HashMap;


public class FragmentButtons extends Fragment {

    public FragmentButtons() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.buttons_fragment, container, false);

     //   LinearLayout linearLayout = rootView.findViewById(R.id.ll_buttons);

        return rootView;
    }
}
