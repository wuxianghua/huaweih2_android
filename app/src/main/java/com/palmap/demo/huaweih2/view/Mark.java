package com.palmap.demo.huaweih2.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.palmap.demo.huaweih2.R;
import com.palmaplus.nagrand.view.overlay.OverlayCell;

/**
 * Created by eric3 on 2016/10/11.
 */
public class Mark extends LinearLayout implements OverlayCell {
  private ImageView mIconView;
  public final static int NORMAL = 0;
  public final static int START = 1;
  public final static int END = 2;
  private int type ;
//  private TextView mPosX;
//  private TextView mPosY;
//  private TextView mPosId;l

  private double[] mGeoCoordinate;
//  private int mId;

  public Mark(Context context) {
    super(context);

    initView();
  }

  public Mark(Context context,int type) {
    super(context);

//    this.mId = id;
    this.type=type;
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.item_mark, this);
    mIconView = (ImageView) findViewById(R.id.icon_mark);
    if (type==END)
      mIconView.setBackgroundResource(R.drawable.ico_map_end);
    else if (type==START)
      mIconView.setBackgroundResource(R.drawable.ico_map_start);
    else
      mIconView.setBackgroundResource(R.drawable.ico_map_marker);

//    mPosX = (TextView) findViewById(R.id.mark_x);
//    mPosY = (TextView) findViewById(R.id.mark_y);
//    mPosId = (TextView) findViewById(R.id.mark_id);
//    mPosId.setText(String.valueOf(mId));
  }

  public void setMark(int id, double x, double y){
//    mId = id;
//    mPosId.setText(String.valueOf(id));
//    mPosX.setText("x: " + x);
//    mPosY.setText("y: " + y);
  }

  public void setMark(int id, double x, double y, int resId) {
//    mId = id;
//    mPosId.setText(String.valueOf(id));
//    mPosX.setText("x: " + x);
//    mPosY.setText("y: " + y);
    mIconView.setBackgroundResource(resId);
  }

  @Override
  public void init(double[] doubles) { // 用于接受一个世界坐标，必须要有
    mGeoCoordinate = doubles;
  }

  @Override
  public double[] getGeoCoordinate() { // 用于返回世界坐标，必须要有
    return mGeoCoordinate;
  }


  //用于定位覆盖物位置，这个接口会由SDK调用，
//最终参数是覆盖物添加世界坐标转换后的屏幕坐标，
//这个接口在做地图交互是会一直调用，如果你想自己控制覆盖物的显示位置，可以自己自定义这个接口
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void position(double[] doubles) {
    setX((float) doubles[0] - getWidth() / 2);
    setY((float) doubles[1] - getHeight());
  }

}