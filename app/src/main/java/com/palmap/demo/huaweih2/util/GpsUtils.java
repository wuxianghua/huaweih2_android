package com.palmap.demo.huaweih2.util;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.palmap.demo.huaweih2.HuaWeiH2Application;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by eric3 on 2016/12/26.
 * gps定位数据获取
 */

public class GpsUtils {
  private static final int EXP_SECONDS = 7;//curXY有效期，用于避免lampsite在室外定位
  private static double curX = 0;
  private static double curY = 0;
  public static boolean hasExactGpsData = false;//EXP_SECONDS s内是否有精确度<10m的GPS数据
  public static int curAccuracy = 99999;//精度,单位m

  private static Timer mTimer;

//  private boolean isGpsOpen = false;

  public static double getCurX() {
    return curX;
  }

  public static double getCurY() {
    return curY;
  }

  private static LocationListener mLocationListener = new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {
      updateToNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      DialogUtils.showShortToast("onStatusChanged" + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
      DialogUtils.showShortToast("onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
      DialogUtils.showShortToast("onProviderDisabled");
    }
  };

  public static boolean isGPSOpen() {
    LocationManager alm = (LocationManager) HuaWeiH2Application.instance
        .getSystemService(Context.LOCATION_SERVICE);
    if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
      return true;
    else
      return false;
  }

  public static boolean checkGPSOpen(final Context context) {

    if (isGPSOpen()) {
//      DialogUtils.showShortToast("GPS模块正常");
      return true;
    }

    DialogUtils.showDialog(context, "您尚未开启GPS定位，是否前往开启？", new DialogUtils.DialogCallBack() {
      @Override
      public void onComplete() {
        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
      }

      @Override
      public void onCancel() {
        DialogUtils.showShortToast("您未开启GPS定位服务");
      }
    });
    return false;
  }


  public static void startGetLocation(Context context) {
    checkGPSOpen(context);

    initGPS();
  }


  private static void initGPS() {
    // 获取位置管理服务
    LocationManager locationManager;
    String serviceName = Context.LOCATION_SERVICE;
    locationManager = (LocationManager) HuaWeiH2Application.instance.getSystemService(serviceName);
    // 查找到服务信息
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
    criteria.setAltitudeRequired(false);
    criteria.setBearingRequired(false);
    criteria.setCostAllowed(false);
    criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

    String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
    Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
    updateToNewLocation(location);
    // 设置监听器，自动更新的最小时间为间隔N毫秒或最小位移变化超过N米
    locationManager.requestLocationUpdates(provider, 5 * 1000, 5,
        mLocationListener);
  }

  private static void updateToNewLocation(Location location) {
    if (location != null) {
      float latitude = (float) location.getLatitude();
      float longitude = (float) location.getLongitude();
      curAccuracy = (int) location.getAccuracy();
//      double[] wgs84 = DataUtil.lonLat2WebMercator(longitude+0.014300f,latitude-0.01400f);
//      double[] wgs84 = CoordinateUtils.lonLat2Mercator(114.0544826063,22.6463831178);//接待处经纬度
      double[] wgs84 = CoordinateUtils.lonLat2Mercator(longitude, latitude);
      curX = wgs84[0];//深圳h2
      curY = wgs84[1];


      if (curAccuracy < 18 ) {//若gps精度小于18m，有lampsite定位也用gps，防止在室外用lampsite定位不准
        if (mTimer!=null) {
          mTimer.cancel();
        }
        hasExactGpsData = true;
        mTimer = new Timer();
        TimerTask task = new TimerTask() {
          @Override
          public void run() {
            hasExactGpsData = false;
          }
        };
        mTimer.schedule(task,EXP_SECONDS * 1000);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//          @Override
//          public void run() {
//            hasExactGpsData = false;
//          }
//        }, EXP_SECONDS * 1000);
      }


//      curX = wgs84[0]-829428.56d;//修正为深圳h2
//      curY = wgs84[1]-1074547.65d;
//      if (Constant.isDebug)
//        DialogUtils.showShortToast("纬度：" + latitude + " x=" + curX + "\n经度" + longitude + " y=" + curY);
    } else {
//      DialogUtils.showShortToast("无法获取地理信息");
    }

  }
}
