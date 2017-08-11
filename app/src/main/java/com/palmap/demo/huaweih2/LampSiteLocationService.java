package com.palmap.demo.huaweih2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.palmap.demo.huaweih2.factory.LocationService;
import com.palmap.demo.huaweih2.factory.ServiceFactory;
import com.palmap.demo.huaweih2.fragment.FragmentMap;
import com.palmap.demo.huaweih2.model.LocationInfoModel;
import com.palmap.demo.huaweih2.model.SvaLocationRsrpModel;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.repo.LocationListener;
import com.palmap.demo.huaweih2.repo.LocationRepo;
import com.palmap.demo.huaweih2.repo.impl.LocationRepoImpl;
import com.palmap.demo.huaweih2.util.GpsUtils;
import com.palmap.demo.huaweih2.util.IpUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class LampSiteLocationService extends Service implements LocationListener {

    private LocationRepo locationRepo;

    public static final String LOCATION_ACTION = "lampSiteLocationBroadcast";
    public static final String MODEL_LOCATIONINFO = "LocationInfoModel";
    public static final String STATE_TAG = "state_tag";
    public static final String EXCEPTION = "exception";
    public static final String ERROR_MSG = "error_msg";
    public static final String TIMESTAMP = "timeStamp";

    public static final int STATE_COMPLETE = 0;
    public static final int STATE_FAILED = -1;

    private static final String TAG = "LampSiteLocationService";

    private volatile AMapLocation currentGpsLocation = null;

    private LocationInfoModel currentLocation = null;

    private static int GPS_Accuracy = 23;
    private static int SVA_Accuracy = 13;

    private boolean isSVALocation = false;

    private int gpsCount = 0;
    private int svaCout = 0;

    private int errorCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        if (locationRepo == null) {
            locationRepo = new LocationRepoImpl(this);
        }
        GpsUtils.addListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.e(TAG, "onLocationChanged: " + aMapLocation.getAddress());
                currentGpsLocation = aMapLocation;
                //Logger.dumpLog(LampSiteLocationService.this, "GPS Accuracy :" + aMapLocation.getAccuracy());
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationRepo.addListener(this);
        locationRepo.stopLocation();
        locationRepo.startLocation();

        /*requestSignalInfo().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable.delay(2000L, TimeUnit.MILLISECONDS);
                    }
                })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.delay(2000L, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribe(new Action1<SvaLocationRsrpModel>() {
                    @Override
                    public void call(SvaLocationRsrpModel rsrpModel) {
                        if (rsrpModel!=null && rsrpModel.getPrrusignal()!=null && rsrpModel.getPrrusignal().size()>0){
                            double minRsrp = 10000;
                            for (int i = 0; i < rsrpModel.getPrrusignal().size(); i++) {
                                double rsrp = rsrpModel.getPrrusignal().get(i).getRsrp();
                                if(rsrp < minRsrp){
                                    minRsrp = rsrp;
                                }
                            }
                            Logger.dumpLog2(LampSiteLocationService.this,"sva rsrp:" + minRsrp);
                        }else{
                            Logger.dumpLog2(LampSiteLocationService.this,"获取 rsrp失败 null");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        Logger.dumpLog2(LampSiteLocationService.this,"获取 rsrp失败");
                    }
                });*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationRepo.removeListener(this);
        locationRepo.stopLocation();
    }

    public LampSiteLocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onComplete(LocationInfoModel locationInfoModel, long timeStamp) {
        Log.e(TAG, "onComplete: 定位成功");

        LocationInfoModel sendModel = null;
        errorCount = 0;
        if (currentGpsLocation == null) {
            sendModel = locationInfoModel;
            isSVALocation = true;
            gpsCount = 0;
            callBackLocation(sendModel, timeStamp);
            return;
        }
        sendModel = isSVALocation ? locationInfoModel : createWithGPS();
        //GPS和SVA切换判断
        float accuracy = currentGpsLocation.getAccuracy();
        int accuracyOffset;
        if (currentLocation != null && isSVALocation) {//从室内出室外
            accuracyOffset = GPS_Accuracy;
        } else {//从室外进入室内
            accuracyOffset = SVA_Accuracy;
        }
        //GPS信号是否过关
        boolean canUseGPS = accuracy < accuracyOffset;
        if (canUseGPS) {
            gpsCount++;
            svaCout = 0;
            //Logger.dumpLog(this,"GPS ++ " + gpsCount);
        } else { //不通过GPS精度判断
            gpsCount = 0;
            svaCout++;
            //Logger.dumpLog(this,"SVA ++ " + svaCout);
        }
        if (isSVALocation) {
            if(gpsCount > 1){
                //Logger.dumpLog(this,"切换到GPS:");
                sendModel = createWithGPS();
            }
        }else{
            if (svaCout > 1) {
                //Logger.dumpLog(this,"切换到SVA:");
                sendModel = locationInfoModel;
            }
        }
        callBackLocation(sendModel,timeStamp);
    }

    private void callBackLocation(LocationInfoModel sendModel, long timeStamp) {
        if (sendModel == null) {
            return;
        }
        currentLocation = sendModel;
        //Logger.dumpLog(this,"使用" + (currentLocation.isSVA() ? "SVA":"GPS")+ "点位");
        isSVALocation = currentLocation.isSVA();
        Intent intent = new Intent(LOCATION_ACTION);
        intent.putExtra(MODEL_LOCATIONINFO, sendModel);
        intent.putExtra(STATE_TAG, STATE_COMPLETE);
        intent.putExtra(TIMESTAMP, timeStamp);
        sendBroadcast(intent);


        LocateTimerService.curFloorID = (long) sendModel.getZ();
        LocateTimerService.curX = sendModel.getX();
        LocateTimerService.curY = sendModel.getY();
        FragmentMap.hasLocated = true;
    }

    @Override
    public void onFailed(Exception ex, String msg) {
        Log.e(TAG, "onFailed: 定位失败");
        //定位失败使用GPS
        errorCount++;
        //Logger.dumpLog(this,"SVA请求失败~ 次数:" + errorCount);
        if (errorCount < 2){
            return;
        }
        //Logger.dumpLog(this,"SVA请求失败连续2次了!");
        if (currentGpsLocation != null) {
            //Logger.dumpLog(this,"SVA请求失败~ GPS!");
            /*
            Intent intent = new Intent(LOCATION_ACTION);
            intent.putExtra(MODEL_LOCATIONINFO, withGPS);
            intent.putExtra(STATE_TAG, STATE_COMPLETE);
            intent.putExtra(TIMESTAMP, System.currentTimeMillis());
            sendBroadcast(intent);*/
            callBackLocation(createWithGPS(), System.currentTimeMillis());
            gpsCount = 0;
            svaCout = 0;
        } else {
            Intent intent = new Intent(LOCATION_ACTION);
            intent.putExtra(STATE_TAG, STATE_FAILED);
            intent.putExtra(EXCEPTION, ex);
            intent.putExtra(ERROR_MSG, msg);
            sendBroadcast(intent);
            FragmentMap.hasLocated = false;
        }
    }

    private LocationInfoModel createWithGPS() {
        if (currentGpsLocation == null) {
            return null;
        }
        LocationInfoModel l = new LocationInfoModel();
        l.setSVA(false);
        LocationInfoModel.PropertiesBean prePropertiesBean = new LocationInfoModel.PropertiesBean();
        prePropertiesBean.setFloor_id((int) Constant.FLOOR_ID_F1);
        l.setProperties(prePropertiesBean);
        LocationInfoModel.GeometryBean geometryBean = new LocationInfoModel.GeometryBean();
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(GpsUtils.getCurX());
        coordinates.add(GpsUtils.getCurY());
        geometryBean.setCoordinates(coordinates);
        l.setGeometry(geometryBean);
        return l;
    }

    public static void start(Context context) {
        context.startService(new Intent(context, LampSiteLocationService.class));
    }

    public static void stop(Context context) {
        context.stopService(new Intent(context, LampSiteLocationService.class));
    }

    private Observable<SvaLocationRsrpModel> requestSignalInfo() {
        return Observable.create(new Observable.OnSubscribe<SvaLocationRsrpModel>() {
            @Override
            public void call(final Subscriber<? super SvaLocationRsrpModel> subscriber) {
                String ip = IpUtils.getIp3(LampSiteLocationService.this);
//                String ip = "10.32.161.99";
                ServiceFactory.create(LocationService.class)
                        .requestSignalInfo(Constant.APP_KEY,ip)
                        .subscribe(new Action1<SvaLocationRsrpModel>() {
                    @Override
                    public void call(SvaLocationRsrpModel locationInfoModel) {
                        subscriber.onNext(locationInfoModel);
                        subscriber.onCompleted();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        subscriber.onError(throwable);
                    }
                });
            }
        });
    }

}
