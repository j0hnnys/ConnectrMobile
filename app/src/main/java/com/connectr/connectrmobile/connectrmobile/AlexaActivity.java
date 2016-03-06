package com.connectr.connectrmobile.connectrmobile;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;

/**
 * Created by Johnny on 3/5/16.
 */
public class AlexaActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return AlexaFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // To handle android.os.NetworkOnMainThreadException errors
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
    }
}
