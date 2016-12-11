package com.palmap.demo.huaweih2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.model.ICSModel;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.util.List;

public class ICSInfoActivity extends BaseActivity {

    TitleBar titleBar;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icsinfo);

        container = (LinearLayout) findViewById(R.id.container);
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

            List<ICSModel> icsModelList = getIntent().getExtras().getParcelableArrayList(ICSModel.class.getSimpleName());
            for (ICSModel icsModel:icsModelList) {
                View view = LayoutInflater.from(this).inflate(R.layout.ics_info_item,null);
                ((TextView) view.findViewById(R.id.tv_title)).setText(icsModel.getTitle());
                ((TextView) view.findViewById(R.id.tv_description)).setText(icsModel.getDescription());
                ((ImageView) view.findViewById(R.id.imageView)).setImageResource(icsModel.getResId());
                container.addView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
