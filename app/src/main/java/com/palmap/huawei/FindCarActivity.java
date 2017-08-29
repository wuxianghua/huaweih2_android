package com.palmap.huawei;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.model.AccelerometerModel;
import com.palmap.demo.huaweih2.model.ApData;
import com.palmap.demo.huaweih2.model.CurrentPositionData;
import com.palmap.demo.huaweih2.model.GyroModel;
import com.palmap.demo.huaweih2.model.MagnetometerModel;
import com.palmap.demo.huaweih2.model.PositionData;
import com.palmap.demo.huaweih2.model.PositionFeature;
import com.palmap.demo.huaweih2.model.PositionGeometry;
import com.palmap.demo.huaweih2.model.PositionProperty;
import com.palmap.demo.huaweih2.model.SensorModel;
import com.palmap.demo.huaweih2.model.WifiData;
import com.palmap.demo.huaweih2.model.WifiPositionData;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bupt.sse309.locsdk.DefaultLocClient;
import cn.bupt.sse309.locsdk.LocClient;

public class FindCarActivity extends BaseActivity implements SensorEventListener{
    private static final String TAG = "FindCarActivity";
    private WebView findCarWebView;
    private WebSettings settings;
    private ApData apData;
    private WifiData mWifiData;
    private WifiPositionData mWifiPositonData;
    private List<ApData> apDatas;
    private PositionFeature positionFeature;
    private PositionGeometry positionGeometry;
    private PositionProperty positionProperty;
    private PositionData positionData;
    private String pos;
    private CurrentPositionData currentPosition;
    private WifiLocationManager wifiLocationManager;
    private Gson gson;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mGyroscope;
    private Sensor mField;
    private TextView tv;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        findCarWebView = (WebView) findViewById(R.id.find_car_web_view);
        findCarWebView.setWebViewClient(new WebViewClient());
        settings = findCarWebView.getSettings();
        this.settings.setJavaScriptEnabled(true);
        this.settings.setDomStorageEnabled(true);
        this.settings.setUseWideViewPort(true);
        this.settings.setLoadWithOverviewMode(true);
        findCarWebView.loadUrl("http://misc.ipalmap.com/hwpk/");
        tv = (TextView) findViewById(R.id.textView);
        positionFeature = new PositionFeature();
        positionGeometry = new PositionGeometry();
        positionProperty = new PositionProperty();
        positionData = new PositionData();
        mWifiData = new WifiData();
        mWifiPositonData = new WifiPositionData();
        apDatas = new ArrayList<ApData>();
        positionData.features = new ArrayList<>();
        gson = new Gson();
        registerSensor();
        wifiLocationManager = new WifiLocationManager();
        LocClient client = new DefaultLocClient(this, "YjU5NjFkMjItNDhlZC00OGNjLTk2N2UtMmNlZmE5YTUyMWU2", "QwZzlf4eXvuhNTAUvi5BDaD9E73aAMZE0z8uFMUrhvU");
        client.setOnLocResultReceivedListener(new LocClient.OnLocResultReceivedListener() {
            @Override
            public void OnSuccess(int type, Map<String, String> result) {
                //楼宇ID
                String buildingId = result.get("building_id");
                //楼层
                int floor = Integer.parseInt(result.get("floor"));
                //发送报文时的时间戳
                long timestamp = Long.parseLong(result.get("timestamp"));
                tv.setText("时间"+(System.currentTimeMillis()-timestamp));
                switch (type) {
                    case LocClient.TYPE_FLOOR:
                        //楼层切换时的结果
                        break;
                    case LocClient.TYPE_LOC:
                        //定位成功返回的结果
                        double x = Float.parseFloat(result.get("x"));
                        double y = Float.parseFloat(result.get("y"));
                        Logger.d("positionData"+"x"+x+""+"y"+y);
                        x =  (12697074.245 + (x/7.850));
                        y =  (2588966.542 - (y/7.850));
                        positionProperty.floor_id = 1261980;
                        positionGeometry.coordinates = new double[]{x, y};
                        positionFeature.geometry = positionGeometry;
                        positionFeature.properties = positionProperty;
                        positionData.features.clear();
                        positionData.features.add(positionFeature);
                        String s = gson.toJson(positionData);
                        findCarWebView.loadUrl("javascript: locatePoint('" + s + "')");
                        break;
                }
            }

            @Override
            public void OnFailed(String code, String message) {
                Logger.d("positionFail"+message);
                double x = 783.267333984375;
                double y = 282.28619384765625;
                Logger.d("positionData"+"x"+x+""+"y"+y);
                x =  (12697074.245 + (x/7.850));
                y =  (2588966.542 - (y/7.850));
                positionProperty.floor_id = 1261980;
                positionGeometry.coordinates = new double[]{x, y};
                positionFeature.geometry = positionGeometry;
                positionFeature.properties = positionProperty;
                positionData.features.clear();
                positionData.features.add(positionFeature);
                String s = gson.toJson(positionData);
                findCarWebView.loadUrl("javascript: locatePoint('" + s + "')");
                Log.e(TAG,message);
            }
        });
        client.start();
    }

    private void registerSensor() {
        mSensorManager.registerListener(this, mAccelerometer, 0);
        mSensorManager.registerListener(this, mGyroscope, 0);
        mSensorManager.registerListener(this, mField, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = event.values;
        }else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
        }else {
            magneticFieldValues = event.values;
        }
        //Log.e(TAG,gson.toJson(sensorModel));
        calculateOrientation();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    int degree;
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[0] = values[0] < 0 ? 360+values[0]:values[0];
        degree = (int) values[0];
        Log.e(TAG,degree+"");
        findCarWebView.loadUrl("javascript: angleOfNorth('" + degree + "')");
    }
}
