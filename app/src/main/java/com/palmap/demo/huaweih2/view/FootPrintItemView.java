package com.palmap.demo.huaweih2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;

/**
 * Created by 王天明 on 2016/10/22.
 */

public class FootPrintItemView extends LinearLayout {

    private ImageView image1;
    private TextView tvName, tvCount;

    public FootPrintItemView(Context context) {
        this(context, null);
    }

    public FootPrintItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_foot_print_itemview, this);
        bindView();
    }

    private void bindView() {
        image1 = (ImageView) findViewById(R.id.image1);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvCount = (TextView) findViewById(R.id.tv_count);
    }

    public void setImageResource(int res) {
        image1.setImageResource(res);
    }

    public void setName(String text) {
        tvName.setText(text);
    }

    public void setCount(int count) {
        tvCount.setText("" + count);
    }

}
