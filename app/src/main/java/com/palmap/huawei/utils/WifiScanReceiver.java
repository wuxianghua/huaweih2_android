package com.palmap.huawei.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.palmap.demo.huaweih2.model.ApData;
import com.palmap.demo.huaweih2.model.WifiData;
import com.palmap.demo.huaweih2.model.WifiPositionData;
import com.palmap.huawei.WifiLocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/23/023.
 */

public class WifiScanReceiver extends BroadcastReceiver {

    private WifiManager mWifiManager;
    private ApData apData;
    private WifiData mWifiData;
    private WifiPositionData mWifiPositonData;
    private List<ApData> apDatas;
    private WifiLocationManager wifiLocationManager;
    private Gson gson;

    public WifiScanReceiver(WifiManager wifiManager) {
        mWifiManager = wifiManager;
        mWifiData = new WifiData();
        mWifiPositonData = new WifiPositionData();
        apDatas = new ArrayList<>();
        gson = new Gson();
        wifiLocationManager = new WifiLocationManager();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ThreadManager.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                List<ScanResult> scanResults = mWifiManager.getScanResults();
                sendPositionDataToJS(scanResults);
                mWifiManager.startScan();
            }
        });
    }

    public  void sendPositionDataToJS(List<ScanResult> list) {
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

}
