package com.palmap.demo.huaweih2.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/7/007.
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastReceiver";
    private static WifiManager mWifiManager;

    public static void setmWifiManager(WifiManager wifiManager) {
        mWifiManager = wifiManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.getAction();
        mWifiManager.startScan();
        Log.e(TAG,"你好哈哈哈");
    }
}
