package com.palmap.demo.huaweih2.util;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmaplus.nagrand.data.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by eric3 on 2016/12/26.
 * gps定位数据获取
 */

public class GpsUtils {
  private static final int EXP_SECONDS = 4; //curXY有效期，用于避免lampsite在室外定位
  private static double curX = 0;
  private static double curY = 0;
  public static boolean hasExactGpsData = false; //EXP_SECONDS s内是否有精确度<10m的GPS数据
  public static int curAccuracy = 99999;//精度,单位m

  private static Timer mTimer;

  private static final String TAG = "GpsUtils";

  //  private static List<LocationListener> listeners = new ArrayList<>();
  private static List<AMapLocationListener> listeners = new ArrayList<>();


//  private boolean isGpsOpen = false;

  public static double getCurX() {
    return curX;
  }

  public static double getCurY() {
    return curY;
  }

//  private static LocationListener mLocationListener = new LocationListener() {
//    @Override
//    public void onLocationChanged(Location location) {
//      updateToNewLocation(location);
//      for (LocationListener listener : listeners) {
//        listener.onLocationChanged(location);
//      }
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//      DialogUtils.showShortToast("onStatusChanged" + provider);
//      for (LocationListener listener : listeners) {
//        listener.onStatusChanged(provider, status, extras);
//      }
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//      DialogUtils.showShortToast("onProviderEnabled");
//      for (LocationListener listener : listeners) {
//        listener.onProviderEnabled(provider);
//      }
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//      DialogUtils.showShortToast("onProviderDisabled");
//      for (LocationListener listener : listeners) {
//        listener.onProviderDisabled(provider);
//      }
//    }
//  };

  //声明定位回调监听器
  private static AMapLocationListener mAmapLocationListener = new AMapLocationListener() {
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
      updateToNewLocationByAmap(amapLocation);
      for (AMapLocationListener listener : listeners) {
        listener.onLocationChanged(amapLocation);
      }
    }
  };

//  public static void addListener(LocationListener listener) {
//    listeners.add(listener);
//  }

  public static void addListener(AMapLocationListener listener) {
    listeners.add(listener);
  }

  public static boolean isGPSOpen() {
    LocationManager alm = (LocationManager) HuaWeiH2Application.instance
            .getSystemService(Context.LOCATION_SERVICE);
    if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
      return true;
    } else {
      return false;
    }
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
    // gaode
    // 声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    // 初始化定位
    mLocationClient = new AMapLocationClient(HuaWeiH2Application.instance);
    // 设置定位回调监听
    mLocationClient.setLocationListener(mAmapLocationListener);

    AMapLocationClientOption mLocationOption = null;
    mLocationOption = new AMapLocationClientOption();
    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
    mLocationOption.setInterval(2000);
    mLocationOption.setWifiScan(false);
//    mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
    mLocationClient.setLocationOption(mLocationOption);
    mLocationClient.startLocation();

