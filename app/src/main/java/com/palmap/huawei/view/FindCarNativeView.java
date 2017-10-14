package com.palmap.huawei.view;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.geojson.FeatureCollection;

/**
 * Created by Administrator on 2017/10/10/010.
 */

public interface FindCarNativeView {
    //添加有车位图层
    void addParkingCarLayer(FeatureCollection featureCollection);

    //更新车位数量
    void changeCarParkingNum(int carNum,int noCarNum);

    //展示定位图标
    void showLocationIcon(double var1,double var2,double var3,double var4);

    //更新地图状态
    void updateMapCamera(CameraPosition position);

    //清除到航线
    void clearRoute();

    //添加定位点
    void addLocationMark(LatLng location);

    //导航完成，回复UI
    void resetBeforeNavi();
}
