package com.palmap.demo.huaweih2.repo.impl;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.facebook.stetho.common.LogUtil;
import com.palmap.demo.huaweih2.factory.LocationService;
import com.palmap.demo.huaweih2.factory.ServiceFactory;
import com.palmap.demo.huaweih2.model.LocationInfoModel;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.repo.LocationListener;
import com.palmap.demo.huaweih2.repo.LocationRepo;
import com.palmap.demo.huaweih2.util.IpUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wtm on 2017/1/3.
 */

public class LocationRepoImpl implements LocationRepo {

    private ArrayList<LocationListener> listeners;

    private LocationService locationService;

    //api 请求得到的定位model
    private LocationInfoModel locationInfoModel = null;

    private Handler callBackHandler;

    private TimeCallBack timeCallBack;

    private Subscription apiSubscription;

    private String ipAddress = null;

    private Context context;

    public LocationRepoImpl(Context context) {
        listeners = new ArrayList<>();
        this.context = context;
        locationService = ServiceFactory.create(LocationService.class);
        callBackHandler = new Handler(Looper.getMainLooper());
        ipAddress = IpUtils.getIpAddress();
    }

    @Override
    public synchronized void startLocation() {
        if (null == timeCallBack) {
            timeCallBack = new TimeCallBack();
            timeCallBack.start(callBackHandler);
            sendApi();
        }
    }

    private void sendApi() {
        LogUtil.e("ip:" + IpUtils.getIpAddress());
        apiSubscription =
                requestLocation()
                        .observeOn(AndroidSchedulers.mainThread())
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
                        .subscribe(new Action1<LocationInfoModel>() {
                            @Override
                            public void call(LocationInfoModel locationInfoModel) {
                                LocationRepoImpl.this.locationInfoModel = locationInfoModel;
                                callBackHandler.removeCallbacks(timeCallBack);
                                timeCallBack = new TimeCallBack();
                                timeCallBack.start(callBackHandler);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                locationInfoModel = null;
                            }
                        });
    }

    private Observable<LocationInfoModel> requestLocation() {
        return Observable.create(new Observable.OnSubscribe<LocationInfoModel>() {
            @Override
            public void call(final Subscriber<? super LocationInfoModel> subscriber) {
                String ip = IpUtils.getIp3(context);
                //Logger.dumpLog(context,"request SVA:" + ip);
                locationService.requestLocation(Constant.APP_KEY, "ip", ip).subscribe(new Action1<LocationInfoModel>() {
                    @Override
                    public void call(LocationInfoModel locationInfoModel) {
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

    @Override
    public synchronized void stopLocation() {
        locationInfoModel = null;
        if (timeCallBack != null) {
            LogUtil.e("停止stopLocation");
            callBackHandler.removeCallbacks(timeCallBack);
            timeCallBack = null;
        }
        if (null != apiSubscription && !apiSubscription.isUnsubscribed()) {
            apiSubscription.unsubscribe();
            apiSubscription = null;
        }
    }

    @Override
    public synchronized void addListener(LocationListener locationListener) {
        if (listeners != null && !listeners.contains(locationListener)) {
            listeners.add(locationListener);
        }
    }

    @Override
    public synchronized void removeListener(LocationListener locationListener) {
        if (listeners != null && listeners.contains(locationListener)) {
            listeners.remove(locationListener);
        }
        if (listeners == null || listeners.size() == 0) {
            stopLocation();
        }
    }

    private final class TimeCallBack implements Runnable {

        private long timeSpace = 2000L;

        private Handler handler;

        public TimeCallBack() {
        }

        public TimeCallBack(long timeSpace) {
            this.timeSpace = timeSpace;
        }

        private void start(Handler handler) {
            this.handler = handler;
            handler.postDelayed(this, timeSpace);
        }

        @Override
        public void run() {
            if (null == locationInfoModel) {
                for (LocationListener l : listeners) {
                    l.onFailed(new NetworkErrorException(), "request error");
                }
            } else {
                LocationInfoModel model = locationInfoModel.valueClone();
                for (LocationListener l : listeners) {
                    l.onComplete(model, System.currentTimeMillis());
                }
                locationInfoModel = null;
            }
            handler.postDelayed(this, timeSpace);
        }
    }
}
