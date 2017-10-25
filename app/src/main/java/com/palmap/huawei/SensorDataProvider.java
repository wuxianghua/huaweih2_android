package com.palmap.huawei;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.palmap.huawei.utils.ThreadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by l00361076 on 2017/9/21.
 */

public class SensorDataProvider extends Handler implements SensorEventListener {
    private HandlerThread m_handler_thread = null;
    private SensorManager m_sensor_manager;
    Sensor m_sensor_acc = null;
    Sensor m_sensor_gyro = null;
    Sensor m_sensor_magn = null;
    private OrientationChangeListener mOrientationChangeListener;

    private static final int MSG_TYPE_SENSOR = 1;
    private static final int SENSOR_SAMPLING_PERIOD = 10;
    public static SensorDataProvider m_self = null;

    private final Object m_lock = new Object();

    private SensorDataProvider(Context ctx, HandlerThread handler_thread) {
        super(handler_thread.getLooper());
        init(ctx, handler_thread);
    }

    public void setOrientationChangeListener(OrientationChangeListener orientationChangeListener) {
        mOrientationChangeListener = orientationChangeListener;
    }

    public static SensorDataProvider getIntance(Context ctx) {
        if (null == m_self) {
            HandlerThread handler_thread = new HandlerThread("SensorDataProviderThread", Process.THREAD_PRIORITY_BACKGROUND);
            handler_thread.start();
            m_self = new SensorDataProvider(ctx, handler_thread);
        }
        return m_self;
    }

    private void init(Context ctx, HandlerThread handler_thread) {
        m_handler_thread = handler_thread;
        m_sensor_manager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);

        m_sensor_acc = m_sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        m_sensor_gyro = m_sensor_manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        m_sensor_magn = m_sensor_manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public boolean start() {
        if (!m_sensor_manager.registerListener(this, m_sensor_acc, 0, this)) {
            return false;
        }

        if (!m_sensor_manager.registerListener(this, m_sensor_gyro, 0, this)) {
            m_sensor_manager.unregisterListener(this);
            return false;
        }

        if (!m_sensor_manager.registerListener(this, m_sensor_magn, 0, this)) {
            m_sensor_manager.unregisterListener(this);
            return false;
        }

        sendEmptyMessageDelayed(MSG_TYPE_SENSOR, SENSOR_SAMPLING_PERIOD);

        return true;
    }

    public void stop() {
        m_sensor_manager.unregisterListener(this);
        removeMessages(MSG_TYPE_SENSOR);
    }

    public void clear() {
        stop();
        m_sensor_manager = null;
        m_handler_thread.quitSafely();
        m_handler_thread = null;
    }

    float[] m_arr_acc = new float[3];
    float[] m_arr_gyro = new float[3];
    float[] m_arr_magn = new float[3];
    public void onSensorChanged(SensorEvent event) {
        synchronized (m_lock) {
            switch (event.sensor.getType()){
                case Sensor.TYPE_ACCELEROMETER:
                    m_arr_acc = event.values.clone();
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    m_arr_gyro = event.values.clone();
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    m_arr_magn = event.values.clone();
                    break;
                default:
                    break;
            }
            ThreadManager.getDownloadPool().execute(new Runnable() {
                @Override
                public void run() {
                    calculateOrientation();
                }
            });
        }
    }

    float degree;
    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, m_arr_acc,
                m_arr_magn);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[0] = values[0] < 0 ? 360+values[0]:values[0];
        degree = values[0];
        if (mOrientationChangeListener != null) {
            mOrientationChangeListener.orientationChanger(degree);
        }
    }

    interface OrientationChangeListener {
        void orientationChanger(float degree);
    }

    Buffer m_this_buf = new Buffer();
    Buffer m_that_buf = new Buffer();
    Buffer m_curr_buf = m_this_buf;

    public JSONObject getSensorData() {
        Buffer curr_buf = m_curr_buf;

        synchronized (m_lock) {
            if (m_curr_buf == m_this_buf) {
                m_curr_buf = m_that_buf;
            }
            else{
                m_curr_buf = m_this_buf;
            }
        }

        return curr_buf.sweepData();
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_TYPE_SENSOR:
            {
                Log.i("liguoliang", "haha");
                long time = System.currentTimeMillis();
                synchronized (m_lock) {
                    m_curr_buf.addData(Sensor.TYPE_ACCELEROMETER, m_arr_acc, time);
                    m_curr_buf.addData(Sensor.TYPE_MAGNETIC_FIELD, m_arr_magn, time);
                    m_curr_buf.addData(Sensor.TYPE_GYROSCOPE, m_arr_gyro, time);
                }

                sendEmptyMessageDelayed(MSG_TYPE_SENSOR, SENSOR_SAMPLING_PERIOD);
            }
                break;
            default:
                break;
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class Buffer {
        HashMap<String, JSONArray> m_map_json_arr = new HashMap<>();

        private String getDataTye(int event_type) {
            String data_type = "";
            switch (event_type) {
                case Sensor.TYPE_ACCELEROMETER:
                    data_type = "accelerometer";
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    data_type = "magnetometer";
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    data_type = "gyro";
                    break;
                default:
                    break;
            }

            return data_type;
        }

        private void addData(int event_type, float[] data, long time) {
            if (null == data || data.length < 3) {
                return;
            }

            String data_type = getDataTye(event_type);
            if (TextUtils.isEmpty(data_type)) {
                return;
            }

            JSONArray json_arr = m_map_json_arr.get(data_type);
            if (null == json_arr) {
                json_arr = new JSONArray();
                m_map_json_arr.put(data_type, json_arr);
            }

            JSONObject json_obj = new JSONObject();
            try {
                json_obj.put( "x", data[0]);
                json_obj.put( "y", data[1]);
                json_obj.put( "z", data[2]);
                json_obj.put("time", time);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            json_arr.put(json_obj);
        }

        private JSONObject sweepData() {
            JSONObject json_obj = new JSONObject();
            try {
                json_obj.put("accelerometer", m_map_json_arr.get("accelerometer"));
                json_obj.put("magnetometer", m_map_json_arr.get("magnetometer"));
                json_obj.put("gyro", m_map_json_arr.get("gyro"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            m_map_json_arr.clear();

            return json_obj;
        }
    }
}
