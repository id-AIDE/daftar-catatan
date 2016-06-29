package com.catatanku;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Starts the About Screen Activity
                startActivity(new Intent(getBaseContext(), DaftarCatatanActivity.class));
            }
        }, 3000L);

        }
    public void onConfigurationChanged(Configuration newConfig) {
        // Manages auto rotation for the Splash Screen Layout
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_splash);
    }
}

