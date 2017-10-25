package com.palmap.huawei.presenter;

import android.content.Context;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.palmap.astar.navi.AStarPath;
import com.palmap.huawei.view.FindCarNativeView;
import com.palmaplus.nagrand.geos.Coordinate;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10/010.
 */

public interface FindCarNativePresenter {

    //获取上下文和view对象
    void attachView(Context context, FindCarNativeView findCarNativeView);
    //周期方法
    void onCreate();
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
    void startNavi(FeatureCollection featureCollection, LatLng coordinate);
    void startMovcNavi(FeatureCollection featureCollection, LatLng coordinate);
    void stopNavi();
    void stopMovcNavi();
    void showSearchPark(LatLng latLng);
    void getLocation();
    boolean canParkCar(int poiId);
    void startNaviEngine(double x,double y);
    /*void setRouteLineData(List<AStarPath> routes);
    void setLatLng(LatLng latLng,double x,double y);*/
}
