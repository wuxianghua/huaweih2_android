package com.palmap.demo.huaweih2.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;
import com.palmaplus.nagrand.view.overlay.OverlayCell;

import static com.palmap.demo.huaweih2.other.Constant.H2大厅;
import static com.palmap.demo.huaweih2.other.Constant.ICS办公区;
import static com.palmap.demo.huaweih2.other.Constant.ICS实验室;
import static com.palmap.demo.huaweih2.other.Constant.会议室;

/**
 * Created by eric3 on 2016/10/24.
 */

public class PoiBlackMark extends LinearLayout implements OverlayCell,View.OnClickListener {
  private TextView tv_name;
  private String name;
//  public final static int NORMAL = 0;
//  public final static int START = 1;
//  public final static int END = 2;
//  private int type ;
//  private TextView mPosX;
//  private TextView mPosY;
//  private TextView mPosId;l

  private double[] mGeoCoordinate;
//  private int mId;

  public PoiBlackMark(Context context) {
    super(context);

    initView();
  }

  public PoiBlackMark(Context context,String name) {
    super(context);

    this.name = name;
    initView();
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.poiblack_mark, this);
    tv_name = (TextView) findViewById(R.id.name);
    if (H2大厅.equals(name)) {
      tv_name.setText(H2大厅);
    } else if (会议室.equals(name)) {
      tv_name.setText(会议室);
    } else if (ICS办公区.equals(name)) {
      tv_name.setText(ICS办公区);
    } else if (name.contains(ICS实验室)) {
      tv_name.setText(ICS实验室);
    }

  }

  public void setMark(int id, double x, double y){
//    mId = id;
//    mPosId.setText(String.valueOf(id));
//    mPosX.setText("x: " + x);
//    mPosY.setText("y: " + y);
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
    setY((float) doubles[1] - getHeight() );
  }

  @Override
  public void onClick(View v) {
    if (H2大厅.equals(name)) {

    } else if (会议室.equals(name)) {

    } else if (ICS办公区.equals(name)) {

    } else if (name.contains(ICS实验室)) {

    }
  }
}
