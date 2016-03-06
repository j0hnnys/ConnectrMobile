package com.connectr.connectrmobile.connectrmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

/**
 * Created by Johnny on 3/5/16.
 */
public class YouTubeVideoFragment extends YouTubePlayerFragment {

    private static final String API_KEY = "AIzaSyCFNCvvOufliwrkI1CWj8lUJ8VHGzWYfNk";

    public static YouTubeVideoFragment newInstance(String youTubeVideoId) {
        final String videoId = youTubeVideoId;
        YouTubeVideoFragment youTubeVideoFragment = new YouTubeVideoFragment();
        youTubeVideoFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
                player.loadVideo(videoId);
                player.play();
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("YouTube Error", "Failed to initialize youtube");
            }
        });
        return youTubeVideoFragment;
    }

}
