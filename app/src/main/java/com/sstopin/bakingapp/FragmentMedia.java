package com.sstopin.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class FragmentMedia extends Fragment {

    private String mMediaUrl;
    private SimpleExoPlayer simpleExoPlayer;
    PlayerView playerView = null;
    private Boolean mLargeScreen;

    FragmentMedia() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.video_fragment, container, false);

        mMediaUrl = getArguments().getString("mediaURL");
        mLargeScreen = getArguments().getBoolean("largeScreen");

        Context context = inflater.getContext();

        playerView = rootView.findViewById(R.id.exo_videoFragment);

        simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(getContext(), "display baking video"));

        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mMediaUrl));

        checkOrientation();
        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.prepare(videoSource);

        return rootView;
    }

    private void checkOrientation() {
        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) &&
                !(mLargeScreen)) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_FULLSCREEN);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
                    playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            playerView.setLayoutParams(params);
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    public FragmentMedia newInstance(String mediaUrl, Boolean largeScreen) {
        Bundle args = new Bundle();
        args.putString("mediaURL", mediaUrl);
        args.putBoolean("largeScreen" ,largeScreen);
        FragmentMedia fm = new FragmentMedia();
        fm.setArguments(args);
        return fm;
    }
}
