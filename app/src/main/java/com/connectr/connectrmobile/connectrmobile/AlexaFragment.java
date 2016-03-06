package com.connectr.connectrmobile.connectrmobile;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Johnny on 3/5/16.
 */
public class AlexaFragment extends Fragment {

    private Firebase mFirebaseRef;

    public static AlexaFragment newInstance() {
        return new AlexaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = new AlexaListeningFragment();
        fm.beginTransaction().replace(R.id.alexa_fragment, fragment).commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alexa, container, false);

        Firebase.setAndroidContext(getActivity());
        mFirebaseRef = new Firebase("https://dazzling-inferno-6316.firebaseio.com/");
        mFirebaseRef.child("twitter").addChildEventListener(TwitterEventListener());
        mFirebaseRef.child("youtube").addChildEventListener(YouTubeEventListener());

        return v;
    }

    private ChildEventListener TwitterEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.alexa_fragment, TwitterTweetsFragment.newInstance()).commit();
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

    private ChildEventListener YouTubeEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FragmentManager fm = getFragmentManager();
                String videoId = dataSnapshot.getValue().toString();
                fm.beginTransaction().add(R.id.alexa_fragment, YouTubeVideoFragment.newInstance(videoId)).commit();
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
