package com.palmap.demo.huaweih2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PositionJson;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric3 on 2016/10/23.
 */

public class LocateTimerService extends Service {
  private static double curX;
  private static double curY;
  static MainActivity mContext;
  private boolean pushthread = false;
  private static LocateTimerService instance;
  //地理围栏
  static double[] hall=new double[]{12697136.398,	12697170.670 ,	2588890.742 	,2588913.578
  };//  x左,x右,y上,y下
  static double[] meeting=new double[]{12697124.910 ,	12697133.112, 	2588902.794 ,	2588917.187
  };
  static double[] lab=new double[]{12697093.954 ,	12697102.738 ,	2588902.847 	,2588913.271
  };
  static double[] office=new double[]{12697082.524 ,	12697091.996 ,	2588865.223 ,	2588885.331
  };
  static double[] h2=new double[]{12697080.571 ,	12697195.929 ,	2588843.728 ,	2588958.557
  };

  //6个定位参测试点坐标
  double[] x={12697143.402,
      12697130.823,
      12697100.672,
      12697090.221,
      12697110.501,
      12697227.218
  };
  double[] y={2588906.316,
      2588907.362,
      2588904.129,
      2588878.236,
      2588901.734,
      2588831.403,
  };
  int count = 0;
  public LocateTimerService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    Log.d("TimerService", "onBind");
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    instance = this;
    Log.d("TimerService", "onStartCommand");
    if (intent.getStringExtra("flags").equals("3")) {
      //判断当系统版本大于20，即超过Android5.0时，我们采用线程循环的方式请求。
      //当小于5.0时的系统则采用定时唤醒服务的方式执行循环
      int currentapiVersion = android.os.Build.VERSION.SDK_INT;
      if (currentapiVersion > 20) {
        getPushThread();
      } else {
        getHttp();
      }
    }
    return super.onStartCommand(intent, flags, startId);
  }

  //循环请求的线程
  public void getPushThread() {
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

    DataProviderCenter.getInstance().getPosition("", new HttpDataCallBack() {
      @Override
      public void onError(int errorCode) {
        if (!Constant.openLocateTest) ErrorCode.showError(errorCode);
        curX = x[count];
        curY = y[count];
        mContext.fragmentMap.addLocationMark(curX,curY);
        count = ++count%(x.length);
      }

      @Override
      public void onComplete(Object content) {
        try {
          if (Constant.useTestServer){
            List<PositionJson> list=new ArrayList<PositionJson>(JSONArray.parseArray(content.toString(),PositionJson.class));
            curY = list.get(0).getY();
            curX = list.get(0).getX();
            mContext.fragmentMap.addLocationMark(curX, curY);
          }else {
            JSONObject jo1 = JSON.parseObject(content.toString());
            JSONObject jo2 = jo1.getJSONObject("geometry");
            JSONArray jo3 = jo2.getJSONArray("coordinates");
            curX = jo3.getDoubleValue(0);
            curY = jo3.getDoubleValue(1);

//            Types.Point point = mMapView.converToScreenCoordinate(x,y);
            mContext.fragmentMap.addLocationMark(curX,curY);
          }


        }catch (IndexOutOfBoundsException e){
          DialogUtils.showShortToast(e.getMessage());
        }

      }
    });



//    String url = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id=1";
//    OkHttpUtils.get().url(url).build().execute(new StringCallback() {
//      @Override
//      public void onError(Call call, Exception e, int id) {
//        Log.d("TimerService", "TimerService" + e.toString());
//      }
//      @Override
//      public void onResponse(String response, int id) {
//        Log.d("TimerService", "response==" + response);
//      }
//    });
  }

  @Override
  public void onDestroy() {
    pushthread = false;
    Log.d("TimerService", "onDestroy");
    super.onDestroy();
  }

  //启动服务和定时器
  public static void getLocation(Context mContext) {
    LocateTimerService.mContext = (MainActivity) mContext;
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
      e.printStackTrace();
    }
  }

public static LocateTimerService getInstance(){
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

    if (mContext==null||mContext.fragmentMap==null||mContext.fragmentMap.mMapView==null)
      return "";

    String poiName="";


//    Types.Point point = mContext.fragmentMap.mMapView.converToScreenCoordinate(curX,curY);
//    poiName=mContext.fragmentMap.getPOINameByPoint((float) point.x,(float)point.y);

    if (curX>h2[0]&&curX<h2[1]&&curY>h2[2]&&curY<h2[3])
      poiName = Constant.ICS走廊;
    if (curX>hall[0]&&curX<hall[1]&&curY>hall[2]&&curY<hall[3])
      poiName = Constant.H2大厅;
    if (curX>lab[0]&&curX<lab[1]&&curY>lab[2]&&curY<lab[3])
      poiName = Constant.ICS实验室;
    if (curX>office[0]&&curX<office[1]&&curY>office[2]&&curY<office[3])
      poiName = Constant.ICS办公区;
    if (curX>meeting[0]&&curX<meeting[1]&&curY>meeting[2]&&curY<meeting[3])
      poiName = Constant.会议室;


//    if (H2大厅.equals(poiName)) {
//      poiName
//    }else if (name.contains(ICS实验室)) {
//      showPush(Constant.ICS实验室);
//      showRedPoiMark(Constant.ICS实验室,x,y);
//    } else if (会议室.equals(name)) {
////      showPush(Constant.会议室);
//      showRedPoiMark(Constant.会议室,x,y);
//    } else if (ICS办公区.equals(name)) {
////      showPush(Constant.ICS办公区);
//      showRedPoiMark(Constant.ICS办公区,x,y);
//    }

    return poiName;
  }
}