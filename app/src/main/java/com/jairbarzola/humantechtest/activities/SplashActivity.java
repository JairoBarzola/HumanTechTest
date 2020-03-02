package com.jairbarzola.humantechtest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jairbarzola.humantechtest.R;
import com.jairbarzola.humantechtest.data.local.SessionManager;

public class SplashActivity extends AppCompatActivity {

    SessionManager _sessionManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initInstances();
        initConfiguration();
    }

    private void initInstances() {
        _sessionManager = new SessionManager(getApplicationContext());
    }

    private void initConfiguration() {
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(2000);

                    if (_sessionManager.isLogin()) {
                        nextActivity(SplashActivity.this, null, HomeActivity.class, true);
                    } else {
                        nextActivity(SplashActivity.this, null, LoginActivity.class, true);
                    }
                } catch (Exception e) {
                    //nothing
                }
            }
        };
        t.start();
    }

    private void nextActivity(Activity context, Bundle bundle, Class<?> activity, boolean destroy) {
        Intent intent = new Intent(context, activity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (destroy) context.finish();
    }
}
