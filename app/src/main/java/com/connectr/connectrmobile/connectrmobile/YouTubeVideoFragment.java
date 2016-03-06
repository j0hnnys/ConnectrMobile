package com.connectr.connectrmobile.connectrmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Johnny on 3/5/16.
 */
public class YouTubeVideoFragment extends Fragment {

    private static final String API_KEY = "AIzaSyCFNCvvOufliwrkI1CWj8lUJ8VHGzWYfNk";
    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer mYouTubePlayer;

    public static YouTubeVideoFragment newInstance() {
        return new YouTubeVideoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);

        // Need to get youtube video id
        final String videoIDPassed = getActivity().getIntent().getStringExtra("rnHCVZdJdJw");

        mYouTubePlayerView = (YouTubePlayerView) v.findViewById(R.id.youtube_player_view);

        // This process runs in background. So mYouTubePlayer may take some time to initialize.
        mYouTubePlayerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYouTubePlayer = youTubePlayer;
                mYouTubePlayer.loadVideo(videoIDPassed);
                mYouTubePlayer.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                System.out.println("\nYouTube Player Initialize FAILED\n");
            }
        });

        return v;
    }
}
