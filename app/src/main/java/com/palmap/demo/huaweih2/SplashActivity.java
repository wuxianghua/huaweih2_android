package com.palmap.demo.huaweih2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.palmap.core.MapEngine;
import com.palmap.core.data.PlanarGraph;

import java.util.Timer;
import java.util.TimerTask;

import static com.palmap.core.util.UtilsKt.loadFromAsset;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView tvVersionName = (TextView) findViewById(R.id.tvVersionName);
        tvVersionName.setText(getAppInfo());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
//        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                HuaWeiH2Application.parkData = new PlanarGraph(loadFromAsset(SplashActivity.this,"h2Data.json"),16);
                HuaWeiH2Application.parkData.resolveData();
                HuaWeiH2Application.parkData.loadStyle(
                        MapEngine.INSTANCE.getRenderableByName("default").getRenderer()
                );
                Intent intent = new Intent(SplashActivity.this, H2MainActivity.class);
                startActivity(intent);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        }, 2000);
    }

    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return TextUtils.isEmpty(versionName) ? null : "v" + versionName;
        } catch (Exception e) {
            return null;
        }
    }

}
