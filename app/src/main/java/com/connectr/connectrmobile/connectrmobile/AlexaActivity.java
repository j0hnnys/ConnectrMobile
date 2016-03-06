package com.connectr.connectrmobile.connectrmobile;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by Johnny on 3/5/16.
 */
public class AlexaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Activity activity = new Activity();
        getLayoutInflater().setFactory(activity);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getFragmentManager();

        // Add fragment_loading_screen for initial display
        Fragment fragment = LoadingScreenFragment.newInstance();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit();

        // Set animation for fragment transaction
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.remove(fragment).commit();

        // Begin transaction to add main screen
        fragment = AlexaFragment.newInstance();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit();

        // To handle android.os.NetworkOnMainThreadException errors
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
