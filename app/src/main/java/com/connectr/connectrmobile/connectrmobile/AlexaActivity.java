package com.connectr.connectrmobile.connectrmobile;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.StrictMode;

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
        Fragment fragment = AlexaFragment.newInstance();
        fm.beginTransaction().add(R.id.fragment_container, fragment).commit();


        // To handle android.os.NetworkOnMainThreadException errors
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
}
