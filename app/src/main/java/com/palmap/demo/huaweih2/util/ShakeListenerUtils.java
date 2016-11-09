package com.palmap.demo.huaweih2.util;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

/**
 * Created by eric3 on 2016/10/19.
 * 摇一摇
 */

public class ShakeListenerUtils implements SensorEventListener {
    private Activity context;
    private OnShakeListener onShakeListener;
    public static boolean isTooShort = false;//保证两次间隔
    public static float MAX_VAL = 21f;
//    public static float MAX_VAL = 15f;

    public ShakeListenerUtils(Activity context, OnShakeListener onShakeListener) {
        super();
        this.context = context;
        this.onShakeListener = onShakeListener;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;

        if (sensorType == Sensor.TYPE_ACCELEROMETER) {

            /*正常情况下，任意轴数值最大就在9.8~10之间，只有在突然摇动手机
              的时候，瞬时加速度才会突然增大或减少。   监听任一轴的加速度大于15即可
            */
            double len = Math.sqrt(values[0]*values[0]+values[1]*values[1]+values[2]*values[2]);
            if (len>MAX_VAL) {
                if (isTooShort) {
                    Log.e("eric", "isTooShort");
                    isTooShort = false;
                    return;
                }

                isTooShort = true;
                VibratorUtil.Vibrate(context, 500);   //震动1000ms
                onShakeListener.onShake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变时回调该方法，Do nothing.
    }

    public interface OnShakeListener {
        void onShake();
    }

}