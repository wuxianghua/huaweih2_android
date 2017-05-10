package com.palmap.demo.huaweih2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PositionJson;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.GpsUtils;
import com.palmap.demo.huaweih2.util.IpUtils;
import com.palmap.demo.huaweih2.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static com.palmap.demo.huaweih2.fragment.FragmentMap.hasLocated;

/**
 * Created by eric3 on 2016/10/23.
 */

public class LocateTimerService extends Service {
    private boolean isReceived;//上次发送定位请求后是否收到返回
    private long timestampRec;//上次收到定位数据时间戳
    public static double curX;//当前定位点坐标，可能是gps和lampsite的结果
    public static double curY;
    public static long curFloorID;//当前定位点floor
    static Context mContext;
    static MainActivity mMainActivity;
    private boolean pushthread = false;
    private static LocateTimerService instance;
    private boolean isUseGpsLocation = true;//是否在室外，使用gps数据
    //地理围栏
    static double[] hall = new double[]{12697136.696, 12697172.696, 2588890.655, 2588917.384
    };//  x小,x大,y小,y大
    static double[] meeting = new double[]{12697125.262, 12697133.136, 2588900.784, 2588917.194
    };
    static double[] lab = new double[]{12697094.209, 12697102.759, 2588900.784, 2588912.914
    };
    static double[] office = new double[]{12697081.767, 12697093.989, 2588868.311, 2588890.293
    };
    static double[] h2 = new double[]{12697080.571, 12697195.929, 2588843.728, 2588958.557
    };
    static double[] h2_out = new double[]{12697149.692, 12697171.838, 2588912.244, 2588957.663
    };

    //6个定位参测试点坐标
    double[] x = {
//      12697159.860  ,
            12697143.402,
            12697142.402,
            12697130.823,
            12697131.823,
            12697100.672,
            12697101.672,
            12697090.221,
            12697091.221,
            12697110.501,
            12697111.501,
            12697227.218
    };
    double[] y = {
//      2588920.829,
            2588906.316,
            2588906.316,
            2588907.362,
            2588907.362,
            2588904.129,
            2588904.129,
            2588878.236,
            2588878.236,
            2588901.734,
            2588901.734,
            2588831.403,
    };
    int count = 0;

    private Handler locationHandler;
    private byte locationModel = 0x00;
    private final byte DATAHUB = 0x01;
    private final byte GPS = DATAHUB << 1;
    private final byte MULTI = DATAHUB << 2;

    public LocateTimerService() {
    }

    public static void setmMainActivity(MainActivity mMainActivity) {
        LocateTimerService.mMainActivity = mMainActivity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TimerService", "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        GpsUtils.addListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                curX = GpsUtils.getCurX();
                curY = GpsUtils.getCurY();
                Message message = locationHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putParcelable("obj", aMapLocation);
                bundle.putDouble("x", curX);
                bundle.putDouble("y", curY);
                message.what = GPS;
                message.setData(bundle);
                message.sendToTarget();
//        hasLocated = false;
            }
        });
        locationHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                int what = message.what;
                Bundle data = message.getData();
                double x = data.getDouble("x");
                double y = data.getDouble("y");
                switch (what) {
                    case 1:
                        processDataHub(x, y);
                        break;
                    case 2:
                        AMapLocation aMapLocation = data.<AMapLocation>getParcelable("obj");
                        processGPS(aMapLocation);
                        break;
                }
                if (locationModel == what) {
                    addLocationMark(x, y);
                }

//        LogToFile.w("=========\nCurrent: ", locationModel == GPS ? "GPS" : "DataHub" + " \n=========\n");
//        if (locationModel == DATAHUB) {
//          addLocationMark(x, y);
//        } else {
//          addLocationMark2(x, y);
//        }

                return false;
            }
        });

