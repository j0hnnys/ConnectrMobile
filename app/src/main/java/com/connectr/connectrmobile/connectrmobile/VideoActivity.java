package com.connectr.connectrmobile.connectrmobile;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Johnny on 3/5/16.
 */
public class VideoActivity extends YouTubeBaseActivity {

    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer mYouTubePlayer;
    private static final String API_KEY = "AIzaSyCFNCvvOufliwrkI1CWj8lUJ8VHGzWYfNk";

    public static final String VIDEO_ID_KEY = "VIDEO_ID_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_video);

        final String videoIDPassed = getIntent().getStringExtra(VIDEO_ID_KEY);

        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);

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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
