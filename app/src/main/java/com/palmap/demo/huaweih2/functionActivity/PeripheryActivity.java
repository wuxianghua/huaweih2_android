package com.palmap.demo.huaweih2.functionActivity;

import android.os.Bundle;

import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.view.TitleBar;

/**
 * 周边
 */
public class PeripheryActivity extends BaseActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periphery);

        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.show(null, "周边", null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                onBackPressed();
            }

            @Override
            public void onRight() {
            }
        });

    }
}
