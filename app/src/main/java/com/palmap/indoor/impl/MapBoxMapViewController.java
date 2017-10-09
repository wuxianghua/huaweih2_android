package com.palmap.indoor.impl;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.palmap.core.IndoorMapView;
import com.palmap.core.MapEngine;
import com.palmap.core.data.PlanarGraph;
import com.palmap.indoor.IMapViewController;
import com.palmap.indoor.OverLayer;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

import static com.palmap.core.util.UtilsKt.loadFromAsset;

/**
 * Created by wtm on 2017/9/30.
 */
public class MapBoxMapViewController implements IMapViewController {

    private IndoorMapView indoorMapView;
    private Context context;

    public MapBoxMapViewController(Context context) {
        this.context = context;
        MapEngine.INSTANCE.start(context,"pk.eyJ1IjoiY2FtbWFjZSIsImEiOiJjaW9vbGtydnQwMDAwdmRrcWlpdDVoM3pjIn0.Oy_gHelWnV12kJxHQWV7XQ");
        indoorMapView = IndoorMapView.Companion.create(context);
    }

    @Override
    public void attachMap(ViewGroup root, Bundle savedInstanceState,final  Action action) {
        indoorMapView.attachMap(root, savedInstanceState, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                action.onAction();
                return null;
            }
        });
    }

    @Override
    public long getFloorId() {
        return indoorMapView.getFloorId();
    }

    @Override
    public void drawPlanarGraph(String mapDataPath) {
        PlanarGraph planarGraph = new PlanarGraph(loadFromAsset(context,mapDataPath),15);
        indoorMapView.drawPlanarGraph(planarGraph);
    }

    @Override
    public void drawPlanarGraph(PlanarGraph p) {
        indoorMapView.drawPlanarGraph(p);
    }

    @Override
    public void resetNorth(long animTime) {
        indoorMapView.resetNorth(animTime);
    }

    @Override
    public void selectFeature(double x, double y) {
        indoorMapView.selectFeature(x,y);
    }

    @Override
    public void setOnSingTapListener(final onSingTapListener l) {
        indoorMapView.setOnSingTapListener(new Function2<Double, Double, Unit>() {
            @Override
            public Unit invoke(Double aDouble, Double aDouble2) {
                l.onAction(aDouble,aDouble2);
                return null;
            }
        });
    }

    @Override
    public void addOverLayer(final OverLayer overLayer) {
        if (overLayer==null){
            return;
        }
        indoorMapView.addOverLayer(new com.palmap.core.OverLayer() {
            @Override
            public int getResource() {
                return overLayer.getResource();
            }

            @NotNull
            @Override
            public double[] getCoordinate() {
                return new double[]{
                        overLayer.getCoordinate().x,
                        overLayer.getCoordinate().y,
                };
            }

            @Override
            public long getFloorId() {
                return overLayer.getFloorId();
            }
        });
    }

    @Override
    public void setLocationMarkIcon(int resource,int w , int h) {
        indoorMapView.setLocationMarkIcon(resource,w ,h);
    }

    @Override
    public void addLocationMark(double x, double y) {
        indoorMapView.addLocationMark(new LatLng(x,y));
    }

    @Override
    public void onStart() {
        indoorMapView.onStart();
    }

    @Override
    public void onStop() {
        indoorMapView.onStop();
    }

    @Override
    public void onResume() {
        indoorMapView.onResume();
    }

    @Override
    public void onPause() {
        indoorMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        indoorMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        indoorMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        indoorMapView.onLowMemory();
    }
}
