package com.palmap.huawei.presenter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.api.utils.turf.TurfMeasurement;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.mapbox.services.commons.geojson.LineString;
import com.mapbox.services.commons.models.Position;
import com.orhanobut.logger.Logger;
import com.palmap.astar.navi.AStarPath;
import com.palmap.astar.navi.AStarVertex;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.util.CoordinateUtils;

import com.palmap.huawei.http.CarServiceFactory;
import com.palmap.huawei.http.GetCarParkingInfoService;
import com.palmap.huawei.mode.CarParkingInfo;
import com.palmap.huawei.rx.RxCarParkStatus;
import com.palmap.huawei.view.FindCarNativeView;
import com.palmap.huawei.widget.LatLngEvaluator;
import com.palmap.nagrand.support.util.DataConvertUtils;
import com.palmaplus.nagrand.geos.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.operation.distance.DistanceOp;

import org.intellij.lang.annotations.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.bupt.sse309.locsdk.DefaultLocClient;
import cn.bupt.sse309.locsdk.LocClient;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/10/10/010.
 */

public class FindCarNativePresenterImpl implements FindCarNativePresenter{

    private static final String TAG = FindCarNativePresenterImpl.class.getSimpleName();
    private Context mContext;
    private Handler handler;
    private ValueAnimator markerAnimator;
    private LatLngEvaluator evaluator = new LatLngEvaluator();
    //定位点
    private LatLng locationLatLng = null;
    //导航点的个数
    private int count;
    //导航是否完成
    private boolean routeFinished = false;
    private FindCarNativeView mFindCarNativeView;
    //到航线上坐标点的集合
    private List<LatLng> routePoints = new ArrayList<>();
    //获取停车位数据
    private GetCarParkingInfoService mGetCarParkingInfoService;
    //是否开始导航
    private boolean isStartNavi;
    //终点
    private LatLng endPoint;
    //距离终点的距离
    private double destationDistance;
    //所有停车的车位
    private List<Integer> parkingCars;
    //没有停车的车位
    private List<Integer> noParkingCars;
    //有停车位的FeatureCollection
    private FeatureCollection carFeatureCollection;
    //没有停车位的FeatureCollection
    private FeatureCollection noCarFeatureCollection;
    //有停车位的Features
    private List<Feature> carFeature;
    //没有停车位的Features
    private List<Feature> noCarFeatures;
    //路线数据
    List<AStarPath> routes;
    //定位经纬度
    private LatLng latLng;
    //导航吸附距离
    private final int nearLength = 10;
    //构造方法
    public FindCarNativePresenterImpl() {

    }

    @Override
    public void attachView(Context context, FindCarNativeView findCarNativeView) {
        mContext = context;
        mFindCarNativeView = findCarNativeView;
    }

