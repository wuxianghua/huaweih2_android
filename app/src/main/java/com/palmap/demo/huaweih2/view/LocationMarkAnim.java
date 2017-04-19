package com.palmap.demo.huaweih2.view;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.palmap.demo.huaweih2.MainActivity;
import com.palmap.demo.huaweih2.R;
import com.palmaplus.nagrand.core.Types;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.overlay.OverlayCell;

/**
 * Created by eric3 on 2016/10/24.
 */

public class LocationMarkAnim extends LinearLayout implements OverlayCell{//} ,SensorEventListener {

  private MainActivity context;
  private double[] position;//世界坐标
  protected MapView mapView;
  public static float currentDegree = 0f;//方向角
  public static float currentMapDegree = 0f;//地图方向角
  private float x;//屏幕坐标
  private float y;//
//  private static  int debugout = 0;

  private ObjectAnimator objectAnimatorX, objectAnimatorY;

  private long floorId;

  public LocationMarkAnim(Context context, MapView mapView) {
    super(context);
    this.mapView = mapView;

    this.context = (MainActivity) context;
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.location_mark_anim,null);
    addView(view);
//    setLayoutParams(new ImageView.LayoutParams(5, 5));
//    setBackgroundResource(R.drawable.ico_map_location);
//    LayoutInflater.from(getContext()).inflate(R.layout.location_mark, this);
//    mIconView = (ImageView) findViewById(R.id.location_img);


//    setBackground(getResources().getDrawable(R.drawable.location_mark));
//    setImageResource(R.drawable.location_mark);

//    // 传感器管理器
//    SensorManager sm = (SensorManager) context.getSystemService(SENSOR_SERVICE);
//    // 注册传感器(Sensor.TYPE_ORIENTATION(方向传感器);SENSOR_DELAY_FASTEST(0毫秒延迟);
//    // SENSOR_DELAY_GAME(20,000毫秒延迟)、SENSOR_DELAY_UI(60,000毫秒延迟))
//    sm.registerListener(this,
//        sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
//        SensorManager.SENSOR_DELAY_UI);
  }

  @Override
  public void init(double[] doubles) {
    position = doubles;
    Types.Point point = mapView.converToScreenCoordinate(doubles[0],doubles[1]);
    x = (float) point.x;
    y = (float) point.y;
  }

//  /**
//   * 屏幕坐标
//   *
//   * @param x
//   * @param y
//   */
//  private void init(double x, double y) {
//    Types.Point point = mapView.converToWorldCoordinate(x, y);
//    init(new double[]{point.x, point.y});
//  }

  public void animTo(double x, double y) {//传入世界坐标
    mapView.addOverlay(this);
    if (null == position || (getX() == 0 && getY() == 0)) {
      return;
    }
    Types.Point point = mapView.converToScreenCoordinate(x, y);

    cancelAnim();
    objectAnimatorX = ObjectAnimator.ofFloat(this, "X", (float) (point.x - getWidth() / 2)).setDuration(1000);
    objectAnimatorX.start();
    objectAnimatorY = ObjectAnimator.ofFloat(this, "Y", (float) (point.y - getHeight() / 2)).setDuration(1000);
    objectAnimatorY.start();

//    Types.Point point = mapView.converToWorldCoordinate(x,y);
//    debugout ++ ;
//    if (Config.isDebug && debugout % 3 == 0){
//      double distance = Math.sqrt((Config.xy[0]-point.x)*(Config.xy[0]-point.x)+(Config.xy[1]-point.y)*(Config.xy[1]-point.y));
////            Log.d("eric debug","x = "+Config.xy[0]+"   y = "+Config.xy[1]);
//      Log.d("eric debug","distance = "+distance);
//      if (Config.debugShowDistance) {
//        java.text.DecimalFormat df = new  java.text.DecimalFormat("#.00");
//        Toast.makeText(getContext(), "定位点距mark点：" + df.format(distance)+"m", Toast.LENGTH_SHORT).show();
//      }
//    }
  }
//  public void refreshLocation(Coordinate coordinate) {
//    double x = coordinate.getX();
//    double y = coordinate.getY();
//    init(new double[]{x, y});
//  }

  public double getDistance(double x, double y){
    if (position==null)
      return 0;

    double distance = Math.sqrt((position[0]-x)*(position[0]-x)+(position[1]-y)*(position[1]-y));
    return distance;
  }


//  @Override
//  public void setRotation(float rotation) {
//    int angle = (int) (180 * rotation / Math.PI);
//    super.setRotation(angle);
//  }

  @Override
  public double[] getGeoCoordinate() {
    return position;
  }

  //用于定位覆盖物位置，这个接口会由SDK调用，
//最终参数是覆盖物添加世界坐标转换后的屏幕坐标，
//这个接口在做地图交互是会一直调用，如果你想自己控制覆盖物的显示位置，可以自己自定义这个接口
  @TargetApi(Build.VERSION_CODES.HONEYCOMB)
  @Override
  public void position(double[] doubles) {
    cancelAnim();
    x = (float) doubles[0];
    y = (float) doubles[1] ;
    setX(x - (float) (getWidth() / 2));
    setY(y - (float) (getHeight() / 2));
  }

  private void cancelAnim() {
    if (objectAnimatorY != null&& objectAnimatorX!=null) {
      objectAnimatorX.cancel();
      objectAnimatorY.cancel();
    }
  }

  public long getFloorId() {
    return floorId;
  }

  public void setFloorId(long floorId) {
    this.floorId = floorId;
  }
////static int i = 0;
//  @Override
//  public void onSensorChanged(SensorEvent event) {
////    if (i++<100)
////      return;
////
////    i=0;
//    if (context.fragmentMap.mCurrentFloor== Constant.FLOOR_ID_B1||context.fragmentMap.isLoadingMap)
//      return;
//
//    if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
//      float degree = event.values[0];
//            /*
//            RotateAnimation类：旋转变化动画类
//            参数说明:
//            fromDegrees：旋转的开始角度。
//            toDegrees：旋转的结束角度。
//            pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
//            pivotXValue：X坐标的伸缩值。
//            pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
//            pivotYValue：Y坐标的伸缩值
//            */
//
//      float todegree = Math.abs(degree-currentMapDegree)%360;
////      float fromDegree =
////      toDegree;
////      if
//      RotateAnimation ra = new RotateAnimation(currentDegree, todegree,//减去地图偏转角
//          Animation.ABSOLUTE, x ,
//          Animation.ABSOLUTE, y );
//      //旋转过程持续时间
//      ra.setDuration(10);
//      //罗盘图片使用旋转动画
//      this.startAnimation(ra);
//      currentDegree = todegree;
//    }
//  }
//
//  @Override
//  public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//  }
}

