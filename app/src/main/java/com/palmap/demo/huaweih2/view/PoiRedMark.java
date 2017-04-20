package com.palmap.demo.huaweih2.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.palmap.demo.huaweih2.R;
import com.palmaplus.nagrand.view.overlay.OverlayCell;

import static com.palmap.demo.huaweih2.other.Constant.H2大厅;
import static com.palmap.demo.huaweih2.other.Constant.ICS办公区;
import static com.palmap.demo.huaweih2.other.Constant.ICS实验室;
import static com.palmap.demo.huaweih2.other.Constant.会议室;

/**
 * Created by eric3 on 2016/10/23.
 */

public class PoiRedMark extends LinearLayout implements OverlayCell{
  private ImageView mIconView;
  private String name;
  private Context context;
  private OnClickListenerForMark onClickListenerForMark;

  private long floorId;

  private double[] mGeoCoordinate;

  public PoiRedMark(Context context,long floorId) {
    super(context);
    this.floorId = floorId;
    this.context = context;
    initView();
  }

  public PoiRedMark(Context context,long floorId, String name,OnClickListenerForMark onClickListenerForMark) {
    super(context);
    this.floorId = floorId;
    this.onClickListenerForMark = onClickListenerForMark;
    this.context=context;
    this.name = name;
    initView();
  }

  public String getName() {
    return name;
  }

  private void initView() {
    LayoutInflater.from(getContext()).inflate(R.layout.poiinfo_mark, this);
    mIconView = (ImageView) findViewById(R.id.poi_img);
    if (H2大厅.equals(name)) {
      mIconView.setBackgroundResource(R.drawable.ico_map_1_red);
    } else if (会议室.equals(name)) {
      mIconView.setBackgroundResource(R.drawable.ico_map_2_red);
    } else if (ICS办公区.contains(name)) {
      mIconView.setBackgroundResource(R.drawable.ico_map_4_red);
    } else if (name.contains(ICS实验室)) {
      mIconView.setBackgroundResource(R.drawable.ico_map_3_red);
    }

    mIconView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        onClickListenerForMark.onMarkSelect(PoiRedMark.this);
      }
    });

  }

  public void setMark(int id, double x, double y){
  }

  public void setMark(int id, double x, double y, int resId) {
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

  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void position(double[] doubles) {
    setX((float) doubles[0] - getWidth() / 2);
    setY((float) doubles[1] - getHeight() );
  }

  @Override
  public long getFloorId() {
    return this.floorId;
  }


  public interface OnClickListenerForMark{
    void onMarkSelect(PoiRedMark mark);
  }

}
