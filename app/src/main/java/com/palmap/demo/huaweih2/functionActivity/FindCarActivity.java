package com.palmap.demo.huaweih2.functionActivity;

import android.content.Intent;
import android.os.Bundle;

import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.MainActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.view.TitleBar;

public class FindCarActivity extends BaseActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print);

        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.show(null, "寻车", null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {

                Intent intent = new Intent(FindCarActivity.this, MainActivity.class);
                intent.putExtra("onFindCarBack", true);
                startActivity(intent);
                finish();
            }

            @Override
            public void onRight() {
            }
        });

    }
}