    @Override
    public void onCreate() {
        initData();
        if (mGetCarParkingInfoService == null) {
            mGetCarParkingInfoService = CarServiceFactory.getInstance().createService(GetCarParkingInfoService.class);
        }
        Observable.interval(2000,60000,TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                RxCarParkStatus.requestCarParkStatus(mGetCarParkingInfoService).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                        .subscribe(new Consumer<List<CarParkingInfo>>() {
                            @Override
                            public void accept(List<CarParkingInfo> carParkingInfos) throws Exception {
                                resolutionParkingData(carParkingInfos);
                            }
                        });
            }
        });
        getLocation();
    }
    //初始化数据
    private void initData() {
        parkingCars = new ArrayList<>();
        noParkingCars = new ArrayList<>();
        carFeature = new ArrayList<>();
        noCarFeatures = new ArrayList<>();
        latLng = new LatLng();
    }

    //解析停车位数据,放在子线程进行处理
    public void resolutionParkingData(List<CarParkingInfo> carParkingInfos) {
        for (CarParkingInfo carParkingInfo : carParkingInfos) {
            if (carParkingInfo.occupied == 1) {
                parkingCars.add(carParkingInfo.poiId);
            }else {
                noParkingCars.add(carParkingInfo.poiId);
            }
        }
        if (HuaWeiH2Application.parkData == null || HuaWeiH2Application.parkData.getDataMap() == null ||HuaWeiH2Application.parkData.getDataMap().get("Area")==null) return;
        for (Feature feature : HuaWeiH2Application.parkData.getDataMap().get("Area").getFeatures()) {
            int featureId = Integer.parseInt(feature.getProperty("id").toString());
            if (featureId == 1284122||featureId ==1261981||featureId ==2037840||featureId ==2037841||featureId ==2037842) continue;
            if (parkingCars.contains(featureId)) {
                carFeature.add(feature);
            }else {
                noCarFeatures.add(feature);
            }
        }
        carFeatureCollection = FeatureCollection.fromFeatures(carFeature);

        noCarFeatureCollection = FeatureCollection.fromFeatures(noCarFeatures);
        mFindCarNativeView.addParkingCarLayer(carFeatureCollection);
        mFindCarNativeView.changeCarParkingNum(parkingCars.size(),noParkingCars.size());
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void startMovcNavi(FeatureCollection featureCollection, LatLng coordinate) {
        animLocationMark(coordinate);
        routePoints.clear();
        List<Feature> features = featureCollection.getFeatures();
        for (int i = 0; i < features.size(); i++) {
            LineString lineString = (LineString) features.get(i).getGeometry();
            for (Position position : lineString.getCoordinates()) {
                LatLng l = new LatLng(
                        position.getLatitude(),
                        position.getLongitude());
                if (!routePoints.contains(l)) {
                    routePoints.add(l);
                }
            }
        }
        startMovcCarNavi();
    }
    //结束导航
    @Override
    public void stopNavi() {
        isStartNavi = false;
        CameraPosition position = new CameraPosition.Builder()
                .target(newLatLng)
                .zoom(18)
                .bearing(0) // Rotate the camera
                .tilt(45)
                .build();
        mFindCarNativeView.updateMapCamera(position);
    }

    //结束模拟导航
    @Override
    public void stopMovcNavi() {
        handler.removeCallbacksAndMessages(null);
        markerAnimator.cancel();
        CameraPosition position = new CameraPosition.Builder()
                .target(newLatLng)
                .zoom(18)
                .bearing(0) // Rotate the camera
                .tilt(45)
                .build();
        mFindCarNativeView.updateMapCamera(position);
    }

    @Override
    public void showSearchPark(LatLng latLng) {
        CameraPosition position = new CameraPosition.Builder()
                .target(latLng)
                .zoom(18)
                .bearing(0) // Rotate the camera
                .tilt(45)
                .build();
        mFindCarNativeView.updateMapCamera(position);
    }

    @Override
    public void startNavi(FeatureCollection featureCollection, LatLng coordinate) {
        isStartNavi = true;
        routeFinished = false;
        routePoints.clear();
        oldLatLng = coordinate;
        List<Feature> features = featureCollection.getFeatures();
        for (int i = 0; i < features.size(); i++) {
            LineString lineString = (LineString) features.get(i).getGeometry();
            for (Position position : lineString.getCoordinates()) {
                LatLng l = new LatLng(
                        position.getLatitude(),
                        position.getLongitude());
                if (!routePoints.contains(l)) {
                    routePoints.add(l);
                }
            }
        }
        endPoint = routePoints.get(routePoints.size()-1);
    }

    private void startMovcCarNavi() {
        if (handler == null) {
            handler = new Handler();
        } else {
            handler.removeCallbacksAndMessages(null);
            count = 0;
            routeFinished = false;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Runnable that = this;
                if (!routeFinished && (routePoints.size() - 1) >= count) {
                    final LatLng endLatLng = routePoints.get(count);
                    double angle = computeHeading(locationLatLng, endLatLng);
                    CameraPosition position = new CameraPosition.Builder()
                            .target(routePoints.get(count))
                            .zoom(20)
                            .bearing(angle) // Rotate the camera
                            .tilt(45)
                            .build();
                    mFindCarNativeView.updateMapCamera(position);
                    animLocationMark(endLatLng);
                    count++;
                    handler.postDelayed(that, 500);
                } else {
                    routeFinished = true;
                    Toast.makeText(mContext, "导航完成 !", Toast.LENGTH_SHORT).show();
                    mFindCarNativeView.clearRoute();
                    mFindCarNativeView.resetBeforeNavi();
                }
            }
        };
        handler.post(runnable);
    }

    private void animLocationMark(final LatLng location) {

        if (locationLatLng == null) {
            locationLatLng = location;
            mFindCarNativeView.addLocationMark(location);
            return;
        }

        if (locationLatLng.equals(location)) {
            return;
        }

        if (markerAnimator != null) {
            markerAnimator.cancel();
            markerAnimator = null;
        }
        final LatLng oldLatLng = locationLatLng;
        markerAnimator = ValueAnimator.ofFloat(0, 1);
        markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LatLng p = evaluator.evaluate(animation.getAnimatedFraction(), oldLatLng, location);
                mFindCarNativeView.addLocationMark(p);
            }
        });
        markerAnimator.setInterpolator(new LinearInterpolator());
        markerAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                locationLatLng = location;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                locationLatLng = location;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        markerAnimator.setDuration(500);
        markerAnimator.start();
    }

    public static double computeHeading(LatLng from, LatLng to) {
        return TurfMeasurement.bearing(
                Position.fromCoordinates(from.getLongitude(), from.getLatitude()),
                Position.fromCoordinates(to.getLongitude(), to.getLatitude())
        );
    }

    //获取定位的位置
    @Override
    public void getLocation() {
        LocClient client = new DefaultLocClient(mContext, "YjU5NjFkMjItNDhlZC00OGNjLTk2N2UtMmNlZmE5YTUyMWU2", "QwZzlf4eXvuhNTAUvi5BDaD9E73aAMZE0z8uFMUrhvU");
        client.setOnLocResultReceivedListener(new LocClient.OnLocResultReceivedListener() {
            @Override
            public void OnSuccess(int type, Map<String, String> result) {
                //楼宇ID
                String buildingId = result.get("building_id");
                //楼层
                int floor = Integer.parseInt(result.get("floor"));
                //发送报文时的时间戳
                long timestamp = Long.parseLong(result.get("timestamp"));
                switch (type) {
                    case LocClient.TYPE_FLOOR:
                        //楼层切换时的结果
                        break;
                    case LocClient.TYPE_LOC:
                        //定位成功返回的结果
                        double x = Float.parseFloat(result.get("x"));
                        double y = Float.parseFloat(result.get("y"));
                        Logger.d("positionData"+"x"+x+""+"y"+y);
                        x =  (12697074.245 + (x/7.850));
                        y =  (2588966.542 - (y/7.850));
                        double[] doubles = CoordinateUtils.mercator2Lonlat(x, y);
                        latLng.setLatitude(doubles[0]);
                        latLng.setLongitude(doubles[1]);
                        double distance = testCalcDistance(latLng);
                        if (distance < nearLength) {
                            latLng = getMinLatLng(latLng);
                        } else {
                            Toast.makeText(mContext,"距离导航线最近距离为:" + distance + "米",Toast.LENGTH_SHORT).show();
                        }
                        Log.e(TAG,"正在定位double[0]"+doubles[0]+"double[1]"+doubles[1]);
                        mFindCarNativeView.showLocationIcon(latLng.getLongitude(),latLng.getLatitude(),x,y);
                        if (isStartNavi == true) {
                            startNaviEngine(latLng.getLongitude(),latLng.getLatitude());
                        }
                        break;
                }
            }

            @Override
            public void OnFailed(String code, String message) {
                double x = 783.267333984375;
                double y = 282.28619384765625;
                Logger.d("positionData"+"x"+x+""+"y"+y);
                x =  (12697084.245 + (x/7.850));
                y =  (2588956.542 - (y/7.850));
                double[] doubles = CoordinateUtils.mercator2Lonlat(x, y);
                Log.e(TAG,"正在定位double[0]"+doubles[0]+"double[1]"+doubles[1]);
                mFindCarNativeView.showLocationIcon(doubles[1],doubles[0],x,y);
                if (isStartNavi) {
                    startNaviEngine(doubles[1],doubles[0]);
                }
            }
        });
        client.start();
    }

    private double testCalcDistance(LatLng latLng) {
        if (routes == null || routes.size() == 0 || null == latLng) {
            //toast("testCalcDistance错误 ！ ！ ！");
            return 0;
        }
        double[] ps = DataConvertUtils.INSTANCE.latlng2WebMercator(latLng.getLatitude(), latLng.getLongitude());

        double minDistance = Double.MAX_VALUE;

        GeometryFactory factory = new GeometryFactory();

        Point point = factory.createPoint(new com.vividsolutions.jts.geom.Coordinate(ps[0], ps[1]));

        //com.vividsolutions.jts.geom.LineString lineString = null;
        for (AStarPath aStarPath : routes) {
            AStarVertex from = aStarPath.getFrom();
            AStarVertex to = aStarPath.getTo();
            com.vividsolutions.jts.geom.LineString line =
                    factory.createLineString(new com.vividsolutions.jts.geom.Coordinate[]{
                            new com.vividsolutions.jts.geom.Coordinate(((Point) from.getVertex().getShape()).getX(), ((Point) from.getVertex().getShape()).getY()),
                            new com.vividsolutions.jts.geom.Coordinate(((Point) to.getVertex().getShape()).getX(), ((Point) to.getVertex().getShape()).getY())
                    });

            double d = line.distance(point);
            Log.e(TAG, "计算出: " + d);
            if (d < minDistance) {
                minDistance = d;
                //lineString = line;
            }
        }
        return minDistance;
    }

    private LatLng getMinLatLng(LatLng latLng) {
        if (routes == null || routes.size() == 0 || null == latLng) {
            return latLng;
        }
        double[] ps = DataConvertUtils.INSTANCE.latlng2WebMercator(latLng.getLatitude(), latLng.getLongitude());

        double minDistance = Double.MAX_VALUE;

        GeometryFactory factory = new GeometryFactory();

        Point point = factory.createPoint(new com.vividsolutions.jts.geom.Coordinate(ps[0], ps[1]));
        com.vividsolutions.jts.geom.LineString lineString = null;

        for (AStarPath aStarPath : routes) {
            AStarVertex from = aStarPath.getFrom();
            AStarVertex to = aStarPath.getTo();
            com.vividsolutions.jts.geom.LineString line =
                    factory.createLineString(new com.vividsolutions.jts.geom.Coordinate[]{
                            new com.vividsolutions.jts.geom.Coordinate(((Point) from.getVertex().getShape()).getX(), ((Point) from.getVertex().getShape()).getY()),
                            new com.vividsolutions.jts.geom.Coordinate(((Point) to.getVertex().getShape()).getX(), ((Point) to.getVertex().getShape()).getY())
                    });

            double d = line.distance(point);
            Log.e(TAG, "计算出: " + d);
            if (d < minDistance) {
                minDistance = d;
                lineString = line;
            }
        }
        DistanceOp d = new DistanceOp(point, lineString);
        double[] result = null;
        com.vividsolutions.jts.geom.Coordinate[] nearestPoints = d.nearestPoints();
        if (nearestPoints != null && nearestPoints.length > 1) {
            com.vividsolutions.jts.geom.Coordinate p = nearestPoints[1];
            result = DataConvertUtils.INSTANCE.webMercator2LatLng(p.x, p.y);
        }
        return new LatLng(result[0],result[1]);
    }

    @Override
    public boolean canParkCar(int poiId) {
        return noParkingCars.contains(poiId);
    }

    @Override
    public void setRouteLineData(List<AStarPath> routes) {
        this.routes = routes;
    }

    private LatLng oldLatLng;
    private LatLng newLatLng;
    private void startNaviEngine(double x,double y) {
        destationDistance = distHaversineRAD(x,y,endPoint.getLatitude(),endPoint.getLongitude());
        newLatLng = new LatLng(x,y);
        Log.e(TAG,"destationDistance"+destationDistance);
        if (!routeFinished||destationDistance>500) {
            double angle = computeHeading(oldLatLng, newLatLng);
            CameraPosition position = new CameraPosition.Builder()
                    .target(newLatLng)
                    .zoom(20)
                    .bearing(angle) // Rotate the camera
                    .tilt(45)
                    .build();
            mFindCarNativeView.updateMapCamera(position);
            oldLatLng = newLatLng;
        }else {
            routeFinished = true;
            Toast.makeText(mContext, "导航完成 !", Toast.LENGTH_SHORT).show();
            mFindCarNativeView.clearRoute();
            mFindCarNativeView.resetBeforeNavi();
        }
    }

    public static double distHaversineRAD(double lat1, double lon1, double lat2, double lon2) {
        double hsinX = Math.sin((lon1 - lon2) * 0.5);
        double hsinY = Math.sin((lat1 - lat2) * 0.5);
        double h = hsinY * hsinY +
                (Math.cos(lat1) * Math.cos(lat2) * hsinX * hsinX);
        return 2 * Math.atan2(Math.sqrt(h), Math.sqrt(1 - h)) * 6367000;
    }
}
