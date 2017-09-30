package com.palmap.huawei;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.palmap.demo.huaweih2.R;
import com.palmap.indoor.IMapViewController;
import com.palmap.indoor.MapViewControllerFactory;

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
                iMapViewController.drawPlanarGraph("h2Data.json");
            }
        });
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
