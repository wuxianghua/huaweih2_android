package com.palmap.huawei;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.mapbox.services.commons.geojson.FeatureCollection;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.R;
import com.palmap.indoor.IMapViewController;
import com.palmap.indoor.MapViewControllerFactory;
import com.palmap.indoor.impl.MapBoxMapViewController;
import com.palmap.indoor.navigate.INavigateManager;
import com.palmap.indoor.navigate.impl.MapBoxNavigateManager;
import com.palmap.nagrand.support.util.DataConvertUtils;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * Created by wtm on 2017/9/30.
 */
public class F2Activity extends Activity {

    IMapViewController iMapViewController;

    private static final String TAG = "F2Activity";

    private Coordinate start, end;

    private MapBoxNavigateManager navigateManager;

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
                //iMapViewController.drawPlanarGraph("h2Data.json");
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
                        iMapViewController.addLocationMark(x, y);
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
                        }
                    }
                });
            }
        });
        navigateManager = new MapBoxNavigateManager(this, "path.json");
        navigateManager.setNavigateListener(new INavigateManager.Listener<FeatureCollection>() {
            @Override
            public void OnNavigateComplete(INavigateManager.NavigateState state, FeatureCollection route) {
                Log.e(TAG, "OnNavigateComplete: " + state);
                if (state == INavigateManager.NavigateState.OK) {
                    showRoute(route);
                }
            }
        });
    }

    private void showRoute(FeatureCollection route) {
        ((MapBoxMapViewController) iMapViewController).showRoute(route);
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
        if (iMapViewController != null) {
            iMapViewController.onDestroy();
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
