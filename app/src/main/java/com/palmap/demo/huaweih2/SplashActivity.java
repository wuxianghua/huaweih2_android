package com.palmap.demo.huaweih2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Intent intent = new Intent(SplashActivity.this, H2MainActivity.class);
                startActivity(intent);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        }, 1000);
    }
}
