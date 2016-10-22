package com.palmap.demo.huaweih2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
/**
 * Created by 王天明 on 2014/6/18
 * 自动回收资源ImageView
 */
public class RecyclableImageView extends ImageView {
    public RecyclableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecyclableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclableImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}