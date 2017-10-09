package com.palmap.indoor;

import android.os.Bundle;
import android.view.ViewGroup;

import com.palmap.core.data.PlanarGraph;

/**
 * Created by wtm on 2017/9/30.
 */

public interface IMapViewController {

    static interface Action{
        void onAction();
    }

    static interface onSingTapListener{
        void onAction(double x,double y);
    }

    void attachMap(ViewGroup root, Bundle savedInstanceState,Action action);

    long getFloorId();

    void drawPlanarGraph(String mapDataPath);

    void drawPlanarGraph(PlanarGraph p);

    void resetNorth(long animTime);

    void selectFeature(double x,double y);

    void setOnSingTapListener(onSingTapListener l);

    void addOverLayer(OverLayer overLayer);

    void onStart();

    void onStop();

    void onResume();

    void onPause();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();

    void onLowMemory();

}
