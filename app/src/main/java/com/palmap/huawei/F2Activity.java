package com.palmap.huawei;

import android.app.Activity;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.palmap.astar.navi.AStarPath;
import com.palmap.core.data.PlanarGraph;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.R;
import com.palmap.indoor.IMapViewController;
import com.palmap.indoor.MapViewControllerFactory;
import com.palmap.indoor.Utils;
import com.palmap.indoor.impl.MapBoxMapViewController;
import com.palmap.indoor.navigate.INavigateManager;
import com.palmap.indoor.navigate.impl.MapBoxNavigateManager;
import com.vividsolutions.jts.geom.Coordinate;

import java.util.List;

/**
 * Created by wtm on 2017/9/30.
 */
public class F2Activity extends Activity {

    IMapViewController iMapViewController;

    private static final String TAG = "F2Activity";

    private Coordinate start, end;

    private MapBoxNavigateManager navigateManager;

    private LatLng locationLatlng = null;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f2);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.layout_mapView);
        iMapViewController = MapViewControllerFactory.createByMapBox(this);
        iMapViewController.attachMap(viewGroup, savedInstanceState, new IMapViewController.Action() {
            @Override
            public void onAction() {
                iMapViewController.drawPlanarGraph(HuaWeiH2Application.parkData);

                iMapViewController.setLocationMarkIcon(R.drawable.bianlidian711, 30, 30);
                iMapViewController.setOnSingTapListener(new IMapViewController.onSingTapListener() {
                    @Override
                    public void onAction(final double x, final double y) {
                        /*iMapViewController.addOverLayer(new OverLayer() {
                            @Override
                            public Coordinate getCoordinate() {
                                return new Coordinate(x,y);
                            }

                            @Override
                            public long getFloorId() {
                                return iMapViewController.getFloorId();
                            }

                            @Override
                            public int getResource() {
                                return R.drawable.bianlidian711;
                            }
                        });*/

                        //iMapViewController.addLocationMark(x, y);

                       /* openLocation();

                        locationLatlng = new LatLng(x,y);

                        if (start == null) {
                            double xy[] = DataConvertUtils.INSTANCE.latlng2WebMercator(x, y);
                            start = new Coordinate(xy[0], xy[1]);
                        } else {
                            double xy2[] = DataConvertUtils.INSTANCE.latlng2WebMercator(x, y);
                            end = new Coordinate(xy2[0], xy2[1]);
                            navigateManager.navigation(
                                    start.x,
                                    start.y,
                                    iMapViewController.getFloorId(),
                                    end.x,
                                    end.y,
                                    iMapViewController.getFloorId()
                            );
                        }*/
                        MapboxMap mapboxMap = ((MapBoxMapViewController) iMapViewController).getMapBox();
                        PointF pointF = mapboxMap.getProjection().toScreenLocation(new LatLng(x,y));
                        List<Feature> features  = mapboxMap.queryRenderedFeatures(pointF, "Area");
                        if (features.size() > 0){
                            try {
                                LatLng latLng = Utils.getFeatureCentroid(features.get(0));
                                iMapViewController.addLocationMark(latLng.getLatitude(),latLng.getLongitude());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        navigateManager = new MapBoxNavigateManager(this, "roadNet.json");
        navigateManager.setNavigateListener(new INavigateManager.Listener<FeatureCollection>() {
            @Override
            public void OnNavigateComplete(INavigateManager.NavigateState state, List<AStarPath> routes, FeatureCollection route) {
                Log.e(TAG, "OnNavigateComplete: " + state);
                if (state == INavigateManager.NavigateState.OK) {
                    showRoute(route);
                }
            }
        });
    }

    private boolean isOpenLocation = false;

    private void openLocation() {

        if (isOpenLocation) return;

        isOpenLocation = true;

        ((MapBoxMapViewController) iMapViewController).setMapBoxLocationMark(R.drawable.bianlidian711);
        ((MapBoxMapViewController) iMapViewController).openMapBoxLocation(new LocationEngine() {
            private void testLocation() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (locationLatlng == null) {
                            testLocation();
                            return;
                        }
                        for (LocationEngineListener l : locationListeners) {
                            l.onLocationChanged(getLastLocation());
                        }
                        testLocation();
                    }
                }, 1000);
            }

            @Override
            public void activate() {
                testLocation();
            }

            @Override
            public void deactivate() {

            }

            @Override
            public boolean isConnected() {
                return true;
            }

            @Override
            public Location getLastLocation() {
                if (locationLatlng == null) {
                    return null;
                }
                Location location = new Location("");
                location.reset();
                location.setLatitude(locationLatlng.getLatitude());
                location.setLongitude(locationLatlng.getLongitude());
                return location;
            }

            @Override
            public void requestLocationUpdates() {

            }

            @Override
            public void removeLocationUpdates() {

            }
            //是否使用跟随模式
        }, false);
    }

    private void showRoute(FeatureCollection route) {
        ((MapBoxMapViewController) iMapViewController).showRoute(route);
    }

    private boolean isSB = false;

    public void showAreaHover(View v) {
        if (isSB) {
            //((MapBoxMapViewController) iMapViewController).showCarHover(null);
            isSB = false;
            return;
        }
        PlanarGraph planarGraph = HuaWeiH2Application.parkData;
        if (planarGraph == null) {
            return;
        }
        FeatureCollection featureCollection = planarGraph.getDataMap().get("Area");

        if (featureCollection == null) {
            return;
        }
        FeatureCollection testFeatureCollection = FeatureCollection.fromFeatures(featureCollection.getFeatures().subList(10, 600));
        //((MapBoxMapViewController) iMapViewController).showCarHover(testFeatureCollection);
        isSB = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (iMapViewController != null) {
            iMapViewController.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (iMapViewController != null) {
            iMapViewController.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (iMapViewController != null) {
            iMapViewController.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (iMapViewController != null) {
            iMapViewController.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (iMapViewController != null) {
            iMapViewController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (iMapViewController != null) {
            iMapViewController.onDestroy();
        }
        if (navigateManager != null) {
            navigateManager.destructor();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (iMapViewController != null) {
            iMapViewController.onLowMemory();
        }
    }

}
