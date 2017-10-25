package com.palmap.huawei;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.tencent.smtt.sdk.WebView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by l00361076 on 2017/9/22.
 */

public class CollectProvider extends Handler {
    private Context m_ctx;
    private SensorDataCollectionListener mSensorDataCollectionListener;
    private HandlerThread m_collect_thread = null;
    private final Object m_lock = new Object();
    private static CollectProvider m_self = null;
    private final int MSG_TYPE_SWEEP_DATA = 1001;
    private final  int SENSOR_DATA_SWEEP_DELAY = 100;
    private static OrientationChangeListener mOrientationChangeListener;
    private CollectProvider(Context ctx, HandlerThread handler_thread) {
        super(handler_thread.getLooper());
        m_ctx = ctx;
        m_collect_thread = handler_thread;
    }

    public interface OrientationChangeListener {
        void orientationChanger(float degree);
    }

    public void setOrientationChangeListener(OrientationChangeListener orientationChangeListener) {
        mOrientationChangeListener = orientationChangeListener;
    }

    public static CollectProvider getInstance(Context ctx) {
        if (null == m_self) {
            HandlerThread collect_thread = new HandlerThread("CollectThread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
            collect_thread.start();
            m_self = new CollectProvider(ctx, collect_thread);
        }
        return m_self;
    }

    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case MSG_TYPE_SWEEP_DATA:
                sweepSensorData();
                Log.i("liguoliangx1", "time: " + System.currentTimeMillis());
                break;
            default:
                break;
        }
    }

    public void setSensorDataCollectionListener(SensorDataCollectionListener sensorDataCollectionListener) {
        mSensorDataCollectionListener = sensorDataCollectionListener;
    }

    public interface SensorDataCollectionListener{
        void collectionSensorData(String data);
    }

    public void stop() {
        if (null != m_collect_thread) {
            m_collect_thread.quitSafely();
            m_collect_thread = null;
        }
        if (m_self != null) {
            m_self = null;
        }

       if(null != SensorDataProvider.getIntance(m_ctx)) {
           SensorDataProvider.getIntance(m_ctx).stop();
            removeMessages(MSG_TYPE_SWEEP_DATA);
        }

    }

    public boolean start() {
        if(SensorDataProvider.getIntance(m_ctx) != null){
            SensorDataProvider.getIntance(m_ctx).setOrientationChangeListener(new SensorDataProvider.OrientationChangeListener() {
                @Override
                public void orientationChanger(float degree) {
                    if (mOrientationChangeListener != null) {
                        mOrientationChangeListener.orientationChanger(degree);
                    }
                }
            });
            SensorDataProvider.getIntance(m_ctx).start();
            sendEmptyMessageDelayed(MSG_TYPE_SWEEP_DATA, SENSOR_DATA_SWEEP_DELAY);
        }
        return true;
    }

    private void sweepSensorData() {
        //       Log.i(TAG,"sweep sensor data");
        if(null != SensorDataProvider.getIntance(m_ctx)) {
            JSONObject sensor_data = SensorDataProvider.getIntance(m_ctx).getSensorData();
            try{
                sensor_data.put("time", System.currentTimeMillis());
                mSensorDataCollectionListener.collectionSensorData(sensor_data.toString());
                Log.i("liguoliangx2", sensor_data.toString());
            }catch(JSONException e){
                e.printStackTrace();
            }

            sendEmptyMessageDelayed(MSG_TYPE_SWEEP_DATA, SENSOR_DATA_SWEEP_DELAY);
        }

    }
}