//    // 获取位置管理服务
//    LocationManager locationManager;
//    String serviceName = Context.LOCATION_SERVICE;
//    locationManager = (LocationManager) HuaWeiH2Application.instance.getSystemService(serviceName);
//    // 查找到服务信息
//    Criteria criteria = new Criteria();
//    criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
//    criteria.setAltitudeRequired(false);
//    criteria.setBearingRequired(false);
//    criteria.setCostAllowed(false);
//    criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
//
//    String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
//    Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
//    updateToNewLocation(location);
//    // 设置监听器，自动更新的最小时间为间隔N毫秒或最小位移变化超过N米
//    locationManager.requestLocationUpdates(provider, 5 * 1000, 5,
//        mLocationListener);
  }

  private static void updateToNewLocationByAmap(AMapLocation amapLocation) {
    if (amapLocation != null) {
      if (amapLocation.getErrorCode() == 0) {
        //定位成功回调信息，设置相关消息
        float latitude;//纬度
        float longitude;//经度

        latitude = (float) amapLocation.getLatitude();
        longitude = (float) amapLocation.getLongitude();
        curAccuracy = (int) amapLocation.getAccuracy();

//        double marLatLon[] = EvilTransform.transform(latitude, longitude);

//        double[] wgs84 = CoordinateUtils.lonLat2Mercator(marLatLon[1], marLatLon[0]);
        double[] wgs84 = DataUtil.lonLat2WebMercator(longitude, latitude);
        curX = wgs84[0];//深圳h2
        curY = wgs84[1];


//                if (curAccuracy < 18) { //若gps精度小于18m，有lampsite定位也用gps，防止在室外用lampsite定位不准
//                    if (mTimer != null) {
//                        mTimer.cancel();
//                    }
//                    hasExactGpsData = true;
//                    mTimer = new Timer();
//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            hasExactGpsData = false;
//                        }
//                    };
//                    mTimer.schedule(task, EXP_SECONDS * 1000);
//
//                }

//        DialogUtils.showShortToast("纬度：" + latitude + " x=" + curX + "\n经度" + longitude + " y=" + curY + "\ntype:" + amapLocation.getLocationType());
      } else {
        Log.e(TAG, "updateToNewLocationByAmap: " + "location Error, ErrCode:"
                + amapLocation.getErrorCode() + ", errInfo:"
                + amapLocation.getErrorInfo());
       /* DialogUtils.showShortToast("location Error, ErrCode:"
                + amapLocation.getErrorCode() + ", errInfo:"
                + amapLocation.getErrorInfo());*/
      }
    }
  }

  private static void updateToNewLocation(Location location) {


    if (location != null) {
      float latitude;//纬度
      float longitude;//经度

      latitude = (float) location.getLatitude();
      longitude = (float) location.getLongitude();
      curAccuracy = (int) location.getAccuracy();


//      double[] wgs84 = DataUtil.lonLat2WebMercator(longitude+0.014300f,latitude-0.01400f);
//      double[] wgs84 = CoordinateUtils.lonLat2Mercator(114.0544826063,22.6463831178);//接待处经纬度


//      longitude = 114.0545046063f;//测试
//      latitude = 22.6463851178f;


      double marLatLon[] = EvilTransform.transform(latitude, longitude);

      double[] wgs84 = CoordinateUtils.lonLat2Mercator(marLatLon[1], marLatLon[0]);
//      double[] wgs84 = DataUtil.lonLat2WebMercator(longitude, latitude);
//      double[] wgs84 = CoordinateUtils.lonLat2Mercator(latitude, longitude);

      curX = wgs84[0];//深圳h2
      curY = wgs84[1];


//            if (curAccuracy < 18) { //若gps精度小于18m，有lampsite定位也用gps，防止在室外用lampsite定位不准
//                if (mTimer != null) {
//                    mTimer.cancel();
//                }
//                hasExactGpsData = true;
//                mTimer = new Timer();
//                TimerTask task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        hasExactGpsData = false;
//                    }
//                };
//                mTimer.schedule(task, EXP_SECONDS * 1000);
//
//            }


//      curX = wgs84[0]-829428.56d;//修正为深圳h2
//      curY = wgs84[1]-1074547.65d;
//      if (Constant.isDebug)
//        DialogUtils.showShortToast("纬度：" + latitude + " x=" + curX + "\n经度" + longitude + " y=" + curY);
    } else {
//      DialogUtils.showShortToast("无法获取地理信息");
    }

  }


  private static class EvilTransform {
    private static final double pi = 3.14159265358979324;

    //
    // Krasovsky 1940
    //
    // a = 6378245.0, 1/f = 298.3
    // b = a * (1 - f)
    // ee = (a^2 - b^2) / a^2;
    private final static double a = 6378245.0;
    private final static double ee = 0.00669342162296594323;

    //
    // World Geodetic System ==> Mars Geodetic System
    public static double[] transform(double wgLat, double wgLon) {
      double mgLat;
      double mgLon;
      if (outOfChina(wgLat, wgLon)) {
        mgLat = wgLat;
        mgLon = wgLon;
        return new double[] {mgLat, mgLon};
      }
      double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
      double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
      double radLat = wgLat / 180.0 * pi;
      double magic = Math.sin(radLat);
      magic = 1 - ee * magic * magic;
      double sqrtMagic = Math.sqrt(magic);
      dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
      dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
      mgLat = wgLat + dLat;
      mgLon = wgLon + dLon;

      return new double[] {mgLat, mgLon};
    }

    private static boolean outOfChina(double lat, double lon) {
      if (lon < 72.004 || lon > 137.8347) {
        return true;
      }
      if (lat < 0.8293 || lat > 55.8271) {
        return true;
      }
      return false;
    }

    private static double transformLat(double x, double y) {
      double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
      ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
      ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
      ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
      return ret;
    }

    private static double transformLon(double x, double y) {
      double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
      ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
      ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
      ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
      return ret;
    }
  }
}
