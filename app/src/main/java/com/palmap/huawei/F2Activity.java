package com.palmap.huawei;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.R;
import com.palmap.indoor.IMapViewController;
import com.palmap.indoor.MapViewControllerFactory;
import com.palmap.indoor.OverLayer;
import com.palmap.indoor.navigate.impl.MapBoxNavigateManager;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * Created by wtm on 2017/9/30.
 */
public class F2Activity extends Activity {

    IMapViewController iMapViewController;

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
                iMapViewController.setOnSingTapListener(new IMapViewController.onSingTapListener() {
                    @Override
                    public void onAction(final double x,final double y) {
                        iMapViewController.addOverLayer(new OverLayer() {
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
                        });
                    }
                });
            }
        });
        MapBoxNavigateManager navigateManager = new MapBoxNavigateManager(this,"path.json");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (iMapViewController!=null){
            iMapViewController.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (iMapViewController!=null){
            iMapViewController.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (iMapViewController!=null){
            iMapViewController.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (iMapViewController!=null){
            iMapViewController.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (iMapViewController!=null){
            iMapViewController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iMapViewController!=null){
            iMapViewController.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (iMapViewController!=null){
            iMapViewController.onLowMemory();
        }
    }

}
