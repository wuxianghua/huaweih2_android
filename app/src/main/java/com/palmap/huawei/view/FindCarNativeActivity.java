package com.palmap.huawei.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerView;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.palmap.astar.navi.AStarPath;
import com.palmap.core.overLayer.PulseMarkerViewOptions;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.R;
import com.palmap.huawei.presenter.FindCarNativePresenter;
import com.palmap.huawei.presenter.FindCarNativePresenterImpl;
import com.palmap.indoor.IMapViewController;
import com.palmap.indoor.MapViewControllerFactory;
import com.palmap.indoor.Utils;
import com.palmap.indoor.impl.MapBoxMapViewController;
import com.palmap.indoor.navigate.INavigateManager;
import com.palmap.indoor.navigate.impl.MapBoxNavigateManager;
import com.palmap.nagrand.support.util.DataConvertUtils;
import com.palmaplus.nagrand.geos.Coordinate;
import com.vividsolutions.jts.io.ParseException;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10/010.
 */

public class FindCarNativeActivity extends Activity implements FindCarNativeView{
    String TAG = FindCarNativeActivity.class.getSimpleName();
    //地图容器
    private LinearLayout mapViewLayout;
    //显示占用车位
    private TextView mTvCarNum;
    //显示空余车位
    private TextView mTvNoCarNum;
    //导航起点和终点
    private Coordinate start, end;
    //定位点
    private Coordinate mLocation;
    private LatLng mLocationLatLng;
    //路线规划
    private MapBoxNavigateManager navigateManager;
    //presenter
    private FindCarNativePresenter mFindCarNativePresenter;
    private IMapViewController iMapViewController;
    //导航线数据
    private FeatureCollection routeFeatureCollection;
    //是否可以显示定位点
    private boolean isCanShowLocationIcon = false;
    //模拟导航
    private Button startMovcNavi;
    //实际导航
    private Button startNavi;
    //停这里
    private Button findCarEnsure;
    //返回
    private Button findCarBack;
    //停车
    private LinearLayout findCar;
    //找车
    private LinearLayout stopCar;
    //展示车位信息
    private LinearLayout showCarInfo;
    //车位名称
    private TextView showCarName;
    //停车返回
    private Button parkCarBack;
    //是否设置完终点
    private boolean isHaveSetEnd;
    //选择起点弹框
    private RelativeLayout showSelectStart;
    //设为起点
    private Button setStartNormal,setStartSelect;
    //展示导航信息
    private LinearLayout mShowNaviInfo;
    //展示终点信息
    private TextView mShowDestination;
    //终点车位名称
    private String carName;
    //路径规划信息
    private LinearLayout routePlanInfo;
    //终点信息
    private TextView mDestationInfo;
    //是否是实际导航
    private boolean isMovcNavi;
    //起点和终点Mark
    private MarkerView startMark;
    private MarkerView endMark;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findcar_native);
        initView();
        initMapView(savedInstanceState);
        mFindCarNativePresenter.attachView(this,this);
        mFindCarNativePresenter.onCreate();
    }

    //初始化地图界面
    private void initMapView(@Nullable Bundle savedInstanceState) {
        iMapViewController = MapViewControllerFactory.createByMapBox(this);
        iMapViewController.attachMap(mapViewLayout, savedInstanceState, new IMapViewController.Action() {
            @Override
            public void onAction() {
                iMapViewController.drawPlanarGraph(HuaWeiH2Application.parkData);
                iMapViewController.setLocationMarkIcon(R.mipmap.ico_map_location,60,60);
                isCanShowLocationIcon = true;
                touchMapView();

            }
        });
        navigateManager = new MapBoxNavigateManager(this, "path.json");
        navigateManager.setNavigateListener(new INavigateManager.Listener<FeatureCollection>() {
            @Override
            public void OnNavigateComplete(INavigateManager.NavigateState state, List<AStarPath> routes, FeatureCollection route) {
                if (state == INavigateManager.NavigateState.OK) {
                    mFindCarNativePresenter.setRouteLineData(routes);
                    showRoute(route);
                }
            }
        });
    }
    //开始模拟导航
    public void startMovcNavi(View view) {
        isHaveSetEnd = false;
        isMovcNavi = true;
        startMovcNavi.setVisibility(View.GONE);
        startNavi.setVisibility(View.GONE);
        routePlanInfo.setVisibility(View.GONE);
        parkCarBack.setVisibility(View.GONE);
        showCarInfo.setVisibility(View.GONE);
        findCarEnsure.setVisibility(View.GONE);
        findCarBack.setVisibility(View.GONE);
        mShowNaviInfo.setVisibility(View.VISIBLE);
        mShowDestination.setText("终点"+carName);
        if (start == null || routeFeatureCollection == null) {
            Toast.makeText(FindCarNativeActivity.this,"先添加导航线",Toast.LENGTH_SHORT).show();
        }
        mFindCarNativePresenter.startMovcNavi(routeFeatureCollection,mLocationLatLng);
    }

    //开始导航
    public void startNavi(View view) {
        if (mLocationLatLng.getLongitude() == 0||mLocationLatLng.getLatitude() == 0) {
            Toast.makeText(FindCarNativeActivity.this,"没有定位，无法实际导航",Toast.LENGTH_SHORT).show();
            return;
        }
        if (start == null || routeFeatureCollection == null) {
            Toast.makeText(FindCarNativeActivity.this,"先添加导航线",Toast.LENGTH_SHORT).show();
            return;
        }
        isMovcNavi = false;
        isHaveSetEnd = false;
        startMovcNavi.setVisibility(View.GONE);
        startNavi.setVisibility(View.GONE);
        routePlanInfo.setVisibility(View.GONE);
        parkCarBack.setVisibility(View.GONE);
        showCarInfo.setVisibility(View.GONE);
        findCarEnsure.setVisibility(View.GONE);
        findCarBack.setVisibility(View.GONE);
        mShowNaviInfo.setVisibility(View.VISIBLE);
        mShowDestination.setText("终点"+carName);
        mFindCarNativePresenter.startNavi(routeFeatureCollection,mLocationLatLng);
    }

    //找车
    public void findCar(View view) {
        Intent intent = new Intent(this,SearchResultActivity.class);
        startActivityForResult(intent,1);
    }

    //停车返回
    public void parkCarBack(View view) {
        parkCarBack.setVisibility(View.GONE);
        startMovcNavi.setVisibility(View.GONE);
        startNavi.setVisibility(View.GONE);
        routePlanInfo.setVisibility(View.GONE);
        isHaveSetEnd = false;
        if (routeFeatureCollection != null) {
            ((MapBoxMapViewController) iMapViewController).showRoute(null);
        }
        findCar.setVisibility(View.VISIBLE);
        stopCar.setVisibility(View.VISIBLE);
    }

    //停车
    public void stopCar(View view) {
        parkCarBack.setVisibility(View.VISIBLE);
        stopCar.setVisibility(View.GONE);
        findCar.setVisibility(View.GONE);
        Toast.makeText(FindCarNativeActivity.this,"请在图上选择绿色车位",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            String carNum = data.getStringExtra("carNum");
            carName = carNum;
            feature = iMapViewController.selectFeature(carNum);
            if (feature == null) {
                Toast.makeText(FindCarNativeActivity.this,"你搜索的车位不存在",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                if (endMark != null) {
                    ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(endMark);
                }
                LatLng featureCentroid = Utils.getFeatureCentroid(feature);
                double[] doubles = DataConvertUtils.INSTANCE.latlng2WebMercator(featureCentroid.getLatitude(), featureCentroid.getLongitude());
                end = new Coordinate(doubles[0], doubles[1]);
                findCarBack.setVisibility(View.VISIBLE);
                findCarEnsure.setVisibility(View.VISIBLE);
                findCar.setVisibility(View.GONE);
                stopCar.setVisibility(View.GONE);
                showCarInfo.setVisibility(View.VISIBLE);
                showCarName.setText(carNum);
                mFindCarNativePresenter.showSearchPark(featureCentroid);
                endMark = ((MapBoxMapViewController) iMapViewController).getMapBox().addMarker(new PulseMarkerViewOptions()
                        .position(featureCentroid)
                        .icon(IconFactory.getInstance(FindCarNativeActivity.this).fromResource(R.mipmap.ico_marker_end))
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void showRoute(FeatureCollection route) {
        routeFeatureCollection = route;
        startMovcNavi.setVisibility(View.VISIBLE);
        startNavi.setVisibility(View.VISIBLE);
        routePlanInfo.setVisibility(View.VISIBLE);
        mDestationInfo.setText(carName);
        parkCarBack.setVisibility(View.VISIBLE);
        showCarInfo.setVisibility(View.GONE);
        ((MapBoxMapViewController) iMapViewController).showRoute(route);
    }
    //点击地图
    Feature feature; //点击处的feature
    int category; //点击处的feature的category
    int poiId;   //点击处的feature的poiId
    private void touchMapView() {
        iMapViewController.setOnSingTapListener(new IMapViewController.onSingTapListener() {
            @Override
            public void onAction(final double x, final double y) {
            if (isHaveSetEnd) {
                if (startMark != null) {
                    ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(startMark);
                }
                setStartNormal.setVisibility(View.GONE);
                setStartSelect.setVisibility(View.VISIBLE);
                double xy2[] = DataConvertUtils.INSTANCE.latlng2WebMercator(x, y);
                start = new Coordinate(xy2[0], xy2[1]);
                startMark = ((MapBoxMapViewController) iMapViewController).getMapBox().addMarker(new PulseMarkerViewOptions()
                        .position(new LatLng(x, y))
                        .icon(IconFactory.getInstance(FindCarNativeActivity.this).fromResource(R.mipmap.ico_marker_start))
                );
                return;
            }else {
                feature = iMapViewController.selectFeature(x, y);
                Log.e(TAG,"X"+x+"Y"+y);
                if (feature == null) return;
                category = feature.getNumberProperty("category").intValue();
                poiId = feature.getNumberProperty("id").intValue();
                if (!mFindCarNativePresenter.canParkCar(poiId)) {
                    Toast.makeText(FindCarNativeActivity.this,"请点击绿色车位",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(category == 22001000||category == 22002000||category == 22003000||category ==22004000) {
                    if (endMark != null) {
                        ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(endMark);
                    }
                    parkCarBack.setVisibility(View.GONE);
                    findCar.setVisibility(View.GONE);
                    stopCar.setVisibility(View.GONE);
                    showCarInfo.setVisibility(View.VISIBLE);
                    carName = feature.getStringProperty("name");
                    showCarName.setText(carName);
                    findCarBack.setVisibility(View.VISIBLE);
                    findCarEnsure.setVisibility(View.VISIBLE);
                    double xy2[] = DataConvertUtils.INSTANCE.latlng2WebMercator(x, y);
                    start = mLocation;
                    end = new Coordinate(xy2[0], xy2[1]);
                    endMark = ((MapBoxMapViewController) iMapViewController).getMapBox().addMarker(new PulseMarkerViewOptions()
                            .position(new LatLng(x, y))
                            .icon(IconFactory.getInstance(FindCarNativeActivity.this).fromResource(R.mipmap.ico_marker_end))
                    );
                }
            }
            }
        });
    }

    //初始化view
    private void initView() {
        mapViewLayout = (LinearLayout) findViewById(R.id.layout_mapView);
        mFindCarNativePresenter = new FindCarNativePresenterImpl();
        mTvNoCarNum = (TextView) findViewById(R.id.tv_nocar_num);
        mTvCarNum = (TextView) findViewById(R.id.tv_car_num);
        startNavi = (Button) findViewById(R.id.start_navi);
        startMovcNavi = (Button) findViewById(R.id.start_movc_navi);
        findCarEnsure = (Button) findViewById(R.id.find_car_ensure);
        findCarBack = (Button) findViewById(R.id.find_car_back);
        findCar = (LinearLayout) findViewById(R.id.find_car);
        stopCar = (LinearLayout) findViewById(R.id.stop_car);
        showCarInfo = (LinearLayout) findViewById(R.id.show_car_info);
        showCarName = (TextView) findViewById(R.id.show_car_name);
        parkCarBack = (Button) findViewById(R.id.park_car_back);
        showSelectStart = (RelativeLayout) findViewById(R.id.show_select_start);
        setStartNormal = (Button) findViewById(R.id.set_start_point_normal);
        setStartSelect = (Button) findViewById(R.id.set_start_point_select);
        mShowDestination = (TextView) findViewById(R.id.show_destination_info);
        mShowNaviInfo = (LinearLayout) findViewById(R.id.show_navi_info);
        routePlanInfo = (LinearLayout) findViewById(R.id.plan_route_info);
        mDestationInfo = (TextView) findViewById(R.id.destation_name);
        mLocation = new Coordinate();
        mLocationLatLng = new LatLng();
    }

    //停这里
    public void findCarEnsure(View view) {
        if (mLocation.getX() == 0||mLocation.getY() == 0) {
            showSelectStart.setVisibility(View.VISIBLE);
            return;
        }
        findCarBack.setVisibility(View.GONE);
        findCarEnsure.setVisibility(View.GONE);
        start = mLocation;
        navigateManager.navigation(
                start.x,
                start.y,
                iMapViewController.getFloorId(),
                end.x,
                end.y,
                iMapViewController.getFloorId()
        );
    }

    @Override
    public void addParkingCarLayer(FeatureCollection featureCollection) {
        ((MapBoxMapViewController) iMapViewController).showCarHover(featureCollection);
    }

    @Override
    public void changeCarParkingNum(final int carNum, final int noCarNum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvCarNum.setText(carNum+"");
                mTvNoCarNum.setText(noCarNum+"");
            }
        });
    }

    @Override
    public void showLocationIcon(double var1, double var2,double var3,double var4) {
        if (isCanShowLocationIcon) {
            mLocation.setX(var3);
            mLocation.setY(var4);
            mLocationLatLng.setLatitude(var1);
            mLocationLatLng.setLongitude(var2);
            iMapViewController.addLocationMark(var1,var2);
        }
    }

    @Override
    public void updateMapCamera(CameraPosition position) {
        ((MapBoxMapViewController) iMapViewController).getMapBox().animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 500);
    }

    @Override
    public void clearRoute() {
        ((MapBoxMapViewController) iMapViewController).showRoute(null);
    }

    @Override
    public void addLocationMark(LatLng location) {
        iMapViewController.addLocationMark(location.getLatitude(),location.getLongitude());
    }

    @Override
    public void resetBeforeNavi() {
        findCar.setVisibility(View.VISIBLE);
        stopCar.setVisibility(View.VISIBLE);
        mShowNaviInfo.setVisibility(View.GONE);
        iMapViewController.addLocationMark(0,0);
        if (endMark != null) {
            ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(endMark);
        }
        if (startMark != null) {
            ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(startMark);
        }
    }
    //选择起点
    public void selectStart(View view) {
        findCarEnsure.setVisibility(View.GONE);
        setStartNormal.setVisibility(View.VISIBLE);
        showSelectStart.setVisibility(View.GONE);
        isHaveSetEnd = true;
    }
    //取消
    public void cancleSelect(View view) {
        showSelectStart.setVisibility(View.GONE);
    }
    //设为起点
    public void setStartPoint(View view) {
        setStartSelect.setVisibility(View.GONE);
        findCarBack.setVisibility(View.GONE);
        navigateManager.navigation(
                start.x,
                start.y,
                iMapViewController.getFloorId(),
                end.x,
                end.y,
                iMapViewController.getFloorId()
        );
    }

    public void findCarBack(View view) {
        showCarInfo.setVisibility(View.GONE);
        findCarBack.setVisibility(View.GONE);
        findCarEnsure.setVisibility(View.GONE);
        findCar.setVisibility(View.VISIBLE);
        stopCar.setVisibility(View.VISIBLE);
        setStartNormal.setVisibility(View.GONE);
        setStartSelect.setVisibility(View.GONE);
        isHaveSetEnd = false;
        if (endMark != null) {
            ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(endMark);
        }
        if (startMark != null) {
            ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(startMark);
        }
    }
    //结束导航
    public void endNavi(View view) {
        if (isMovcNavi) {
            mFindCarNativePresenter.stopMovcNavi();
            iMapViewController.addLocationMark(0,0);
        }else {
            mFindCarNativePresenter.stopNavi();
        }
        if (endMark != null) {
            ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(endMark);
        }
        if (startMark != null) {
            ((MapBoxMapViewController) iMapViewController).getMapBox().removeMarker(startMark);
        }
        mShowNaviInfo.setVisibility(View.GONE);
        ((MapBoxMapViewController) iMapViewController).showRoute(null);
        findCar.setVisibility(View.VISIBLE);
        stopCar.setVisibility(View.VISIBLE);
    }
    //定位
    public void location(View view) {
        mFindCarNativePresenter.getLocation();
    }
}
