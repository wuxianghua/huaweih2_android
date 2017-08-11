package com.palmap.huawei;

import android.util.Log;

/**
 * Created by z00249906 on 2017/6/28.
 */

public class WifiLocationManager{

        String TAG="WifiLocationManager";
        public WifiLocationManager(){
                try{
                        System.loadLibrary("WifiLocationJni");
                        Log.i(TAG,"load WifiLoationMgr success");
                }
                catch(Exception e){
                        Log.i(TAG, "WifiLocationManager: "+e.getMessage());
                }
        }

        public void setWifiData(String wifidata)
        {
                nAddWifiData(wifidata);
        }

        public void setSensorData(String sensordata)
        {
                nAddSensorData(sensordata);
        }

        public String getBuildingName(){
                return nGetBuildingName();
        }

        public int getMoveStatus(){
                return nGetMoveStatus();
        }
        public String getPos(){
                return nGetPos();
        }

        private native void nAddWifiData(String s);
        private native void nAddSensorData(String s);
        private native String nGetBuildingName();
        private native int nGetMoveStatus();
        private native String nGetPos();
}
