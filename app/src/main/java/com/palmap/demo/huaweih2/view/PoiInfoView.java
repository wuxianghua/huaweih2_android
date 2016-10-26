//package com.palmap.demo.huaweih2.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
///**
// * Created by eric3 on 2016/10/22.
// */
//
//public class PoiInfoView extends LinearLayout implements View.OnClickListener{
//  TextView tv_title;
//  ImageView iv_poi;
//  TextView tv_detail;
//  ImageView iv_close;
//
//  public PoiInfoView(Context context, AttributeSet attrs)
//  {
//    super(context,attrs);
//
//    mImage = new ImageView(context,attrs);
//    mImage.setPadding(0,0,0,0);
//    mText = new TextView(context,attrs);
//    //mText.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
//    //  mText.setGravity(android.view.Gravity.CENTER_VERTICAL);
//    mText.setPadding(0,0,0,0);
//
//
//    setClickable(true);
//    setFocusable(true);
//    setBackgroundResource(android.R.drawable.btn_default);
//    setOrientation(LinearLayout.VERTICAL);
//    addView(mImage);
//    addView(mText);
//  }
//
//  @Override
//  public void onClick(View v) {
//
//  }
//}
