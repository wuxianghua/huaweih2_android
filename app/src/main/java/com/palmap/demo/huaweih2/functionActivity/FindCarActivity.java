package com.palmap.demo.huaweih2.functionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.H2MainActivity;
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

                Intent intent = new Intent(FindCarActivity.this, H2MainActivity.class);
//                intent.putExtra("onFindCarBack", true);
                startActivity(intent);
                finish();
            }

            @Override
            public void onRight() {
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, H2MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FindCarActivity.this, H2MainActivity.class);
//        intent.putExtra("onFindCarBack", true);
        startActivity(intent);
        finish();

    }
}
