package com.sstopin.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class FragmentMedia extends Fragment {

    private String mMediaUrl;

    FragmentMedia(String mediaUrl) {
        mMediaUrl = mediaUrl;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.video_fragment, container, false);

        Context context = inflater.getContext();

        PlayerView playerView = rootView.findViewById(R.id.exo_videoFragment);

        SimpleExoPlayer SimpleExoPlayer = new SimpleExoPlayer.Builder(context).build();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(getContext(), "display baking video"));

        MediaSource videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mMediaUrl));

        playerView.setPlayer(SimpleExoPlayer);
        SimpleExoPlayer.prepare(videoSource);

        return rootView;
    }
}
