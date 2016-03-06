package com.connectr.connectrmobile.connectrmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Johnny on 3/5/16.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, AlexaActivity.class);
        startActivity(intent);
        finish();
    }
}