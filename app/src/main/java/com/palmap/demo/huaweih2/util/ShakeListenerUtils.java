package com.palmap.demo.huaweih2.util;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by eric3 on 2016/10/19.
 * 摇一摇
 */

public class ShakeListenerUtils implements SensorEventListener
{
  private Activity context;
  private OnShakeListener onShakeListener;

  public ShakeListenerUtils(Activity context,OnShakeListener onShakeListener)
  {
    super();
    this.context = context;
    this.onShakeListener = onShakeListener;
  }

  @Override
  public void onSensorChanged(SensorEvent event)
  {
    int sensorType = event.sensor.getType();
    //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
    float[] values = event.values;

    if (sensorType == Sensor.TYPE_ACCELEROMETER)
    {

            /*正常情况下，任意轴数值最大就在9.8~10之间，只有在突然摇动手机
              的时候，瞬时加速度才会突然增大或减少。   监听任一轴的加速度大于15即可
            */
      if ((Math.abs(values[0]) > 15 || Math.abs(values[1]) > 15 || Math
          .abs(values[2]) > 15))
      {
        VibratorUtil.Vibrate(context, 500);   //震动1000ms
        onShakeListener.onShake();
//        DataProviderCenter.getInstance().
      }
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy)
  {
    //当传感器精度改变时回调该方法，Do nothing.
  }

  public interface OnShakeListener{
    void onShake();
  }

}