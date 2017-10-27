package com.palmap.huawei;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.model.ApData;
import com.palmap.demo.huaweih2.model.CurrentPositionData;
import com.palmap.demo.huaweih2.model.PositionData;
import com.palmap.demo.huaweih2.model.PositionFeature;
import com.palmap.demo.huaweih2.model.PositionGeometry;
import com.palmap.demo.huaweih2.model.PositionProperty;
import com.palmap.demo.huaweih2.model.WifiData;
import com.palmap.demo.huaweih2.model.WifiPositionData;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;

public class FindCarActivity extends BaseActivity{
    private static final String TAG = FindCarActivity.class.getSimpleName();
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
    private WifiScanReceiver mWifiScanReceiver;
    private WifiManager mWifiManager;
    private CollectProvider instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print);
        findCarWebView = (WebView) findViewById(R.id.find_car_web_view);
        findCarWebView.setWebViewClient(new WebViewClient());
        settings = findCarWebView.getSettings();
        this.settings.setJavaScriptEnabled(true);
        this.settings.setDomStorageEnabled(true);
        this.settings.setUseWideViewPort(true);
        mWifiManager = (WifiManager)getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.settings.setLoadWithOverviewMode(true);
        findCarWebView.loadUrl("http://misc.ipalmap.com/hwpk/");
        instance = CollectProvider.getInstance(getApplicationContext());
        positionFeature = new PositionFeature();
        positionGeometry = new PositionGeometry();
        positionProperty = new PositionProperty();
        positionData = new PositionData();
        mWifiData = new WifiData();
        mWifiPositonData = new WifiPositionData();
        apDatas = new ArrayList<>();
        positionData.features = new ArrayList<>();
        gson = new Gson();
        wifiLocationManager = new WifiLocationManager();
        doStart();
        instance.setSensorDataCollectionListener(new CollectProvider.SensorDataCollectionListener() {
            @Override
            public void collectionSensorData(String data) {
                getPositionData();
                wifiLocationManager.setSensorData(data);
            }
        });
        instance.start();
    }

    private void doStart() {
        mWifiScanReceiver = new WifiScanReceiver();
        registerReceiver(mWifiScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        mWifiManager.startScan();
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
        String wifiData = gson.toJson(mWifiPositonData);
        wifiLocationManager.setWifiData(wifiData);
    }

    private void getPositionData() {
        pos=wifiLocationManager.getPos();
        try {
            currentPosition = gson.fromJson(pos, CurrentPositionData.class);
            positionProperty.floor_id = 1261980;
            positionGeometry.coordinates = new double[]{currentPosition.current_position.x, currentPosition.current_position.y};
            positionFeature.geometry = positionGeometry;
            positionFeature.properties = positionProperty;
            positionData.features.clear();
            positionData.features.add(positionFeature);
            String s = gson.toJson(positionData);
            findCarWebView.loadUrl("javascript: locatePoint('" + s + "')");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWifiScanReceiver != null) {
            unregisterReceiver(mWifiScanReceiver);
            mWifiScanReceiver = null;
        }
        instance.stop();
    }
}
