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

public class FindCarActivity extends BaseActivity implements SensorEventListener {
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
    private WifiScanReceiver mWifiScanReceiver;
    private WifiManager mWifiManager;
    private List<AccelerometerModel> accelerometerModels;
    private List<GyroModel> gyroModels;
    private List<MagnetometerModel> magnetometerModels;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    private int count1 = 0;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            count1++;
            if (accelerometerModels == null) {
                accelerometerModels = new ArrayList<>();
            }
            if (gyroModels == null) {
                gyroModels = new ArrayList<>();
            }
            if (magnetometerModels == null) {
                magnetometerModels = new ArrayList<>();
            }
            magnetometerModels.add(magnetometerModel);
            gyroModels.add(gyroModel);
            accelerometerModels.add(accelerometerModel);
            Message message = Message.obtain();
            handler.sendMessageDelayed(message,10);
            if (count1 == 10) {
                if (gyroModels == null||magnetometerModels == null || accelerometerModels == null|| wifiLocationManager == null) {
                    return;
                }
                SensorModel sensorModel = new SensorModel();
                sensorModel.gyro = gyroModels;
                sensorModel.magnetometer = magnetometerModels;
                sensorModel.accelerometer = accelerometerModels;
                sensorModel.time = System.currentTimeMillis();
                getPositionData();
                wifiLocationManager.setSensorData(gson.toJson(sensorModel));
                Logger.d("sensorModel"+gson.toJson(sensorModel));
                magnetometerModels.clear();
                gyroModels.clear();
                accelerometerModels.clear();
                count1 = 0;
            }
        }
    };

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
        mWifiManager = (WifiManager)getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.settings.setLoadWithOverviewMode(true);
        findCarWebView.loadUrl("http://misc.ipalmap.com/hwpk/");
        //findCarWebView.loadUrl("http://html5test.com/");
        positionFeature = new PositionFeature();
        positionGeometry = new PositionGeometry();
        positionProperty = new PositionProperty();
        positionData = new PositionData();
        mWifiData = new WifiData();
        mWifiPositonData = new WifiPositionData();
        apDatas = new ArrayList<ApData>();
        positionData.features = new ArrayList<>();
        gson = new Gson();
        wifiLocationManager = new WifiLocationManager();
        Message msg = Message.obtain();
        handler.sendMessageDelayed(msg,200);
    }

    @Override
    protected void onStart() {
        super.onStart();
        doStart();
    }

    private void doStart() {
        mWifiScanReceiver = new WifiScanReceiver();
        registerReceiver(mWifiScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        mWifiManager.startScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, 0);
        mSensorManager.registerListener(this, mGyroscope, 0);
        mSensorManager.registerListener(this, mField, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWifiScanReceiver != null) {
            unregisterReceiver(mWifiScanReceiver);
            mWifiScanReceiver = null;
        }
    }

    class WifiScanReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            List<ScanResult> scanResults = mWifiManager.getScanResults();
            sendPositionDataToJS(scanResults);
            mWifiManager.startScan();
        }

    }
    public void sendPositionDataToJS(List<ScanResult> list) {
        apDatas.clear();
        for (int i = 0; i < list.size(); i++) {
            apData = new ApData();
            apData.mac = list.get(i).BSSID;
            apData.rssi = list.get(i).level;
            apData.ssid = "Huawei-Employee";
            apDatas.add(apData);
        }
        mWifiData.time = System.currentTimeMillis();
        mWifiData.arr = apDatas;
        mWifiPositonData.building_name = null;
        mWifiPositonData.time = System.currentTimeMillis();
        mWifiPositonData.wifi_data = mWifiData;
        wifiLocationManager.setWifiData(gson.toJson(mWifiPositonData));
        Log.e(TAG,gson.toJson(mWifiPositonData));
    }

    private void getPositionData() {
        Logger.d("wifi data finish");
        pos=wifiLocationManager.getPos();
        Logger.d("get data wifi"+pos);
        try {
            currentPosition = gson.fromJson(pos, CurrentPositionData.class);
            positionProperty.floor_id = 1261980;
            positionGeometry.coordinates = new double[]{currentPosition.current_position.x, currentPosition.current_position.y};
            positionFeature.geometry = positionGeometry;
            positionFeature.properties = positionProperty;
            positionData.features.clear();
            positionData.features.add(positionFeature);
            String s = gson.toJson(positionData);
            Logger.d("positionData"+currentPosition.current_position.x+currentPosition.current_position.y);
            findCarWebView.loadUrl("javascript: locatePoint('" + s + "')");
        }catch (Exception e) {
            e.printStackTrace();
            Logger.d("error"+e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
    AccelerometerModel accelerometerModel;
    GyroModel gyroModel;
    MagnetometerModel magnetometerModel;
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerModel = new AccelerometerModel();
            accelerometerValues = event.values;
            accelerometerModel.time = System.currentTimeMillis();
            accelerometerModel.x = event.values[0];
            accelerometerModel.y = event.values[1];
            accelerometerModel.z = event.values[2];
        }else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroModel = new GyroModel();
            gyroModel.time = System.currentTimeMillis();
            gyroModel.x = event.values[0];
            gyroModel.y = event.values[1];
            gyroModel.z = event.values[2];
        }else {
            magnetometerModel = new MagnetometerModel();
            magneticFieldValues = event.values;
            magnetometerModel.time = System.currentTimeMillis();
            magnetometerModel.x = event.values[0];
            magnetometerModel.y = event.values[1];
            magnetometerModel.z = event.values[2];
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
        findCarWebView.loadUrl("javascript: angleOfNorth('" + degree + "')");
    }
}
