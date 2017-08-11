package com.palmap.demo.huaweih2.model;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9/009.
 */

public class SensorModel {
    public String building_name;
    public long time;
    public List<AccelerometerModel> accelerometer;
    public List<GyroModel> gyro;
    public List<MagnetometerModel> magnetometer;
}
