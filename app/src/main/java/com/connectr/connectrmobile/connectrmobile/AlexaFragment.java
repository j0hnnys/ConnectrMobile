package com.connectr.connectrmobile.connectrmobile;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Johnny on 3/5/16.
 */
public class AlexaFragment extends Fragment {

    private Firebase mFirebaseRef;
    private LinearLayout header;
    private ImageView spinningLogo;

    public static AlexaFragment newInstance() {
        return new AlexaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = new AlexaListeningFragment();
        fm.beginTransaction().replace(R.id.alexa_fragment, fragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alexa, container, false);
        header = (LinearLayout) v.findViewById(R.id.header);
        spinningLogo = (ImageView) v.findViewById(R.id.spinning_logo);
        spinningLogo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));

        Firebase.setAndroidContext(getActivity());
        mFirebaseRef = new Firebase("https://dazzling-inferno-6316.firebaseio.com/");
        mFirebaseRef.child("youtube").addChildEventListener(YouTubeEventListener());
        mFirebaseRef.child("tweets").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                ArrayList<TwitterData> topTenTweets = new ArrayList<>();

                while (iterator.hasNext()) {
                    DataSnapshot snapshot = iterator.next();

                    String hashtag = snapshot.child("name").getValue().toString();
                    double tweet_volume = 100000 + (Math.random() * 100000);
                    String url = snapshot.child("url").getValue().toString();
                    TwitterData twitterData = new TwitterData(hashtag, (int) tweet_volume, url);

                    topTenTweets.add(twitterData);
                }

                header.setVisibility(View.VISIBLE);
                spinningLogo.setVisibility(View.GONE);

                Fragment fragment = TwitterTweetsFragment.newInstance(topTenTweets);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.alexa_fragment, fragment).commit();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    private ChildEventListener YouTubeEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FragmentManager fm = getFragmentManager();
                String videoId = dataSnapshot.getValue().toString();
                fm.beginTransaction().replace(R.id.alexa_fragment, YouTubeVideoFragment.newInstance(videoId)).commit();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        };
    }
}