//    GpsUtils.addListener(new LocationListener() {
//      @Override
//      public void onLocationChanged(Location location) {
//        curX = GpsUtils.getCurX();
//        curY = GpsUtils.getCurY();
//        Message message = locationHandler.obtainMessage();
//        Bundle bundle = new Bundle();
//        bundle.putDouble("x", curX);
//        bundle.putDouble("y", curY);
//        message.what = GPS;
//        message.setData(bundle);
//        message.sendToTarget();
//        hasLocated = false;
//      }
//
//      @Override
//      public void onStatusChanged(String s, int i, Bundle bundle) {
//      }
//
//      @Override
//      public void onProviderEnabled(String s) {
//      }
//
//      @Override
//      public void onProviderDisabled(String s) {
//      }
//    });
//    locationHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
//      @Override
//      public boolean handleMessage(Message message) {
//        int what = message.what;
//        Bundle data = message.getData();
//        double x = data.getDouble("x");
//        double y = data.getDouble("y");
//        switch (what) {
//          case 1:
//            processDataHub(x, y);
//            break;
//          case 2:
//            processGPS(x, y);
//            break;
//        }
//
//        if (locationModel == what) {
//          addLocationMark(x, y);
//        }
//
//        return false;
//      }
//    });

        Log.d("TimerService", "onStartCommand");
        String ext = intent.getStringExtra("flags");
        if (ext == null) {
            DialogUtils.showLongToast("请重启定位服务");
        }
        if ("3".equals(ext)) {
            //判断当系统版本大于20，即超过Android5.0时，我们采用线程循环的方式请求。
            //当小于5.0时的系统则采用定时唤醒服务的方式执行循环
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion > 20) {
                getPushThread();
            } else {
                getHttp();
            }
        }
        return START_NOT_STICKY;
    }

    //循环请求的线程
    private void getPushThread() {
        pushthread = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pushthread) {
                    try {
                        Thread.sleep(Constant.LOCATE_FRESH_TIME);
                        getHttp();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //请求网络获取数据
    private void getHttp() {

        LogUtils.i("" + (System.currentTimeMillis() - timestampRec));
        if (!isReceived && System.currentTimeMillis() - timestampRec < 10000)
            return;

        isReceived = false;
        HuaWeiH2Application.userIp = IpUtils.getIpAddress();

//    curX = 12697148.213;
//    curY = 2588906.09;
//    hasLocated = true;
//    Message message = locationHandler.obtainMessage();
//    Bundle bundle = new Bundle();
//    bundle.putDouble("x", curX);
//    bundle.putDouble("y", curY);
//    message.what = DATAHUB;
//    message.setData(bundle);
//    message.sendToTarget();

        DataProviderCenter.getInstance().getPosition(new HttpDataCallBack() {
            @Override
            public void onError(int errorCode) {
                isReceived = true;
                timestampRec = System.currentTimeMillis();
                isUseGpsLocation = true;//没有lampsite数据，只能使用gps

                if (Constant.openLocateTest) {//测试代码，虚拟定位点
                    curX = x[count];
                    curY = y[count];
                    curFloorID = Constant.FLOOR_ID_F1;
                    hasLocated = true;
                    if (mMainActivity != null && mMainActivity.fragmentMap != null) {
                        mMainActivity.fragmentMap.addLocationMark(curX, curY);

                    }
                    count = ++count % (x.length);
                } else {

                    if (mMainActivity == null || mMainActivity.fragmentMap == null)
                        return;
                    //没有定位数据，查看是否有gps

                    curFloorID = Constant.FLOOR_ID_F1;
                    curX = GpsUtils.getCurX();
                    curY = GpsUtils.getCurY();

                    // TODO: 2016/12/29 室外hasLocated=true怎么办
                    hasLocated = false;

                    addLocationMark();
//                    if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1) {
//                        mMainActivity.fragmentMap.addLocationMark(curX, curY);
//                        if (Constant.isDebug)
//                            ErrorCode.showError(ErrorCode.CODE_GPS);
//                    }
//                    ErrorCode.showError(CODE_NO_LOCATE_DATA);
                }
            }

            @Override
            public void onComplete(Object content) {
                isReceived = true;
                timestampRec = System.currentTimeMillis();
                if (mMainActivity == null || mMainActivity.fragmentMap == null)
                    return;
                try {
                    if (Constant.useTestServer) {
                        List<PositionJson> list = new ArrayList<PositionJson>(JSONArray.parseArray(content.toString(), PositionJson.class));
                        curY = list.get(0).getY();
                        curX = list.get(0).getX();
                        if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1)
                            mMainActivity.fragmentMap.addLocationMark(curX, curY);
                    } else {
                        JSONObject jo1 = JSON.parseObject(content.toString());
                        JSONObject jo2 = jo1.getJSONObject("geometry");
                        JSONObject jo4 = jo1.getJSONObject("properties");
                        curFloorID = jo4.getLongValue("floor_id");
                        JSONArray jo3 = jo2.getJSONArray("coordinates");
                        curX = jo3.getDoubleValue(0);
                        curY = jo3.getDoubleValue(1);
                        hasLocated = true;
                        Message message = locationHandler.obtainMessage();
                        isUseGpsLocation = false;//没有lampsite数据，只能使用gps
                        Bundle bundle = new Bundle();
                        bundle.putDouble("x", curX);
                        bundle.putDouble("y", curY);
                        message.what = DATAHUB;
                        message.setData(bundle);
                        message.sendToTarget();

//            if (GpsUtils.hasExactGpsData || (curX > h2_out[0] && curX < h2_out[1] && curY > h2_out[2] && curY < h2_out[3])) {
//                            /*如果最近 EXP_SECONDS s内有精度小于18m的gps数据，就强制用gps
//                               避免lampsite在室外也能获取不准确的定位点*/
//              isUseGpsLocation = true;
//              curX = GpsUtils.getCurX();
//              curY = GpsUtils.getCurY();
//              hasLocated = false;
//            } else {
//              isUseGpsLocation = false;//使用lampsite数据
//            }


//            addLocationMark();

//                        if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1) {
//                            mMainActivity.fragmentMap.addLocationMark(curX, curY);
//                            if (Constant.isDebug)
//                                ErrorCode.showError(ErrorCode.CODE_HUAWEI);
//                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        pushthread = false;
        Log.d("TimerService", "onDestroy");
        super.onDestroy();
    }

    //启动服务和定时器
    public static void start(Context mContext) {
        LocateTimerService.mContext = mContext;
        try {
            Intent intent = new Intent(mContext, LocateTimerService.class);
            intent.putExtra("flags", "3");
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion > 20) {
                //一般的启动服务的方式
                mContext.startService(intent);
            } else {
                //定时唤醒服务的启动方式
                PendingIntent pIntent = PendingIntent.getService(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) mContext
                        .getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(), Constant.LOCATE_FRESH_TIME, pIntent);
            }
        } catch (Exception e) {
            DialogUtils.showShortToast(e.getMessage());
        }
    }

    public static LocateTimerService getInstance() {
        return instance;
    }

    //停止由AlarmManager启动的循环
    public static void stop(Context mContext) {
        instance = null;
        Intent intent = new Intent(mContext, LocateTimerService.class);
        PendingIntent pIntent = PendingIntent.getService(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    public static String getCurrentLocationArea() {

        if (mMainActivity == null || mMainActivity.fragmentMap == null || mMainActivity.fragmentMap.mMapView == null)
            return Constant.其它;

        String poiName = Constant.其它;


//    Types.Point point = mContext.fragmentMap.mMapView.converToScreenCoordinate(curX,curY);
//    poiName=mContext.fragmentMap.getPOINameByPoint((float) point.x,(float)point.y);

        if (curX > h2[0] && curX < h2[1] && curY > h2[2] && curY < h2[3])
            poiName = Constant.H2大楼;
        if (curX > hall[0] && curX < hall[1] && curY > hall[2] && curY < hall[3])
            poiName = Constant.H2大厅;
        if (curX > lab[0] && curX < lab[1] && curY > lab[2] && curY < lab[3])
            poiName = Constant.ICS实验室;
        if (curX > office[0] && curX < office[1] && curY > office[2] && curY < office[3])
            poiName = Constant.ICS办公区;
        if (curX > meeting[0] && curX < meeting[1] && curY > meeting[2] && curY < meeting[3])
            poiName = Constant.会议室;


        return poiName;
    }

    private void processDataHub(double x, double y) {
        if (!isUseGpsLocation && accuracyCount != 1) {
            locationModel = DATAHUB;
            accuracyCount = 0;
        }
//    isUseGpsLocation = false;
//    if (!GpsUtils.hasExactGpsData) {
//      locationModel = DATAHUB;
//      isUseGpsLocation = false;
//    }
    }

    private int accuracyCount = 0;
    private float accuracy = 0;


    private void processGPS(AMapLocation aMapLocation) {
        accuracy = aMapLocation.getAccuracy();
        if (isUseGpsLocation || accuracy < 10) {
            accuracyCount = 1;
            locationModel = GPS;
        } else {
            accuracyCount = 0;
        }
//    if (isUseGpsLocation) {
//      locationModel = GPS;
//    }
//    if (GpsUtils.hasExactGpsData) {
//      locationModel = GPS;
//      isUseGpsLocation = true;
//    }
    }

    private void addLocationMark(double x, double y) {
        if (null != mMainActivity && null != mMainActivity.fragmentMap && mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1)
            mMainActivity.fragmentMap.addLocationMark(x, y);

        if (Constant.isDebug) {
            if (isUseGpsLocation) {
                ErrorCode.showError(ErrorCode.CODE_GPS);
            } else {
                ErrorCode.showError(ErrorCode.CODE_HUAWEI);
            }
        }
    }

    /**
     * @Author: eric3
     * @Description: 根据gps和lampsite状态选择有效的定位点，添加到地图
     * @Time 2016/12/29 12:14
     */
    private void addLocationMark() {
        if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1)
            mMainActivity.fragmentMap.addLocationMark(curX, curY);

        if (Constant.isDebug) {
            if (isUseGpsLocation) {
                ErrorCode.showError(ErrorCode.CODE_GPS);
            } else {
                ErrorCode.showError(ErrorCode.CODE_HUAWEI);
            }
        }
    }
}