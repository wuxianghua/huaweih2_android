package com.palmap.demo.huaweih2.functionActivity;

import android.os.Bundle;

import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.view.TitleBar;

public class FootPrintActivity extends BaseActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print2);

        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.show(null, "足迹", null);
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
