package com.connectr.connectrmobile.connectrmobile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Johnny on 3/5/16.
 */
public class LoadingScreenFragment extends Fragment {

    public static LoadingScreenFragment newInstance() {
        return new LoadingScreenFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loading_screen, container, false);
        return v;
    }
}
