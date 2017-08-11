package com.palmap.demo.huaweih2.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2/002.
 */

public class WifiAdmin {
    //定义一个WifiManager对象
    private WifiManager mWifiManager;
    //定义一个WifiInfo对象
    private WifiInfo mWifiInfo;
    //扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    //网络连接列表
    private List<WifiConfiguration> mWifiConfigurations;
    WifiManager.WifiLock wifiLock;

    public WifiAdmin(Context context) {
        //取得wifiManager对象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //取得wifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    //打开wifi
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    //关闭wifi
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    //检查当前wifi状态
    public int checkState() {
        return mWifiManager.getWifiState();
    }

    //锁定wifiLock
    public void acquireWifiLock() {
        wifiLock.acquire();
    }

    //解锁WiFiLock
    public void releaseWifiLock() {
        //判断是否锁定
        if (wifiLock.isHeld()) {
            wifiLock.acquire();
        }
    }

    //创建一个wifiLock
    public void createWifiLock() {
        wifiLock = mWifiManager.createWifiLock("position");
    }

    //得到配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfigurations;
    }

    //指定配置好的网络进行连接
    public void connectionConfiguration(int index) {
        if (index > mWifiConfigurations.size()) {
            return;
        }
        //连接配置好指定ID的网络
        mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId,true);
    }

    public void startScan() {
        mWifiManager.startScan();
        //NetworkConnectChangedReceiver.setmWifiManager(mWifiManager);
        //得到扫描结果
        //得到配置好的网络连接
        //mWifiConfigurations = mWifiManager.getConfiguredNetworks();
    }

    //得到网络列表
    public List<ScanResult> getWifiList() {
        mWifiList = mWifiManager.getScanResults();
        return mWifiList;
    }



    //查看扫描结果
    public StringBuffer lookUpScan() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < mWifiList.size();i++) {
            sb.append("Index_"+new Integer(i+1).toString()+":");
            //将ScanResult信息转换成一个字符串包
            //其中包括：BSSID、SSID、capabilities、frequency、level
            sb.append(mWifiList.get(i).toString()).append("\n");
        }
        return sb;
    }

    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL":mWifiInfo.getMacAddress();
    }
    public String getBSSID() {
        return (mWifiInfo == null)? "NULL":mWifiInfo.getBSSID();
    }

    public int getIpAddress() {
        return (mWifiInfo == null)? 0:mWifiInfo.getIpAddress();
    }

    //得到连接的ID
    public int getNetWorkId() {
        return (mWifiInfo == null)? 0:mWifiInfo.getNetworkId();
    }

    //得到wifiInfo的所有信息
    public String getWifiInfo() {
        return (mWifiInfo == null)? "NULL":mWifiInfo.toString();
    }

    //添加一个网络并连接
    public void addNetWork(WifiConfiguration wifiConfiguration) {
        int wcgId = mWifiManager.addNetwork(wifiConfiguration);
        mWifiManager.enableNetwork(wcgId,true);
    }

    //断开指定ID的网络
    public void disConnectionWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }
}
