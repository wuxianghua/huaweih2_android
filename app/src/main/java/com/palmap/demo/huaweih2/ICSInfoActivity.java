package com.palmap.demo.huaweih2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.palmap.demo.huaweih2.model.ICSModel;
import com.palmap.demo.huaweih2.view.TitleBar;

public class ICSInfoActivity extends BaseActivity {

    TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icsinfo);
        titleBar = (TitleBar)findViewById(R.id.title_bar);
        titleBar.show(null, "ICS解决方案", null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                onBackPressed();
            }

            @Override
            public void onRight() {
            }
        });

        try {
            ICSModel icsModel = getIntent().getExtras().getParcelable(ICSModel.class.getSimpleName());
            ((TextView) findViewById(R.id.tv_title)).setText(icsModel.getTitle());
            ((TextView) findViewById(R.id.tv_description)).setText(icsModel.getDescription());
            ((ImageView) findViewById(R.id.imageView)).setImageResource(icsModel.getResId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
